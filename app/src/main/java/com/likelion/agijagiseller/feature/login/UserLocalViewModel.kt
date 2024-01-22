package com.likelion.agijagiseller.feature.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.agijagiseller.feature.login.data.repository.UserLocalRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLocalViewModel @Inject constructor(
    private val userLocalRepositoryImpl: UserLocalRepositoryImpl,
) : ViewModel() {
    private var _uid = MutableLiveData<String>()
    val uid get() = _uid

    private var _email = MutableLiveData<String>()
    val email get() = _email

    private var _loginType = MutableLiveData<String>()
    val loginType get() = _loginType

    private var _name = MutableLiveData<String>()
    val name get() = _name

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

    fun fetchUid() {
        viewModelScope.launch {
            userLocalRepositoryImpl.uid.collect { uid ->
                _uid.value = uid
            }
        }
    }

    fun fetchEmail() {
        viewModelScope.launch {
            userLocalRepositoryImpl.email.collect { email ->
                _email.value = email
            }
        }
    }

    fun fetchLoginType() {
        viewModelScope.launch {
            userLocalRepositoryImpl.loginType.collect { loginType ->
                _loginType.value = loginType
            }
        }
    }

    fun fetchName() {
        viewModelScope.launch {
            userLocalRepositoryImpl.name.collect { name ->
                _name.value = name
            }
        }
    }
}
