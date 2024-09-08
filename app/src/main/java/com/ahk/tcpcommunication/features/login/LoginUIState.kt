package com.ahk.tcpcommunication.features.login

import com.ahk.tcpcommunication.base.model.ErrorModel
import com.ahk.tcpcommunication.core.model.LoginUser

sealed class LoginUIState {
    object Initial : LoginUIState()
    object Loading : LoginUIState()
    data class Success(
        val loginUser: LoginUser,
    ) : LoginUIState()

    data class Error(
        val errorModel: ErrorModel,
    ) : LoginUIState()

    fun getSuccessUser(): LoginUser? {
        return when (this) {
            is Success -> loginUser
            else -> null
        }
    }

    fun setSuccessUserName(name: String): LoginUIState {
        return when (this) {
            is Success -> copy(loginUser = loginUser.copy(name = name))
            else -> Success(LoginUser(name, "", 0, ""))
        }
    }

    fun setSuccessUserIp(ip: String): LoginUIState {
        return when (this) {
            is Success -> copy(loginUser = loginUser.copy(ip = ip))
            else -> Success(LoginUser("", ip, 0, ""))
        }
    }

    fun setSuccessUserPort(port: Int): LoginUIState {
        return when (this) {
            is Success -> copy(loginUser = loginUser.copy(port = port))
            else -> Success(LoginUser("", "", port, ""))
        }
    }

    fun setSuccessUserPassword(password: String): LoginUIState {
        return when (this) {
            is Success -> copy(loginUser = loginUser.copy(password = password))
            else -> Success(LoginUser("", "", 0, password))
        }
    }
}
