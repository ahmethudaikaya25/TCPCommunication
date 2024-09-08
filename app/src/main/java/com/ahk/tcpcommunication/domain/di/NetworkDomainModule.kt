package com.ahk.tcpcommunication.domain.di

import com.ahk.tcpcommunication.core.data.network.NetworkService
import com.ahk.tcpcommunication.domain.network.FetchLocalIpUseCase
import com.ahk.tcpcommunication.domain.network.NetworkRepository
import com.ahk.tcpcommunication.domain.network.NetworkRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkDomainModule {
    @Provides
    fun provideNetworkRepository(networkService: NetworkService): NetworkRepository {
        return NetworkRepositoryImpl(networkService)
    }

    @Provides
    fun provideFetchLocalIpUseCase(networkRepository: NetworkRepository): FetchLocalIpUseCase {
        return FetchLocalIpUseCase(networkRepository)
    }
}
