package com.withapp.coffeememo.search.bean.domain.interactor

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.search.bean.domain.presenter.SearchBeanPresenter
import com.withapp.coffeememo.search.bean.domain.use_case.GetAllBeanUseCase
import javax.inject.Inject

class GetAllBeanInteractor @Inject constructor(
    private var beanRepo: BeanRepository,
    private var searchBeanPresenter: SearchBeanPresenter
) : GetAllBeanUseCase {
    override suspend fun getAllBean(): List<SearchBeanModel> {
       val beans = beanRepo.getSearchBeanModel()
        return searchBeanPresenter.presentAllBeans(beans)
    }
}