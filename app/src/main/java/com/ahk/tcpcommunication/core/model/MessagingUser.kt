package com.ahk.tcpcommunication.core.model

data class MessagingUser(
    val id: Int,
    val name: String,
    val ip: String,
    val port: Int,
)
