package com.example.coffeememos.di.search.use_case

import com.example.coffeememos.search.recipe.domain.iterator.DeleteFilterRecipeInputDataIterator
import com.example.coffeememos.search.recipe.domain.use_case.DeleteFilterRecipeInputDataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DeleteFilterRecipeInputDataUseCaseModule {

    @Binds
    abstract fun bindDeleteFilterRecipeInputDataUseCase(
        deleteFilterRecipeInputData: DeleteFilterRecipeInputDataIterator
    ): DeleteFilterRecipeInputDataUseCase
}