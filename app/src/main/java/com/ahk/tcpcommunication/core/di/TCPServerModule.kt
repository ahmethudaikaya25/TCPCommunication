package com.ahk.tcpcommunication.core.di

import android.content.Context
import com.ahk.tcpcommunication.core.data.tcp.ITCPServiceConnection
import com.ahk.tcpcommunication.core.data.tcp.TCPServiceConnection
import com.ahk.tcpserver.server.ITCPServer
import com.ahk.tcpserver.server.TCPServer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TCPServerModule {
    @Provides
    @Singleton
    fun provideTCPServer(): TCPServer {
        return ITCPServer()
    }

    @Provides
    @Singleton
    fun provideTCPServiceConnection(
        @ApplicationContext context: Context,
    ): TCPServiceConnection {
        return ITCPServiceConnection(context)
    }
}
