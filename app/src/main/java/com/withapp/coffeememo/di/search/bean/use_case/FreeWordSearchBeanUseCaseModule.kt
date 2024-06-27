package com.withapp.coffeememo.di.search.bean.use_case

import com.withapp.coffeememo.domain.usecase.bean.freewordsearch.FreeWordSearchInteractor
import com.withapp.coffeememo.domain.usecase.bean.freewordsearch.FreeWordSearchUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FreeWordSearchBeanUseCaseModule {

    @Binds
    abstract fun bindFreeWordSearchRecipeUseCase(
        freeWordSearchUseCaseImpl: FreeWordSearchInteractor
    ): FreeWordSearchUseCase
}