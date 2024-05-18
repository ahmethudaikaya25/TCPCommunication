package com.ahk.tcpcommunication.features.login

import com.ahk.tcpcommunication.core.model.User

sealed class LoginUIState {
    object Loading : LoginUIState()
    data class Success(
        val user: User,
    ) : LoginUIState()

    object Error : LoginUIState()

    fun getSuccessUser(): User? {
        return when (this) {
            is Success -> user
            else -> null
        }
    }
}
