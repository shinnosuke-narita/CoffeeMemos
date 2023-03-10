package com.example.coffeememos.di.home.bean.mapper

import com.example.coffeememos.home.bean.data.mapper.HomeBeanModelMapper
import com.example.coffeememos.home.bean.data.mapper.HomeBeanModelMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeBeanModelMapperModule {

    @Binds
    abstract fun bindHomeBeanModelMapper(
        homeBeanModelMapperImpl: HomeBeanModelMapperImpl
    ): HomeBeanModelMapper
}