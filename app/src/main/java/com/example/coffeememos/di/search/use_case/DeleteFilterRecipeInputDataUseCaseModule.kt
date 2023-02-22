package com.example.coffeememos.di.search.use_case

import com.example.coffeememos.search.domain.iterator.DeleteFilterRecipeInputDataIterator
import com.example.coffeememos.search.domain.iterator.FreeWordSearchIterator
import com.example.coffeememos.search.domain.use_case.DeleteFilterRecipeInputDataUseCase
import com.example.coffeememos.search.domain.use_case.FreeWordSearchUseCase
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