package com.ahk.tcpcommunication.core.model

import com.ahk.tcpserver.model.ServerModel

data class LoginUser(
    val name: String,
    val ip: String,
    val port: Int,
    val password: String,
) {
    fun getServerModel(): ServerModel {
        return ServerModel(ip, port)
    }
}
