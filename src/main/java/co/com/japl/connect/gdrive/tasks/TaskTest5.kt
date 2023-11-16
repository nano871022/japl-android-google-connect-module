package co.com.japl.connect.gdrive.tasks

import android.accounts.AuthenticatorException
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes

class TaskTest5(val context: Context): AsyncTask<Void, Void, Void>(){

    fun getDriveService(): Drive?{
        Log.d(javaClass.name,"Start getDriveService")
        val googleAccount = GoogleSignIn.getLastSignedInAccount(context)
        if(googleAccount == null){
            if(!login(context)) {
                throw AuthenticatorException("Usuario no logeado")
            }
        }
        val credentials =
            GoogleAccountCredential.usingOAuth2(context, listOf(DriveScopes.DRIVE_FILE))
        credentials.selectedAccount = googleAccount?.account!!
        return Drive.Builder(
            AndroidHttp.newCompatibleTransport(),
            JacksonFactory.getDefaultInstance(),
            credentials)
            .setApplicationName("cralameda181")
            .build()
    }

    fun login(context:Context):Boolean{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(context, gso)
        val intent = client.signInIntent
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        return ContextCompat.startActivities(context, arrayOf(intent))
    }

    fun accessDriveFiles(googleDriveService:Drive){
        //scope.launch(Dispatchers.Default) {
        var pageToken: String? = null
        do{
            val result = googleDriveService.files().list().apply {
                spaces = "drive"
                fields = "nextPageToken, files(id, name)"
                pageToken = this.pageToken
            }.execute()
            result.files.forEach { Log.d(javaClass.name,"Name: ${it.name}") }
        }while(pageToken != null)
        //}
    }
    override fun doInBackground(vararg p0: Void?): Void? {
        Log.d(javaClass.name,"Start doInBackgound")
        val googleDriveService = getDriveService()
        val result = accessDriveFiles(googleDriveService!!)
        Log.d(javaClass.name,"Finish doInBackgound $result")
        return null
    }


}