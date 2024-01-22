package com.likelion.agijagiseller.feature.login.data.repository

import com.google.firebase.auth.FirebaseUser
import com.likelion.agijagiseller.feature.login.model.User

interface UserRemoteRepository {
    suspend fun signUp(
        email: String,
        password: String,
    ): Boolean

    suspend fun saveUser(
        uid: String,
        user: User,
    ): Boolean

    suspend fun getCurrentUser(): FirebaseUser?

    suspend fun getUser(
        uid: String,
    ): User?
}
