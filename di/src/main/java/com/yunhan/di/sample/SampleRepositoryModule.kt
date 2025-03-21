package com.yunhan.di.sample

import com.yunhan.data.sample.repository.SampleRepositoryImpl
import com.yunhan.domain.sample.repository.SampleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SampleRepositoryModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun bindSampleRepository(repository: SampleRepositoryImpl): SampleRepository
    }
}