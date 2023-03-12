package com.example.coffeememos.di.create.recipe.use_case

import com.example.coffeememos.create.recipe.domain.interactor.CreateRecipeAndTasteInteractor
import com.example.coffeememos.create.recipe.domain.use_case.CreateRecipeAndTasteUseCase
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