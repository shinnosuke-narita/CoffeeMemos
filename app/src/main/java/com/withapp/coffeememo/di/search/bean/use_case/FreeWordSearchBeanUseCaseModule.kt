package com.withapp.coffeememo.di.search.bean.use_case

import com.withapp.coffeememo.search.bean.domain.iterator.FreeWordSearchIterator
import com.withapp.coffeememo.search.bean.domain.use_case.FreeWordSearchUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FreeWordSearchBeanUseCaseModule {

    @Binds
    abstract fun bindFreeWordSearchRecipeUseCase(
        freeWordSearchUseCaseImpl: FreeWordSearchIterator
    ): FreeWordSearchUseCase
}