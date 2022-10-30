package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.manager.RatingManager.*

class BeanDetailViewModel(private val beanDao: BeanDao) : ViewModel() {
    private lateinit var _beanRatingManager: RatingManager

    private val _beanId: MutableLiveData<Long> = MutableLiveData(0)

    val selectedBean: LiveData<Bean> = _beanId.switchMap { beanId ->
        liveData{
            emit(beanDao.getBeanById(beanId))
        }
    }

    val beanStarList: LiveData<List<Star>> = selectedBean.switchMap { bean ->
        val result = MutableLiveData<List<Star>>()
        _beanRatingManager.changeRatingState(bean.rating)
        result.value = _beanRatingManager.starList
        _beanCurrentRating.value = _beanRatingManager.currentRating

        result
    }

    private var _recipeCurrentRating: MutableLiveData<Int> = MutableLiveData(1)
    val recipeCurrentRating: LiveData<Int> = _recipeCurrentRating

    private var _beanCurrentRating: MutableLiveData<Int> = MutableLiveData(1)
    val beanCurrentRating: LiveData<Int> = _beanCurrentRating

    fun initialize(beanId: Long, beanRatingManager: RatingManager) {
        _beanId.value         = beanId
        _beanRatingManager    = beanRatingManager
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