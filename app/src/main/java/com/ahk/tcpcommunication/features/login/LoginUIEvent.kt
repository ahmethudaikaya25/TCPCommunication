package com.ahk.tcpcommunication.features.login

import com.ahk.tcpcommunication.core.model.LoginUser

sealed class LoginUIEvent {
    data class LoginClicked(val loginUser: LoginUser) : LoginUIEvent()
}
