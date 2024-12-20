package com.ahk.tcpcommunication.domain.network

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface NetworkRepository {
    fun fetchIp(): Single<String>

    fun isWifiConnected(): Completable
}
