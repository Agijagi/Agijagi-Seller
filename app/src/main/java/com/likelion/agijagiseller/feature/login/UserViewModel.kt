package com.likelion.agijagiseller.feature.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.likelion.agijagiseller.feature.login.data.repository.UserRemoteRepositoryImpl
import com.likelion.agijagiseller.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRemoteRepositoryImpl: UserRemoteRepositoryImpl,
) : ViewModel() {
    private var _signUpStatus = MutableLiveData<Boolean>()
    val signUpStatus get() = _signUpStatus

    private var _userSaved = MutableLiveData<Boolean>()
    val userSaved get() = _userSaved

    private var _currentUser = MutableLiveData<FirebaseUser>()
    val currentUser = _currentUser

    private var _userGetStatus = MutableLiveData<User?>()
    val userGetStatus get() = _userGetStatus

    fun signUp(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _signUpStatus.value = userRemoteRepositoryImpl.signUp(email, password)
        }
    }

    fun saveUser(
        uid: String,
        user: User,
    ) {
        viewModelScope.launch {
            _userSaved.value = userRemoteRepositoryImpl.saveUser(uid, user)
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            _currentUser.value = userRemoteRepositoryImpl.getCurrentUser()
        }
    }

    fun getUser(
        uid: String,
    ) {
        viewModelScope.launch {
            _userGetStatus.value = userRemoteRepositoryImpl.getUser(uid)
        }
    }
}
