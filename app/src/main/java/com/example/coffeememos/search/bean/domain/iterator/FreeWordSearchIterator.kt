package com.example.coffeememos.search.bean.domain.iterator

import com.example.coffeememos.search.bean.domain.model.SearchBeanModel
import com.example.coffeememos.search.bean.domain.presenter.SearchBeanPresenter
import com.example.coffeememos.search.bean.domain.repository.SearchBeanDiskRepository
import com.example.coffeememos.search.bean.domain.use_case.FreeWordSearchUseCase
import javax.inject.Inject

class FreeWordSearchIterator @Inject constructor()
    : FreeWordSearchUseCase {
    @Inject
    lateinit var freeWordSearchRepository: SearchBeanDiskRepository
    @Inject
    lateinit var freeWordSearchPresenter: SearchBeanPresenter

    override suspend fun handle(freeWord: String): List<SearchBeanModel> {
        val originBeanList: List<SearchBeanModel> = freeWordSearchRepository.searchBeanByFreeWord(freeWord)

        return freeWordSearchPresenter.presentFreeWordSearchRes(originBeanList)
    }
}