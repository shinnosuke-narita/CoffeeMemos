package com.withapp.coffeememo.di.search.recipe.use_case

import com.withapp.coffeememo.search.recipe.domain.interactor.DeleteFilterRecipeInputDataIterator
import com.withapp.coffeememo.search.recipe.domain.use_case.DeleteFilterRecipeInputDataUseCase
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