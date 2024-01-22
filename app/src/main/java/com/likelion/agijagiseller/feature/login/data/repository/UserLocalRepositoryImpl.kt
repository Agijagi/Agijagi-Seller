package com.likelion.agijagiseller.feature.login.data.repository

import com.likelion.agijagiseller.feature.login.data.local.UserLocalDataSource
import javax.inject.Inject

class UserLocalRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
) : UserLocalRepository{
    override suspend fun updateUid(uid: String) = userLocalDataSource.updateUid(uid)

    override suspend fun updateEmail(email: String) = userLocalDataSource.updateEmail(email)

    override suspend fun updateLoginType(loginType: String) = userLocalDataSource.updateLoginType(loginType)

    override suspend fun updateName(name: String) = userLocalDataSource.updateName(name)
}
