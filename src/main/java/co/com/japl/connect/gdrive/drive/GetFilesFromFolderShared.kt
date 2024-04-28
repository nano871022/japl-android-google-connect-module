package co.com.japl.connect.gdrive.drive

import android.content.Context
import android.util.Log
import androidx.annotation.RawRes
import co.com.japl.connect.gdrive.BuildConfig
import co.com.japl.connect.gdrive.R
import co.com.japl.connect.gdrive.model.DriveFile
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.FileList
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.concurrent.CompletableFuture

class GetFilesFromFolderShared(val context:Context) {
    private val APPLICATION_NAME = BuildConfig.APPLICATION_NAME
    private val MIME_TYPE_IMAGE = BuildConfig.MIME_TYPE_IMAGE
    private val FOLDER_ID = BuildConfig.FOLDER_ID
    private val URL_INITIAL = BuildConfig.URL_INITIAL

    fun execute(@RawRes resId:Int = R.raw.cralameda181_34c486bb5b56, folder:String = FOLDER_ID):List<DriveFile>?{
        val response = CompletableFuture.supplyAsync(){ getFiles(resId,folder) }
        return response.get()
    }

    fun downloadFile(idFile:String,@RawRes resId:Int = R.raw.cralameda181_34c486bb5b56, folder:String = FOLDER_ID):File?{
        val response = CompletableFuture.supplyAsync(){ getFiles(idFile,resId,folder) }
        return response.get()
    }

    private fun getFiles(@RawRes resId: Int, folder:String):List<DriveFile>?{
        Log.d("TaskTest6","<<<=== START:TaskTest6#doInBackground read files  $APPLICATION_NAME $MIME_TYPE_IMAGE $FOLDER_ID $URL_INITIAL")
        val credentials = getCredentials(resId)
        val drive = getDriveConnection(credentials)
        val result = getListFiles(drive,folder)
        return result?.files?.map {
            DriveFile(
                id = it.id
                , name = it.name
                , mimeType =  it.mimeType
                , url=URL_INITIAL + it.id
                , date = LocalDateTime.ofInstant(Instant.ofEpochMilli(it.createdTime?.value?:LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()), ZoneId.systemDefault())
                , version = it.version?.toString()?:"1"
                , description = it.description)
        }?.also { Log.d("TaskTest6","<<<=== FINISH:TaskTest6#doInBackground read files $it $credentials $drive $result") }
    }

    private fun getFiles(idFile:String,@RawRes resId: Int, folder:String):File?{
        Log.d("TaskTest7","<<<=== START:TaskTest6#doInBackground read files  $APPLICATION_NAME $MIME_TYPE_IMAGE $FOLDER_ID $URL_INITIAL")
        val credentials = getCredentials(resId)
        val drive = getDriveConnection(credentials)
        val fileFound = getFile(idFile,folder,drive) ?: return null

        val file = File(context.filesDir, fileFound.name)
        try {
            val outputStream = FileOutputStream(file)
            downloadFile(idFile,drive, outputStream)
            outputStream.flush()
            outputStream.close()
        }catch (e:Exception){
            Log.e("TaskTest7","<<<=== ERROR:TaskTest6#doInBackground read files $e")
        }
        return file?.also { Log.d("TaskTest7","<<<=== FINISH:TaskTest6#doInBackground read files $it $credentials $drive ${it.name}") }
    }

    private fun getCredentials(@RawRes resId: Int): GoogleCredential? {
        val stream = context.resources.openRawResource(resId)
        return GoogleCredential.fromStream(stream)
            .createScoped(listOf(DriveScopes.DRIVE_READONLY))
    }

    private fun getDriveConnection(credentials:GoogleCredential?): Drive? {
        return Drive.Builder(credentials?.transport,credentials?.jsonFactory,credentials)
            .setApplicationName(APPLICATION_NAME)
            .build()
    }

    private fun getFile(idFile:String,folder:String,drive:Drive?):com.google.api.services.drive.model.File? {
        return getListFiles(folder,drive)?.files?.firstOrNull { it.id == idFile}
    }

    private fun getListFiles(drive:Drive?,folder:String,mimeType:String = MIME_TYPE_IMAGE): FileList? {
        return drive?.files()?.list()
            ?.setQ(mimeType)
            ?.setQ("'$folder' in parents")
            ?.setFields("files(id, name, mimeType, version, createdTime, description)")
            ?.execute()
    }

    private fun getListFiles(folder:String,drive:Drive?): FileList? {
        return drive?.files()?.list()
            ?.setQ("'$folder' in parents")
            ?.setFields("files(id, name, mimeType, version, createdTime, description)")
            ?.execute()
    }

    private fun downloadFile(idFile:String,drive:Drive?,fileOutputStream:FileOutputStream) {
        if(drive == null) return
         drive.files()[idFile]?.executeAndDownloadTo(fileOutputStream)
    }
}