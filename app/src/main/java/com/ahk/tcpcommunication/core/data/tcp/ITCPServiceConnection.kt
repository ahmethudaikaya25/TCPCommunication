package com.ahk.tcpcommunication.core.data.tcp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.ahk.tcpcommunication.R
import com.ahk.tcpcommunication.core.model.DataException
import com.ahk.tcpcommunication.base.model.ErrorCode
import com.ahk.tcpcommunication.base.model.ErrorModel
import com.ahk.tcpserver.model.ServerModel
import com.ahk.tcpserver.service.TCPService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class ITCPServiceConnection @Inject constructor(
    private val context: Context,
) : TCPServiceConnection {
    private val messageSubject = PublishSubject.create<ByteArray>()

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Timber.d("Service connected")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Timber.d("Service disconnected")
        }
    }

    private fun bindService() {
        val service = Intent(context, TCPService::class.java)
        context.bindService(service, serviceConnection, 0)
        Timber.d("TCP server service is bound")
    }

    override fun connect(serverModel: ServerModel): Completable {
        return try {
            val service = Intent(context, TCPService::class.java).apply {
                putExtra("serverModel", serverModel)
            }
            context.startService(service)?.let {
                Timber.d("TCP server service is started")
                Completable.complete()
            } ?: Completable.error(
                DataException.ConnectionError(
                    ErrorModel(
                        ErrorCode.CONNECTION_ERROR,
                        R.string.Connection_Error,
                        R.string.TCP_Server_Could_Not_Be_Started,
                        R.drawable.default_error_icon,
                    ),
                ),
            )
        } catch (e: Exception) {
            Timber.e(e)
            Completable.error(
                DataException.ConnectionError(
                    ErrorModel(
                        ErrorCode.CONNECTION_ERROR,
                        R.string.Connection_Error,
                        R.string.TCP_Server_Could_Not_Be_Started,
                        R.drawable.default_error_icon,
                    ),
                ),
            )
        }
    }

    override fun listen(): PublishSubject<ByteArray> {
        try {
            bindService()
        } catch (e: Exception) {
            Timber.e(e)
            messageSubject.onError(e)
        }
        return messageSubject
    }

    override fun disconnect(): Completable {
        return Completable.create {
            context.unbindService(serviceConnection)
            context.stopService(Intent(context, TCPService::class.java))
            Timber.d("TCP server service is unbound")
            it.onComplete()
        }
    }
}
