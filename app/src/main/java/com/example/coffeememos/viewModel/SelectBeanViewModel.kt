package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.Bean
import kotlinx.coroutines.launch

class SelectBeanViewModel(
    val beanDao: BeanDao
) : ViewModel() {
    private var _beanList: MutableLiveData<List<Bean>> = MutableLiveData(listOf())
    val beanList: LiveData<List<Bean>> = _beanList

    init {
        viewModelScope.launch {
            _beanList.postValue(beanDao.getAll())
        }
    }
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