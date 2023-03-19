package com.withapp.coffeememo.di.search.recipe.use_case

import com.withapp.coffeememo.search.recipe.domain.iterator.GetAllRecipeIterator
import com.withapp.coffeememo.search.recipe.domain.use_case.GetAllRecipeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetAllRecipeUseCaseModule {

    @Binds
    abstract fun bindGetAllRecipeUseCase(
        GetAllRecipeUseCaseImpl: GetAllRecipeIterator
    ): GetAllRecipeUseCase
}