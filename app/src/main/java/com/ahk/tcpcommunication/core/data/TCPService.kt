package com.ahk.tcpcommunication.core.data

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.ahk.tcpcommunication.TCPServiceData
import com.ahk.tcpcommunication.TCPServiceDataCallback
import com.ahk.tcpcommunication.core.model.ServerModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class TCPService : Service() {
    val disposables = CompositeDisposable()
    val tcpServer = ITCPServer()

    val binder = object : TCPServiceData.Stub() {
        override fun messageReceive(onMessageReceived: TCPServiceDataCallback?) {
            tcpServer.listen().observeOn(Schedulers.io()).let { observable ->
                disposables.add(
                    observable.subscribe(
                        {
                            Timber.d("Message received: ${it.contentToString()}")
                            onMessageReceived?.onMessageReceived(it)
                        },
                        {
                            Timber.e(it)
                            onMessageReceived?.onErrorOccurred(it.message)
                        },
                    ),
                )
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val serverModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.extras?.getParcelable("serverModel", ServerModel::class.java)
        } else {
            intent?.getParcelableExtra("serverModel")
        }
        serverModel?.let {
            tcpServer.start(it)
        } ?: Timber.e("TCP server model is null")

        return if (tcpServer.isRunning()) {
            Timber.d("TCP server is running")
            START_STICKY
        } else {
            Timber.e("TCP server is not running")
            stopSelf()
            STOP_FOREGROUND_REMOVE
        }
    }

    override fun onDestroy() {
        Timber.d("Service destroyed")
        super.onDestroy()
        tcpServer.stop()
        disposables.clear()
    }

    override fun onBind(p0: Intent?): IBinder {
        Timber.d("Service bound")
        return binder
    }
}
