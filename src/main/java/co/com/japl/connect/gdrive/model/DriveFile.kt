package co.com.japl.connect.gdrive.model

import java.time.LocalDateTime

data class DriveFile(   val id: String,
                        val name: String,
                        val mimeType: String,
                        val url: String,
    val version: String,
    val date: LocalDateTime,
    val description: String? = null

                        )
