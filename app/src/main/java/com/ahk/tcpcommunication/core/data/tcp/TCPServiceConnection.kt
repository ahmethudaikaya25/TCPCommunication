package com.ahk.tcpcommunication.core.data.tcp

import com.ahk.tcpserver.model.ServerModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.subjects.PublishSubject

interface TCPServiceConnection {
    fun connect(serverModel: ServerModel): Completable

    fun listen(): PublishSubject<ByteArray>

    fun disconnect(): Completable
}
