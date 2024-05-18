package com.ahk.tcpcommunication.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServerModel(
    val ip: String,
    val port: Int,
) : Parcelable
