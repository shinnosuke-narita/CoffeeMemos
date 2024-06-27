package com.withapp.coffeememo.di.home.bean.mapper

import com.withapp.coffeememo.data.mapper.bean.HomeBeanModelMapper
import com.withapp.coffeememo.data.mapper.bean.impl.HomeBeanModelMapperImpl
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