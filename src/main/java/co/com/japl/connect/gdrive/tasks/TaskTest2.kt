package co.com.japl.connect.gdrive.tasks

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class TaskTest2 {

    class My2Task(val context: Context): AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            val folderId =
                "0B3jBktJ8T7h8dWxzZUlNem5HZGs?resourcekey=0-x_xsSsu68PILRyU1MnrGfQ&usp=drive_link"
            val urlInitial = "https://drive.google.com/drive/u/0/folders/${folderId}"
            val url = URL(urlInitial)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            val code = connection.responseCode
            if (code == 200) {
                Log.d(javaClass.name,"doInBackgound ${connection.responseMessage} ${connection.content}")
                val buffered = BufferedReader(InputStreamReader(connection.inputStream))
                val lines = buffered.readLines()
                Log.d(javaClass.name,"doInBackgound ${lines}")
                connection.inputStream.close()
                buffered.close()
            }
            return null;
        }
    }
}