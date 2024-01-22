package com.likelion.agijagiseller.feature.login.data.repository

import com.likelion.agijagiseller.feature.login.data.local.UserLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserLocalRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
) : UserLocalRepository{
    override suspend fun updateUid(uid: String) = userLocalDataSource.updateUid(uid)

    override suspend fun updateEmail(email: String) = userLocalDataSource.updateEmail(email)

    override suspend fun updateLoginType(loginType: String) = userLocalDataSource.updateLoginType(loginType)

    override suspend fun updateName(name: String) = userLocalDataSource.updateName(name)

    override val uid: Flow<String?>
        get() = userLocalDataSource.uid

    override val email: Flow<String?>
        get() = userLocalDataSource.email

    override val loginType: Flow<String?>
        get() = userLocalDataSource.loginType

    override val name: Flow<String?>
        get() = userLocalDataSource.name
}
