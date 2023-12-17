package com.withapp.coffeememo.di.home.bean.controller

import com.withapp.coffeememo.home.bean.presentation.controller.HomeBeanController
import com.withapp.coffeememo.home.bean.presentation.controller.HomeBeanControllerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeBeanControllerModule {

    @Binds
    abstract fun bindHomeBeanController(
        homeBeanControllerImpl: HomeBeanControllerImpl
    ): HomeBeanController
}