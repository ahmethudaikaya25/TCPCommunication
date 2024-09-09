package com.ahk.tcpcommunication.domain.login

import javax.inject.Inject

class StopListeningUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    fun invoke() = loginRepository.stopListenning()
}
