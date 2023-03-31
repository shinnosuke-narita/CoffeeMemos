package com.withapp.coffeememo.search.bean.domain.iterator

import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.search.bean.domain.presenter.SearchBeanPresenter
import com.withapp.coffeememo.search.bean.domain.repository.SearchBeanDiskRepository
import com.withapp.coffeememo.search.bean.domain.use_case.FreeWordSearchUseCase
import javax.inject.Inject

class FreeWordSearchIterator @Inject constructor()
    : FreeWordSearchUseCase {
    @Inject
    lateinit var freeWordSearchRepository: SearchBeanDiskRepository
    @Inject
    lateinit var freeWordSearchPresenter: SearchBeanPresenter

    override suspend fun handle(freeWord: String): List<SearchBeanModel> {
        val originBeanList: List<SearchBeanModel> =
            freeWordSearchRepository.searchBeanByFreeWord(freeWord)

        return freeWordSearchPresenter.presentFreeWordSearchRes(originBeanList)
    }
}