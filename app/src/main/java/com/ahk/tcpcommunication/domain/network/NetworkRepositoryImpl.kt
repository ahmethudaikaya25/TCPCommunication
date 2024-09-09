package com.ahk.tcpcommunication.domain.network

import com.ahk.tcpcommunication.core.data.network.NetworkService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class NetworkRepositoryImpl(
    private val networkService: NetworkService,
) : NetworkRepository {
    override fun fetchIp(): Single<String> {
        return networkService.fetchIp()
    }

    override fun isWifiConnected(): Completable {
        return networkService.isWifiConnected()
    }
}
