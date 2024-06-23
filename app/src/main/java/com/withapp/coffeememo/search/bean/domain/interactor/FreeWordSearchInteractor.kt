package com.withapp.coffeememo.search.bean.domain.interactor

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.search.bean.domain.presenter.SearchBeanPresenter
import com.withapp.coffeememo.search.bean.domain.use_case.FreeWordSearchUseCase
import javax.inject.Inject

class FreeWordSearchInteractor @Inject constructor(
    private val beanRepo: BeanRepository,
    private val freeWordSearchPresenter: SearchBeanPresenter
) : FreeWordSearchUseCase {
    override suspend fun handle(freeWord: String): List<SearchBeanModel> {
        val originBeanList: List<SearchBeanModel> =
            beanRepo.getSearchBeanModelByKeyword(freeWord)

        return freeWordSearchPresenter.presentFreeWordSearchRes(originBeanList)
    }
}