package com.withapp.coffeememo.domain.interactor.bean

import com.withapp.coffeememo.domain.usecase.bean.GetBeanCountUseCase
import com.withapp.coffeememo.domain.repository.BeanRepository
import javax.inject.Inject

class GetBeanCountInteractor @Inject constructor(
    private val beanRepo: BeanRepository
) : GetBeanCountUseCase {
    override suspend fun handle(): Int {
        return beanRepo.getBeanCount()
    }
}