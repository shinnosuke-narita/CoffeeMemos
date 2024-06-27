package com.withapp.coffeememo.domain.usecase.bean.getbeancount

interface GetBeanCountUseCase {
    suspend fun handle(): Int
}