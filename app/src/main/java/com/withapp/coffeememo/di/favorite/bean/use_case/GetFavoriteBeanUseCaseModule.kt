package com.withapp.coffeememo.di.favorite.bean.use_case


import com.withapp.coffeememo.domain.usecase.bean.getfavoritebean.GetFavoriteBeanInteractor
import com.withapp.coffeememo.domain.usecase.bean.getfavoritebean.GetFavoriteBeanUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetFavoriteBeanUseCaseModule {

    @Binds
    abstract fun bindFavoriteBeanUseCase(
        getFavoriteRecipeUseCase: GetFavoriteBeanInteractor
    ): GetFavoriteBeanUseCase
}