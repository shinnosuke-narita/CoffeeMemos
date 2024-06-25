package com.withapp.coffeememo.di.create.recipe.use_case

import com.withapp.coffeememo.domain.interactor.recipe.CreateRecipeAndTasteInteractor
import com.withapp.coffeememo.domain.usecase.recipe.CreateRecipeAndTasteUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CreateRecipeAndTasteUseCaseModule {

    @Binds
    abstract fun bindCreateRecipeAndTasteUseCase(
        createRecipeAndTasteUseCaseImpl: CreateRecipeAndTasteInteractor
    ): CreateRecipeAndTasteUseCase
}