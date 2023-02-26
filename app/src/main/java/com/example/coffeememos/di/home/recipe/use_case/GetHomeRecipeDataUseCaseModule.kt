package com.example.coffeememos.di.home.recipe.use_case

import com.example.coffeememos.home.recipe.domain.interactor.GetHomeRecipeDataUseCaseInteractor
import com.example.coffeememos.home.recipe.domain.repository.StorageRepository
import com.example.coffeememos.home.recipe.domain.use_case.GetHomeRecipeDataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetHomeRecipeDataUseCaseModule {

    @Binds
    abstract fun bindGetHomeRecipeUseCase(
        getHomeRecipeDataImpl: GetHomeRecipeDataUseCaseInteractor
    ): GetHomeRecipeDataUseCase
}