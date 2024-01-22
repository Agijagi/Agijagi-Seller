package com.likelion.agijagiseller.feature.login.data.repository

import kotlinx.coroutines.flow.Flow

interface UserLocalRepository {
    suspend fun updateUid(uid: String)

    suspend fun updateEmail(email: String)

    suspend fun updateLoginType(loginType: String)

    suspend fun updateName(name: String)

    val uid: Flow<String?>

    val email: Flow<String?>

    val loginType: Flow<String?>

    val name: Flow<String?>
}
