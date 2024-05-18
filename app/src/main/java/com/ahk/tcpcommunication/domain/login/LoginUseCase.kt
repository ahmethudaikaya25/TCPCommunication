package com.ahk.tcpcommunication.domain.login

import com.ahk.tcpcommunication.R
import com.ahk.tcpcommunication.core.model.ErrorCode
import com.ahk.tcpcommunication.core.model.ErrorModel
import com.ahk.tcpcommunication.core.model.User
import com.ahk.tcpcommunication.domain.util.DomainException
import io.reactivex.rxjava3.core.Single

class LoginUseCase(
    val loginRepository: LoginRepository,
) {
    fun invoke(user: User): Single<User> {
        if (user.name.isEmpty() || user.ip.isEmpty() || user.port == 0 || user.password.isEmpty()) {
            val errorModel = ErrorModel(
                ErrorCode.INVALID_LOGIN_ARGUMENTS,
                R.string.invalid_login_arguments,
                R.string.error_all_fields_required_description,
                R.drawable.default_error_icon,
            )
            return Single.error(DomainException.InvalidLoginArguments(errorModel))
        }

        return Single.just(user)
    }
}
