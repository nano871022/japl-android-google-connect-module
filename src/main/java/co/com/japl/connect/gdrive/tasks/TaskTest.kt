package co.com.japl.connect.gdrive.tasks

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import co.com.japl.connect.gdrive.R
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials

class TaskTest(val context: Context): AsyncTask<Void, Void, Void>(){
    override fun doInBackground(vararg p0: Void?): Void? {

        val transport = GoogleNetHttpTransport.newTrustedTransport()
        val stream = context.resources.openRawResource(R.raw.uralameda181_auth2)
        val credentials = GoogleCredentials.fromStream(stream).createScoped(DriveScopes.all())
        val requestInitializer = HttpCredentialsAdapter(credentials)
        val service: Drive = Drive.Builder(transport,
            JacksonFactory.getDefaultInstance(),requestInitializer)
            .setApplicationName("URAlameda181")
            .build()
        val folderId = "0B3jBktJ8T7h8dWxzZUlNem5HZGs"
        val resourceKey = "0-x_xsSsu68PILRyU1MnrGfQ"
        val files = mutableListOf<File>()
        var pageToken: String? = null
        do{
            val result = service.files().list()
                .execute()
            result.files?.forEach{ Log.d(javaClass.name,"Found file: ${it.id} ${it.name} ") }
            files.addAll(result.files ?: emptyList())
            pageToken = result.nextPageToken
        }while (pageToken != null)
        return null
    }
}