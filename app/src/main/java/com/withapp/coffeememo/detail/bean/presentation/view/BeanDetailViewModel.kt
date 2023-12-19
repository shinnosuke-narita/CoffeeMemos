package com.withapp.coffeememo.detail.bean.presentation.view

import androidx.lifecycle.*
import com.withapp.coffeememo.core.data.dao.BeanDao
import com.withapp.coffeememo.core.data.entity.Bean
import com.withapp.coffeememo.entity.Rating
import com.withapp.coffeememo.entity.Rating.Star
import kotlinx.coroutines.launch

class BeanDetailViewModel(private val beanDao: BeanDao) : ViewModel() {
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
            _selectedBean.postValue(beanDao.getBeanById(id))
        }
    }

    fun deleteRecipe() {
        viewModelScope.launch {
            beanDao.deleteBeanById(_selectedBean.value!!.id)
        }
    }

}



class BeanDetailViewModelFactory(
    private val beanDao  : BeanDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeanDetailViewModel::class.java)) {
            return BeanDetailViewModel(beanDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
    }
}