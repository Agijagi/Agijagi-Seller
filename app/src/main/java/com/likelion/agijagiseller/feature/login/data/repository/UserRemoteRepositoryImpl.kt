package com.likelion.agijagiseller.feature.login.data.repository

import com.likelion.agijagiseller.feature.login.data.remote.UserRemoteDataSource
import com.likelion.agijagiseller.feature.login.model.User
import javax.inject.Inject

class UserRemoteRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRemoteRepository {
    override suspend fun signUp(
        email: String,
        password: String,
    ) = userRemoteDataSource.signUp(email, password)

    override suspend fun saveUser(
        uid: String,
        user: User,
    ) = userRemoteDataSource.saveUser(uid, user)

    override suspend fun getCurrentUser() = userRemoteDataSource.getCurrentUser()

    override suspend fun getUser(
        uid: String,
    ): User? = userRemoteDataSource.getUser(uid)
}
