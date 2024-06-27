package com.withapp.coffeememo.domain.usecase.bean.freewordsearch

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.domain.model.bean.SearchBeanModel
import javax.inject.Inject

class FreeWordSearchInteractor @Inject constructor(
    private val beanRepo: BeanRepository,
) : FreeWordSearchUseCase {
    override suspend fun handle(freeWord: String): List<SearchBeanModel> {
        return beanRepo.getSearchBeanModelByKeyword(freeWord)
    }
}