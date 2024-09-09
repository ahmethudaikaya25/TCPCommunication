package com.ahk.tcpcommunication.domain.login

import com.ahk.tcpcommunication.R
import com.ahk.tcpcommunication.base.model.ErrorCode
import com.ahk.tcpcommunication.base.model.ErrorModel
import com.ahk.tcpcommunication.core.model.LoginUser
import com.ahk.tcpcommunication.domain.network.NetworkRepository
import com.ahk.tcpcommunication.domain.util.DomainException
import com.ahk.tcpcommunication.domain.util.isIP
import com.ahk.tcpcommunication.domain.util.isPort
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val networkRepository: NetworkRepository,
) {
    private var disposable: CompositeDisposable = CompositeDisposable()

    fun invoke(loginUser: LoginUser): PublishSubject<String> {
        val publishSubject = PublishSubject.create<String>()
        with(networkRepository) {
            disposable.add(
                isWifiConnected()
                    .andThen(checkLoginInformation(loginUser))
                    .andThen(loginRepository.login(loginUser))
                    .subscribe({
                        disposable.add(
                            loginRepository.listen().subscribe({
                                publishSubject.onNext(it)
                            }, {
                                publishSubject.onError(it)
                            }),
                        )
                    }, {
                        publishSubject.onError(it)
                    }),
            )
            return publishSubject
        }
    }

    private fun checkLoginInformation(loginUser: LoginUser): Completable {
        if (loginUser.name.isEmpty() || loginUser.ip.isEmpty() || loginUser.port == 0 || loginUser.password.isEmpty()) {
            val errorModel = ErrorModel(
                ErrorCode.INVALID_LOGIN_ARGUMENTS,
                R.string.Invalid_Login_Arguments,
                R.string.Error_All_Fields_Required_Description,
                R.drawable.default_error_icon,
            )
            return Completable.error(
                DomainException.InvalidLoginArguments(
                    errorModel,
                ),
            )
        }

        if (!loginUser.ip.isIP()) {
            return Completable.error(
                DomainException.InvalidLoginArguments(
                    ErrorModel(
                        ErrorCode.INVALID_LOGIN_ARGUMENTS,
                        R.string.Invalid_Login_Arguments,
                        R.string.Error_Invalid_Ip_Description,
                        R.drawable.default_error_icon,
                    ),
                ),
            )
        }
        if (!loginUser.port.isPort()) {
            return Completable.error(
                DomainException.InvalidLoginArguments(
                    ErrorModel(
                        ErrorCode.INVALID_LOGIN_ARGUMENTS,
                        R.string.Invalid_Login_Arguments,
                        R.string.Error_Invalid_Port_Description,
                        R.drawable.default_error_icon,
                    ),
                ),
            )
        }
        return Completable.complete()
    }

    fun clear() {
        disposable.dispose()
    }
}
