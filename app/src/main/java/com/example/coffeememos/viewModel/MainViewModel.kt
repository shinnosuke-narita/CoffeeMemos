package com.example.coffeememos.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeememos.entity.Bean

class MainViewModel : ViewModel() {
    private val _selectedBean: MutableLiveData<Bean> = MutableLiveData()
    val selectedBean: LiveData<Bean> = _selectedBean

    fun setBean(bean: Bean) {
        _selectedBean.value = bean
    }
}