package com.withapp.coffeememo.presentation.selectbean

import androidx.lifecycle.*
import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectBeanViewModel @Inject constructor(
    private val beanRepo: BeanRepository
) : ViewModel() {
    val beanList: LiveData<List<SearchBeanModel>> = beanRepo.getCustomBeanByFlow().asLiveData()
}