package co.com.japl.connect.gdrive

import android.content.Context
import androidx.annotation.RequiresApi
import co.com.japl.connect.gdrive.drive.GetFilesFromFolderShared
import co.com.japl.connect.gdrive.model.DriveFile

class GDrive() {
    @RequiresApi(34)
    fun connectToFolder( context:Context):List<DriveFile> {
        return GetFilesFromFolderShared(context).execute() ?: emptyList()
    }
}