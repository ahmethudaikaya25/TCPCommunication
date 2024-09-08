package com.ahk.tcpcommunication.core.di

import android.content.Context
import android.net.ConnectivityManager
import com.ahk.tcpcommunication.core.data.network.LocalNetworkService
import com.ahk.tcpcommunication.core.data.network.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager? {
        if (context == null) {
            return null
        }
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) ?: return null

        if (connectivityManager !is ConnectivityManager) {
            return null
        }

        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    fun provideNetworkService(connectivityManager: ConnectivityManager?): NetworkService {
        return LocalNetworkService(connectivityManager)
    }
}
