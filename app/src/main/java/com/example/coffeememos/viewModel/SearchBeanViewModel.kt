package com.example.coffeememos.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coffeememos.dao.BeanDao

class SearchBeanViewModel(beanDao: BeanDao) : ViewModel() {


    class SearchBeanViewModelFactory(
        private val beanDao: BeanDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchBeanViewModel::class.java)) {
                return SearchBeanViewModel(beanDao) as T
            }
            throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
        }
    }
}