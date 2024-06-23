package com.withapp.coffeememo.create.recipe.domain.interactor

import com.withapp.coffeememo.create.recipe.domain.use_case.GetBeanCountUseCase
import com.withapp.coffeememo.domain.repository.BeanRepository
import javax.inject.Inject

class GetBeanCountInteractor @Inject constructor(
    private val beanRepo: BeanRepository
) : GetBeanCountUseCase {
    override suspend fun handle(): Int {
        return beanRepo.getBeanCount()
    }
}