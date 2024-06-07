package co.com.japl.connect.gdrive.firebase.realtime

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Realtime @Inject constructor(private val database: FirebaseDatabase) {

    fun connect(key:String) : Flow<String?> {

        val reference = database.getReference(key)
        val defReference = reference.get()
        return flow{
            val snapshot = defReference.await()
            emit(snapshot.value as? String)
        }

    }
}