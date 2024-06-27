package com.withapp.coffeememo.domain.usecase.bean.getall

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.domain.model.bean.SearchBeanModel
import javax.inject.Inject

class GetAllBeanInteractor @Inject constructor(
    private var beanRepo: BeanRepository,
) : GetAllBeanUseCase {
    override suspend fun getAllBean(): List<SearchBeanModel> {
       return beanRepo.getSearchBeanModel()
    }
}