package com.ahk.tcpcommunication.features.login

import com.ahk.tcpcommunication.core.model.LoginUser

sealed class LoginUIState {
    object Loading : LoginUIState()
    data class Success(
        val loginUser: LoginUser,
    ) : LoginUIState()

    data class Error(
        val message: String,
    ) : LoginUIState()

    fun getSuccessUser(): LoginUser? {
        return when (this) {
            is Success -> loginUser
            else -> null
        }
    }
}
