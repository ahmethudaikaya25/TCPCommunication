package com.ahk.tcpcommunication.features.login

import androidx.lifecycle.ViewModel
import com.ahk.tcpcommunication.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    val uiState = ReplaySubject.createWithSize<LoginUIState>(1)
    val uiEvent = PublishSubject.create<LoginUIEvent>()
    private var lastSuccessStateValue: LoginUIState.Success? = null

    fun onLoginClicked() {
        val success = uiState.value ?: lastSuccessStateValue

        success?.getSuccessUser()?.let {
            setEvent(LoginUIEvent.LoginClicked(it))
        }
    }

    fun handleLoginClicked(user: User) {

    }

    fun setState(state: LoginUIState) {
        if (state is LoginUIState.Success) {
            lastSuccessStateValue = state
        }
        uiState.onNext(state)
    }

    fun setEvent(event: LoginUIEvent) {
        uiEvent.onNext(event)
    }
}
