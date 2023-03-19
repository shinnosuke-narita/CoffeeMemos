package com.withapp.coffeememo.di.search.bean.presenter

import com.withapp.coffeememo.search.bean.domain.presenter.SearchBeanPresenter
import com.withapp.coffeememo.search.bean.presentation.presenter.SearchBeanPresenterImpl
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