package com.withapp.coffeememo.di.create.recipe.converter

import com.withapp.coffeememo.presentation.create.recipe.converter.TimeConverter
import com.withapp.coffeememo.presentation.create.recipe.converter.TimeConverterImpl
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