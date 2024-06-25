package com.withapp.coffeememo.presentation.detail.bean

import androidx.lifecycle.*
import com.withapp.coffeememo.core.data.entity.Bean
import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.entity.Rating
import com.withapp.coffeememo.entity.Rating.Star
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeanDetailViewModel @Inject constructor(
    private val beanRepo: BeanRepository
) : ViewModel() {
    private lateinit var _beanRatingManager: Rating

    private val _selectedBean: MutableLiveData<Bean> = MutableLiveData()
    val selectedBean: LiveData<Bean> = _selectedBean

    val beanStarList: LiveData<List<Star>> = selectedBean.switchMap { bean ->
        val result = MutableLiveData<List<Star>>()
        _beanRatingManager.changeRatingState(bean.rating)
        result.value = _beanRatingManager.starList
        _beanCurrentRating.value = _beanRatingManager.currentRating

        result
    }

    private var _beanCurrentRating: MutableLiveData<Int> = MutableLiveData(1)
    val beanCurrentRating: LiveData<Int> = _beanCurrentRating

    fun initialize(beanId: Long, beanRatingManager: Rating) {
        _beanRatingManager  = beanRatingManager

        updateBean(beanId)
    }

    fun updateBean(id: Long) {
        viewModelScope.launch {
            _selectedBean.postValue(beanRepo.getBeanById(id))
        }
    }

    fun deleteRecipe() {
        viewModelScope.launch {
            beanRepo.deleteBeanById(_selectedBean.value!!.id)
        }
    }
}