package com.ahk.tcpcommunication.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        Timber.d("Login fragment created")
        observeUIState()
        observeUIEvent()
        return binding.root
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
                viewModel.handleLoginClicked(event.loginUser)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.forEach { it.clear() }
    }
}
