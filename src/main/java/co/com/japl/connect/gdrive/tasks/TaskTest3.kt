package co.com.japl.connect.gdrive.tasks

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials

class TaskTest3(val context: Context): AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg p0: Void?): Void? {

        val transport = GoogleNetHttpTransport.newTrustedTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()
        val credentials = GoogleCredentials.getApplicationDefault()
        val requestInitializer = HttpCredentialsAdapter(credentials)
        val drive = Drive.Builder(transport,jsonFactory,requestInitializer)
            .setApplicationName("URAlameda181")
            .build()
        val folderId ="0B3jBktJ8T7h8dWxzZUlNem5HZGs"
        val folderLink = "https://drive.google.com/drive/u/0/folders/${folderId}"
        val request = drive.files().list()
        request.q = "'$folderLink' in parents"
        try{
            val response = request.execute()
            response.files.forEach { Log.d(javaClass.name,"Name: ${it.name}") }
        }catch(e: GoogleJsonResponseException){
            Log.e(javaClass.name,"$e")
        }

        return null;
    }
}