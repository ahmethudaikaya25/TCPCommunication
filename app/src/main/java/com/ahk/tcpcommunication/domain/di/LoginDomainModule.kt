package com.ahk.tcpcommunication.domain.di

import com.ahk.tcpcommunication.core.data.tcp.TCPServiceConnection
import com.ahk.tcpcommunication.domain.login.LoginRepository
import com.ahk.tcpcommunication.domain.login.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoginDomainModule {
    @Provides
    fun provideLoginRepository(tcpService: TCPServiceConnection): LoginRepository {
        return LoginRepositoryImpl(tcpService)
    }
}
