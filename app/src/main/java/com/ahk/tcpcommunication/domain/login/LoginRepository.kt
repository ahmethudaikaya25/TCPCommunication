package com.ahk.tcpcommunication.domain.login

import com.ahk.tcpcommunication.core.model.LoginUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.subjects.PublishSubject

interface LoginRepository {
    fun login(loginUser: LoginUser): Completable

    fun listen(): PublishSubject<String>

    fun stopListenning()
}
