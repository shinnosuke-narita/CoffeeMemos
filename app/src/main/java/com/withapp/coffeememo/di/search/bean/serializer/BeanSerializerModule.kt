package com.withapp.coffeememo.di.search.recipe.serializer

import com.withapp.coffeememo.domain.serialization.bean.BeanSerializer
import com.withapp.coffeememo.domain.serialization.bean.BeanSerializerImpl
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