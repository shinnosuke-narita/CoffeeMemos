package com.withapp.coffeememo.di.home.bean.presenter

import com.withapp.coffeememo.home.bean.presentation.presenter.HomeBeanPresenter
import com.withapp.coffeememo.home.bean.presentation.presenter.HomeBeanPresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeBeanPresenterModule {

    @Binds
    abstract fun bindHomeBeanPresenter(
        homeBeanPresenterImpl: HomeBeanPresenterImpl
    ): HomeBeanPresenter
}