package com.example.coffeememos.di.create.recipe.converter

import com.example.coffeememos.create.recipe.presentation.converter.TimeConverter
import com.example.coffeememos.create.recipe.presentation.converter.TimeConverterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TimeConverterModule {

    @Binds
    abstract fun bindTimeConverter(
        timeConverterImpl: TimeConverterImpl
    ): TimeConverter
}