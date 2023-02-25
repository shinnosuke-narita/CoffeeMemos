package com.example.coffeememos.di.search.recipe.serializer

import com.example.coffeememos.search.bean.domain.serialization.BeanSerializer
import com.example.coffeememos.search.bean.domain.serialization.BeanSerializerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class BeanSerializerModule {

    @Binds
    abstract fun bindBeanSerializer(
        beanSerializerImpl: BeanSerializerImpl
    ): BeanSerializer
}