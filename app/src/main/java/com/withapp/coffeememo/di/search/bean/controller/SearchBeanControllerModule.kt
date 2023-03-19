package com.withapp.coffeememo.di.search.bean.controller

import com.withapp.coffeememo.search.bean.presentation.controller.SearchBeanController
import com.withapp.coffeememo.search.bean.presentation.controller.SearchBeanControllerImpl
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