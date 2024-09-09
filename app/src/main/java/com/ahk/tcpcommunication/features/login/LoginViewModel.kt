package com.ahk.tcpcommunication.features.login

import androidx.lifecycle.ViewModel
import com.ahk.tcpcommunication.base.util.toCustomException
import com.ahk.tcpcommunication.core.model.LoginUser
import com.ahk.tcpcommunication.domain.login.LoginUseCase
import com.ahk.tcpcommunication.domain.login.StopListeningUseCase
import com.ahk.tcpcommunication.domain.network.FetchLocalIpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val ipUseCase: FetchLocalIpUseCase,
    private val stopListeningUseCase: StopListeningUseCase,
) : ViewModel() {
    val uiState = ReplaySubject.createWithSize<LoginUIState>(1)
    val uiEvent = PublishSubject.create<LoginUIEvent>()
    var lastSuccessStateValue: LoginUIState.Success? = null
    private var compositeDisposable = CompositeDisposable()

    fun startState() {
        ipUseCase.invoke().subscribe(
            {
                setState(LoginUIState.Success(LoginUser("", it, 0, "")))
            },
            {
                setState(LoginUIState.Error(it.toCustomException(it).errorModel))
            },
            compositeDisposable,
        )
    }

    fun handleLoginClicked(loginUser: LoginUser) {
        if (uiState.value is LoginUIState.Loading) {
            return
        }
        compositeDisposable.add(
            loginUseCase.invoke(loginUser).subscribe(
                {
                    Timber.d("Message has come: $it")
                    setState(LoginUIState.Success(loginUser))
                },
                {
                    setState(LoginUIState.Error(it.toCustomException(it).errorModel))
                },
            ),
        )
    }

    fun onLoginClicked() {
        val loginUser = if (uiState.value is LoginUIState.Success) {
            (uiState.value as LoginUIState.Success).loginUser
        } else if (lastSuccessStateValue != null) {
            lastSuccessStateValue!!.loginUser
        } else {
            LoginUser("", "", 0, "ß")
        }

        setEvent(LoginUIEvent.LoginClicked(loginUser))
    }

    fun setState(state: LoginUIState) {
        if (state is LoginUIState.Success) {
            lastSuccessStateValue = state
        }
        uiState.onNext(state)
    }

    private fun setEvent(event: LoginUIEvent) {
        uiEvent.onNext(event)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        loginUseCase.clear()
        super.onCleared()
    }
}
