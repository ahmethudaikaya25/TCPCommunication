package com.ahk.tcpcommunication.domain.network

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchLocalIpUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
) {
    fun invoke(): Single<String> = networkRepository.fetchIp()
}
