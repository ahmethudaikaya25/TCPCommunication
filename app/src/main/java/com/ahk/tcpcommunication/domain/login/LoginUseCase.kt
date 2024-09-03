package com.ahk.tcpcommunication.domain.login

import com.ahk.tcpcommunication.R
import com.ahk.tcpcommunication.core.model.ErrorCode
import com.ahk.tcpcommunication.core.model.ErrorModel
import com.ahk.tcpcommunication.core.model.LoginUser
import com.ahk.tcpcommunication.domain.util.DomainException
import com.ahk.tcpcommunication.domain.util.isIP
import com.ahk.tcpcommunication.domain.util.isPort
import io.reactivex.rxjava3.core.Completable

class LoginUseCase(
    private val loginRepository: LoginRepository,
) {
    fun invoke(loginUser: LoginUser): Completable {
        if (loginUser.name.isEmpty() || loginUser.ip.isEmpty() || loginUser.port == 0 || loginUser.password.isEmpty()) {
            val errorModel = ErrorModel(
                ErrorCode.INVALID_LOGIN_ARGUMENTS,
                R.string.invalid_login_arguments,
                R.string.error_all_fields_required_description,
                R.drawable.default_error_icon,
            )
            return Completable.error(DomainException.InvalidLoginArguments(errorModel))
        }

        if (!loginUser.ip.isIP()) {
            return Completable.error(
                DomainException.InvalidLoginArguments(
                    ErrorModel(
                        ErrorCode.INVALID_LOGIN_ARGUMENTS,
                        R.string.invalid_login_arguments,
                        R.string.error_invalid_ip_description,
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
                        R.string.invalid_login_arguments,
                        R.string.error_invalid_port_description,
                        R.drawable.default_error_icon,
                    ),
                ),
            )
        }

        return loginRepository.login(loginUser)
    }
}
