package com.likelion.agijagiseller.feature.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.agijagiseller.feature.login.data.repository.UserLocalRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLocalViewModel @Inject constructor(
    private val userLocalRepositoryImpl: UserLocalRepositoryImpl,
) : ViewModel() {
    fun updateUid(uid: String) =
        viewModelScope.launch {
            userLocalRepositoryImpl.updateUid(uid)
        }

    fun updateEmail(email: String) =
        viewModelScope.launch {
            userLocalRepositoryImpl.updateEmail(email)
        }

    fun updateLoginType(loginType: String) =
        viewModelScope.launch {
            userLocalRepositoryImpl.updateLoginType(loginType)
        }

    fun updateName(name: String) =
        viewModelScope.launch {
            userLocalRepositoryImpl.updateName(name)
        }
}
