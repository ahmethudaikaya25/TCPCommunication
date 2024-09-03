package com.ahk.tcpserver.server

import com.ahk.tcpserver.model.ServerModel
import io.reactivex.rxjava3.subjects.PublishSubject

interface TCPServer {

    fun isRunning(): Boolean

    fun isListening(): Boolean

    fun start(serverModel: ServerModel)

    fun listen(): PublishSubject<ByteArray>

    fun stop()
}
