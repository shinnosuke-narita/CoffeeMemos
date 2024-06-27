package com.withapp.coffeememo.di.home.recipe.use_case

import com.withapp.coffeememo.domain.usecase.recipe.gethomerecipe.GetHomeRecipeDataUseCaseInteractor
import com.withapp.coffeememo.domain.usecase.recipe.gethomerecipe.GetHomeRecipeDataUseCase
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