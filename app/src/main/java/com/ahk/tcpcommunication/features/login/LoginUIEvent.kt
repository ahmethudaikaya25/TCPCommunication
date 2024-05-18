package com.ahk.tcpcommunication.features.login

import com.ahk.tcpcommunication.core.model.User

sealed class LoginUIEvent {
    data class LoginClicked(val user: User) : LoginUIEvent()
}
