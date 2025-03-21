package com.yunhan.di.sample

import com.yunhan.data.sample.datasource.SampleDataSource
import com.yunhan.data.sample.datasource.SampleDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SampleDataSourceModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun bindSampleDataSource(
            dataSource: SampleDataSourceImpl
        ): SampleDataSource
    }
}