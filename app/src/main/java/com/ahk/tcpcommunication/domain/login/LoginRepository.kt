package com.ahk.tcpcommunication.domain.login

import com.ahk.tcpcommunication.core.data.tcp.TCPServiceConnection
import com.ahk.tcpcommunication.core.model.LoginUser
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val tcpServiceConnection: TCPServiceConnection,
) {
    fun login(loginUser: LoginUser): Completable =
        tcpServiceConnection.connect(loginUser.getServerModel())
}
