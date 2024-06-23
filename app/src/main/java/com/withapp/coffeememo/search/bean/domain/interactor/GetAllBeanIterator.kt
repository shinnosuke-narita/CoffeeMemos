package com.withapp.coffeememo.search.bean.domain.interactor

import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.search.bean.domain.presenter.SearchBeanPresenter
import com.withapp.coffeememo.search.bean.domain.repository.SearchBeanDiskRepository
import com.withapp.coffeememo.search.bean.domain.use_case.GetAllBeanUseCase
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