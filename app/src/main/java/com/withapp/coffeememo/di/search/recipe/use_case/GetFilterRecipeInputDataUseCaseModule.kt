package com.withapp.coffeememo.di.search.recipe.use_case

import com.withapp.coffeememo.domain.usecase.recipe.getfilterelement.GetFilterRecipeInputDataInteractor
import com.withapp.coffeememo.domain.usecase.recipe.getfilterelement.GetFilterRecipeOutputDataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetFilterRecipeInputDataUseCaseModule {

    @Binds
    abstract fun bindGetFilterRecipeInputDataUseCase(
        getFilterRecipeInputDataImpl: GetFilterRecipeInputDataInteractor
    ): GetFilterRecipeOutputDataUseCase
}