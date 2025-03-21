package com.yunhan.di.navigator

import com.yunhan.presentation.impl.navigator.ActivityNavigatorImpl
import com.yunhan.presentation.util.navigator.ActivityNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ActivityNavigatorModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun bindActivityNavigator(impl: ActivityNavigatorImpl): ActivityNavigator

    }
}