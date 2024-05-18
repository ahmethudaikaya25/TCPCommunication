package com.ahk.tcpcommunication.core.model

data class User(
    val name: String,
    val ip: String,
    val port: Int,
    val password: String,
) {
    fun getServerModel(): ServerModel {
        return ServerModel(ip, port)
    }
}
