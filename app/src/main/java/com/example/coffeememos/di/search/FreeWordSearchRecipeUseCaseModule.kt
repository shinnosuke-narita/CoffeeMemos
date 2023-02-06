package com.example.coffeememos.di.search

import com.example.coffeememos.search.domain.interator.FreeWordSearchIterator
import com.example.coffeememos.search.domain.use_case.FreeWordSearchUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FreeWordSearchRecipeUseCaseModule {

    @Binds
    abstract fun bindFreeWordSearchRecipeUseCase(
        freeWordSearchUseCaseImpl: FreeWordSearchIterator
    ): FreeWordSearchUseCase
}