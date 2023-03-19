package com.withapp.coffeememo.di.create.recipe.use_case

import com.withapp.coffeememo.create.recipe.domain.interactor.GetBeanCountInteractor
import com.withapp.coffeememo.create.recipe.domain.use_case.GetBeanCountUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetBeanCountUseCaseModule {

    @Binds
    abstract fun bindGetBeanCountUseCase(
        getBeanCountUseCaseImpl: GetBeanCountInteractor
    ): GetBeanCountUseCase
}