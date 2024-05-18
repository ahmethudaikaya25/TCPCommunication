package com.ahk.tcpcommunication.core.di

import com.ahk.tcpcommunication.core.data.ITCPServer
import com.ahk.tcpcommunication.core.data.TCPServer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TCPServerDI {
    @Provides
    @Singleton
    fun provideTCPServer(): TCPServer {
        return ITCPServer()
    }
}
