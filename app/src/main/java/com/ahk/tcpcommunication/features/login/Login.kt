package com.ahk.tcpcommunication.features.login

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ahk.tcpcommunication.TCPServiceData
import com.ahk.tcpcommunication.TCPServiceDataCallback
import com.ahk.tcpcommunication.core.data.TCPService
import com.ahk.tcpcommunication.core.model.ServerModel
import com.ahk.tcpcommunication.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import timber.log.Timber

@AndroidEntryPoint
class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val disposables = mutableListOf<CompositeDisposable>()

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Timber.d("Service connected")

            val tcpServiceData = TCPServiceData.Stub.asInterface(service)
            tcpServiceData.messageReceive(object : TCPServiceDataCallback.Stub() {
                override fun onMessageReceived(message: ByteArray?) {
                    Timber.d("Message received: ${message?.contentToString()}")
                }

                override fun onErrorOccurred(error: String?) {
                    Timber.e("Error occurred: $error")
                }
            })
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Timber.d("Service disconnected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        Timber.d("Login fragment created")
        observeUIState()
        observeUIEvent()
        binding.connect.setOnClickListener {
            Timber.d("Connect button clicked")
            val serverModel = ServerModel(
                ip = "127.0.0.1",
                port = 2123,
            )
            val service = Intent(requireContext(), TCPService::class.java).apply {
                putExtra("serverModel", serverModel)
            }
            requireContext().startService(service)
            Timber.d("TCP server service is started")
        }
        return binding.root
    }

    private fun bindService() {
        val service = Intent(requireContext(), TCPService::class.java)
        requireContext().bindService(service, serviceConnection, 0)
        Timber.d("TCP server service is bound")
    }

    private fun observeUIState() {
        val compositeDisposable = CompositeDisposable()
        viewModel.uiState.observeOn(AndroidSchedulers.mainThread()).subscribe(::onUIStateChange)
            .addTo(compositeDisposable)
        disposables.add(compositeDisposable)
    }

    private fun onUIStateChange(state: LoginUIState) {
        Timber.d("UI State: $state")
    }

    private fun observeUIEvent() {
        val compositeDisposable = CompositeDisposable()
        viewModel.uiEvent.observeOn(AndroidSchedulers.mainThread()).subscribe(::onUIEventChanged)
            .addTo(compositeDisposable)
        disposables.add(compositeDisposable)
    }

    private fun onUIEventChanged(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.LoginClicked -> {
                viewModel.handleLoginClicked(event.user)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.forEach { it.clear() }
    }
}
