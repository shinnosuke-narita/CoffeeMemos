package com.example.coffeememos.di.search.use_case

import com.example.coffeememos.search.recipe.domain.iterator.UpdateFavoriteIterator
import com.example.coffeememos.search.recipe.domain.use_case.UpdateFavoriteUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UpdateFavoriteUseCaseModule {

    @Binds
    abstract fun bindUpdateFavoriteUseCase(
        updateFavoriteUseCaseImpl : UpdateFavoriteIterator
    ): UpdateFavoriteUseCase
}