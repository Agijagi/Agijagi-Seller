package com.likelion.agijagiseller.feature.login.data.repository

interface UserLocalRepository {
    suspend fun updateUid(uid: String)

    suspend fun updateEmail(email: String)

    suspend fun updateLoginType(loginType: String)

    suspend fun updateName(name: String)
}
