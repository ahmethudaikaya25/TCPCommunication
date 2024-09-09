package com.ahk.tcpserver.server

import com.ahk.tcpserver.model.ServerModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import java.io.DataInputStream
import java.net.ServerSocket

class ITCPServer : TCPServer {
    private var socket: ServerSocket? = null
    private var working = false
    private var dataInputStream: DataInputStream? = null
    private val disposable = CompositeDisposable()
    private val messageSubject = PublishSubject.create<ByteArray>()
    private var listen = false

    override fun isRunning(): Boolean {
        return working
    }

    override fun isListening(): Boolean {
        return listen
    }

    override fun start(serverModel: ServerModel) {
        socket = ServerSocket(serverModel.port)
        working = true
        Single.create<Unit> {
            while (working) {
                try {
                    val client = socket?.accept()
                    dataInputStream = DataInputStream(client?.getInputStream())
                    Timber.d("Client connected: ${client?.inetAddress?.hostAddress}")
                    if (isRunning() && isListening()) {
                        listenToClient()
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                    e.printStackTrace()
                }
            }
        }.subscribeOn(Schedulers.io()).subscribe().addTo(disposable)
    }

    private fun listenToClient() {
        var lastOfMessage = false
        while (working && !lastOfMessage) {
            try {
                dataInputStream?.let {
                    if (it.available() > 0) {
                        val byteArray = it.readBytes()
                        Timber.d("Message received: ${byteArray.decodeToString()}")
                        messageSubject.onNext(byteArray)
                    } else {
                        lastOfMessage = true
                    }
                } ?: run {
                    listen = false
                }
            } catch (e: Exception) {
                listen = false
                Timber.e(e)
                e.printStackTrace()
            }
        }
    }

    override fun listen(): PublishSubject<ByteArray> {
        listen = true
        return messageSubject
    }

    override fun stop() {
        listen = false
        working = false
        disposable.clear()
        socket?.close()
    }
}
