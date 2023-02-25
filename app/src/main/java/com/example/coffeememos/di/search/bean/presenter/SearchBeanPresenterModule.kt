package com.example.coffeememos.di.search.bean.presenter

import com.example.coffeememos.search.bean.domain.presenter.SearchBeanPresenter
import com.example.coffeememos.search.bean.presentation.presenter.SearchBeanPresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchBeanPresenterModule {

    @Binds
    abstract fun bindSearchBeanPresenter(
        searchBeanPresenterImpl: SearchBeanPresenterImpl
    ): SearchBeanPresenter
}