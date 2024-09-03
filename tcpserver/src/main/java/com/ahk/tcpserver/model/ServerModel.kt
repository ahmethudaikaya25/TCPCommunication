package com.ahk.tcpserver.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServerModel(
    val ip: String,
    val port: Int,
) : Parcelable
