package com.yunhan.presentation.di.navigator

import com.yunhan.presentation.impl.navigator.ActivityNavigatorImpl
import com.yunhan.presentation.util.navigator.ActivityNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
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

//@Module
//@InstallIn(SingletonComponent::class)
//interface ActivityNavigatorModule {
//    @Binds
//    @Singleton
//    fun bindActivityNavigator(impl: ActivityNavigatorImpl): ActivityNavigator
//}

//@Module
//@InstallIn(SingletonComponent::class)
//object ActivityNavigatorModule {
//
//    @Provides
//    @Singleton
//    fun provideActivityNavigator(): ActivityNavigator {
//        return ActivityNavigatorImpl()
//    }
//}