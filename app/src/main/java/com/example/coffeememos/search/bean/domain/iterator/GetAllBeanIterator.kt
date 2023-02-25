package com.example.coffeememos.search.bean.domain.iterator

import com.example.coffeememos.search.bean.domain.model.SearchBeanModel
import com.example.coffeememos.search.bean.domain.presenter.SearchBeanPresenter
import com.example.coffeememos.search.bean.domain.repository.SearchBeanDiskRepository
import com.example.coffeememos.search.bean.domain.use_case.GetAllBeanUseCase
import javax.inject.Inject

class GetAllBeanIterator @Inject constructor()
    : GetAllBeanUseCase {
    @Inject
    lateinit var searchBeanRepository: SearchBeanDiskRepository
    @Inject
    lateinit var searchBeanPresenter: SearchBeanPresenter

    override suspend fun getAllBean(): List<SearchBeanModel> {
       val beans = searchBeanRepository.getAllBean()
        return searchBeanPresenter.presentAllBeans(beans)
    }
}