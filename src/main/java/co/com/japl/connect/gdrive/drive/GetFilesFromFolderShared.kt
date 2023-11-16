package co.com.japl.connect.gdrive.drive

import android.content.Context
import android.util.Log
import co.com.japl.connect.gdrive.BuildConfig
import co.com.japl.connect.gdrive.R
import co.com.japl.connect.gdrive.model.DriveFile
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.FileList
import java.util.concurrent.CompletableFuture

class GetFilesFromFolderShared(val context:Context) {
    private val APPLICATION_NAME = BuildConfig.APPLICATION_NAME
    private val MIME_TYPE_IMAGE = BuildConfig.MIME_TYPE_IMAGE
    private val FOLDER_ID = BuildConfig.FOLDER_ID
    private val URL_INITIAL = BuildConfig.URL_INITIAL

    fun execute():List<DriveFile>?{
        val response = CompletableFuture.supplyAsync(){ getFiles() }
        return response.get()
    }

    private fun getFiles():List<DriveFile>?{
        Log.d("TaskTest6","<<<=== START:TaskTest6#doInBackground read files  $APPLICATION_NAME $MIME_TYPE_IMAGE $FOLDER_ID $URL_INITIAL")
        val credentials = getCredentials()
        val drive = getDriveConnection(credentials)
        val result = getListFiles(drive)
        return result?.files?.map {
            DriveFile(it.id, it.name, it.mimeType, URL_INITIAL + it.id)
        }?.also { Log.d("TaskTest6","<<<=== FINISH:TaskTest6#doInBackground read files $it $credentials $drive $result") }
    }

    private fun getCredentials(): GoogleCredential? {
        val stream = context.resources.openRawResource(R.raw.cralameda181_34c486bb5b56)
        return GoogleCredential.fromStream(stream)
            .createScoped(listOf(DriveScopes.DRIVE_READONLY))
    }

    private fun getDriveConnection(credentials:GoogleCredential?): Drive? {
        return Drive.Builder(credentials?.transport,credentials?.jsonFactory,credentials)
            .setApplicationName(APPLICATION_NAME)
            .build()
    }

    private fun getListFiles(drive:Drive?): FileList? {
        return drive?.files()?.list()
            ?.setQ(MIME_TYPE_IMAGE)
            ?.setQ("'$FOLDER_ID' in parents")
            ?.setFields("files(id, name, mimeType)")
            ?.execute()
    }

}