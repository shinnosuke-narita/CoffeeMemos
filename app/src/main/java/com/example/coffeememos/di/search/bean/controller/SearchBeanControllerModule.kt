package com.example.coffeememos.di.search.bean.controller

import com.example.coffeememos.search.bean.presentation.controller.SearchBeanController
import com.example.coffeememos.search.bean.presentation.controller.SearchBeanControllerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchBeanControllerModule {

    @Binds
    abstract fun bindSearchBeanController(
        searchBeanController: SearchBeanControllerImpl
    ): SearchBeanController
}