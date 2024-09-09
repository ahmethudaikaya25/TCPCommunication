package com.ahk.tcpcommunication.domain.login

import com.ahk.tcpcommunication.core.data.tcp.TCPServiceConnection
import com.ahk.tcpcommunication.core.model.LoginUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

class LoginRepositoryImpl(
    private val tcpServiceConnection: TCPServiceConnection,
) : LoginRepository {
    private var disposable: CompositeDisposable = CompositeDisposable()

    override fun login(loginUser: LoginUser): Completable =
        tcpServiceConnection.connect(loginUser.getServerModel())

    override fun listen(): PublishSubject<String> {
        val strRetVal: PublishSubject<String> = PublishSubject.create()
        disposable.add(
            tcpServiceConnection.listen().subscribe({
                strRetVal.onNext(it.decodeToString())
            }, {
                strRetVal.onError(it)
            }),
        )
        return strRetVal
    }

    override fun stopListenning() {
        tcpServiceConnection.disconnect()
        disposable.dispose()
    }
}
