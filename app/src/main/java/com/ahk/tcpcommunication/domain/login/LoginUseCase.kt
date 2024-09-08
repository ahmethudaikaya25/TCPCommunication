package com.ahk.tcpcommunication.domain.login

import com.ahk.tcpcommunication.R
import com.ahk.tcpcommunication.core.model.DataException
import com.ahk.tcpcommunication.base.model.ErrorCode
import com.ahk.tcpcommunication.base.model.ErrorModel
import com.ahk.tcpcommunication.core.model.LoginUser
import com.ahk.tcpcommunication.domain.network.NetworkRepository
import com.ahk.tcpcommunication.domain.util.DomainException
import com.ahk.tcpcommunication.domain.util.isIP
import com.ahk.tcpcommunication.domain.util.isPort
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val networkRepository: NetworkRepository,
) {
    fun invoke(loginUser: LoginUser): Completable =
        networkRepository.isWifiConnected().flatMapCompletable {
            if (!it) {
                return@flatMapCompletable Completable.error(
                    DataException.ConnectionError(
                        ErrorModel(
                            ErrorCode.CONNECTION_ERROR,
                            R.string.Connection_Error,
                            R.string.There_Is_No_Wifi_Connection,
                            R.drawable.no_wifi_icon,
                        ),
                    ),
                )
            }

            if (loginUser.name.isEmpty() || loginUser.ip.isEmpty() || loginUser.port == 0 || loginUser.password.isEmpty()) {
                val errorModel = ErrorModel(
                    ErrorCode.INVALID_LOGIN_ARGUMENTS,
                    R.string.Invalid_Login_Arguments,
                    R.string.Error_All_Fields_Required_Description,
                    R.drawable.default_error_icon,
                )
                return@flatMapCompletable Completable.error(
                    DomainException.InvalidLoginArguments(
                        errorModel,
                    ),
                )
            }

            if (!loginUser.ip.isIP()) {
                return@flatMapCompletable Completable.error(
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
                return@flatMapCompletable Completable.error(
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

            return@flatMapCompletable loginRepository.login(loginUser)
        }
}
