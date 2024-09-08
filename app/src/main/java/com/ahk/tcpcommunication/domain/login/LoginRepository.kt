package com.ahk.tcpcommunication.domain.login

import com.ahk.tcpcommunication.core.model.LoginUser
import io.reactivex.rxjava3.core.Completable

interface LoginRepository {
    fun login(loginUser: LoginUser): Completable
}
