package co.com.japl.connect.gdrive.tasks

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import co.com.japl.connect.gdrive.R
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials

class TaskTest4(val context: Context): AsyncTask<Void, Void, Void>(){
    override fun doInBackground(vararg p0: Void?): Void? {

        val APPLICATION_NAME = "cralameda181"
        val JSON_FILE_PATH = "uralameda181_auth2.json"
        val packageName = "co.com.alameda181.unidadresidencialalameda181"

        val transport = NetHttpTransport.Builder().build()
        val stream = context.resources.openRawResource(R.raw.credentials)
        val jsonFactory = JacksonFactory.getDefaultInstance()
        //val serviceAccountKeyStream = resources.openRawResource(resources.getIdentifier(JSON_FILE_PATH,"raw",packageName))
        val serviceAccountKey = GoogleCredentials.fromStream(stream)
        val scopes = serviceAccountKey.createScoped(listOf("https://www.googleapis.com/auth/drive","https://www.googleapis.com/auth/drive.file"))
        val scoped = HttpCredentialsAdapter(scopes)
        val driveService = Drive.Builder(transport,jsonFactory,null)
            .setHttpRequestInitializer(scoped)
            .setApplicationName(APPLICATION_NAME)
            .build()
        driveService.files().list().execute().files.forEach { Log.d(javaClass.name,"Archivo: ${it.name}") }

        return null
    }
}