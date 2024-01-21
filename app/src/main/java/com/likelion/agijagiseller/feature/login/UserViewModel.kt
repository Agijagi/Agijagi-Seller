package com.likelion.agijagiseller.feature.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.likelion.agijagiseller.feature.login.data.repository.UserRepositoryImpl
import com.likelion.agijagiseller.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl,
) : ViewModel() {
    private var _signUpStatus = MutableLiveData<Boolean>()
    val signUpStatus get() = _signUpStatus

    private var _userSaved = MutableLiveData<Boolean>()
    val userSaved get() = _userSaved

    private var _currentUser = MutableLiveData<FirebaseUser>()
    val currentUser = _currentUser

    fun signUp(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _signUpStatus.value = userRepositoryImpl.signUp(email, password)
        }
    }

    fun saveUser(
        uid: String,
        user: User,
    ) {
        viewModelScope.launch {
            _userSaved.value = userRepositoryImpl.saveUser(uid, user)
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            _currentUser.value = userRepositoryImpl.getCurrentUser()
        }
    }
}
