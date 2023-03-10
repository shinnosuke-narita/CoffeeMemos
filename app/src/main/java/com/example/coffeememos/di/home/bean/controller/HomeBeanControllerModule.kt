package com.example.coffeememos.di.home.bean.controller

import com.example.coffeememos.home.bean.presentation.controller.HomeBeanController
import com.example.coffeememos.home.bean.presentation.controller.HomeBeanControllerImpl
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