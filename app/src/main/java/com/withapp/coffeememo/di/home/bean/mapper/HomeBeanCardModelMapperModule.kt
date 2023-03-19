package com.withapp.coffeememo.di.home.bean.mapper

import com.withapp.coffeememo.home.bean.presentation.mapper.HomeBeanCardModelMapper
import com.withapp.coffeememo.home.bean.presentation.mapper.HomeBeanCardModelMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeBeanCardModelMapperModule {

    @Binds
    abstract fun bindHomeBeanCardDataMapper(
        homeBeanCardMapperImpl: HomeBeanCardModelMapperImpl
    ): HomeBeanCardModelMapper
}