package com.withapp.coffeememo.viewModel

import androidx.lifecycle.*
import com.withapp.coffeememo.core.data.dao.BeanDao
import com.withapp.coffeememo.core.data.entity.CustomBean
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel

class SelectBeanViewModel(
    val beanDao: BeanDao
) : ViewModel() {
    val beanList: LiveData<List<SearchBeanModel>> = beanDao.getCustomBeanByFlow().asLiveData()

}

class SelectBeanViewModelFactory(private val beanDao: BeanDao)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectBeanViewModel::class.java)) {
            return SelectBeanViewModel(beanDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_NEWRECIPEVIEWMODEL")
    }
}