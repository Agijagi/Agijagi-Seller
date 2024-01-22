package com.likelion.agijagiseller.feature.login.data.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.likelion.agijagiseller.feature.login.model.User
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
) {
    suspend fun signUp(
        email: String,
        password: String,
    ): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Log.d("user CREATE 성공", "user CREATE 성공")
            true
        } catch (e: Exception) {
            Log.d("user CREATE 실패", "user CREATE 실패")
            false
        }

    }

    suspend fun saveUser(
        uid: String,
        user: User,
    ): Boolean {
        return try {
            db.collection("seller").document(uid).set(user).await()
            Log.d("user 정보 CREATE 성공", "user 정보 CREATE 성공")
            true
        } catch (e: Exception) {
            Log.d("user 정보 CREATE 실패", e.message.toString())
            false
        }
    }

    suspend fun getCurrentUser(): FirebaseUser? {
        return try {
            Log.d("user READ 성공", "user READ 성공")
            auth.currentUser
        } catch (e: Exception) {
            Log.d("user READ 실패", e.message.toString())
            null
        }
    }

    suspend fun getUser(
        uid: String,
    ): User? {
        return try {
            val document = db.collection("seller").document(uid).get().await()
            document.toObject(User::class.java)?.also { user ->
                Log.d("user 정보 READ 성공", "$user")
                return user
            }
        } catch (e: Exception) {
            Log.d("user 정보 READ 실패", e.message.toString())
            null
        }
    }
}
