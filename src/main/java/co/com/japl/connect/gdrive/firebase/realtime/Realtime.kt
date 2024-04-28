package co.com.japl.connect.gdrive.firebase.realtime

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class Realtime @Inject constructor(private val database: FirebaseDatabase) {
    fun connect(){

        val reference = database.getReference("data")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue()
                Log.d("Realtime",data.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Realtime",error.details)
            }

        })
    }
}