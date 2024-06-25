package com.withapp.coffeememo.domain.usecase.bean

interface GetBeanCountUseCase {
    suspend fun handle(): Int
}