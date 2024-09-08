package com.ahk.tcpcommunication.core.data.network

import io.reactivex.rxjava3.core.Single

interface NetworkService {
    fun fetchIp(): Single<String>
    fun isWifiConnected(): Single<Boolean>
}