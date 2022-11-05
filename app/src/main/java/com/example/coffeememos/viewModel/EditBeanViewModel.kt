package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.manager.RatingManager
import kotlinx.coroutines.launch
import org.w3c.dom.Comment

class EditBeanViewModel(private val beanDao: BeanDao) : ViewModel() {
    private val _selectedBean: MutableLiveData<Bean> = MutableLiveData()
    val selectedBean: LiveData<Bean> = _selectedBean

    private lateinit var _ratingManager: RatingManager

    private var _beanCurrentRating: MutableLiveData<Int> = MutableLiveData(1)
    val beanCurrentRating: LiveData<Int> = _beanCurrentRating

    private val _beanStarList: MutableLiveData<List<RatingManager.Star>> = MutableLiveData(listOf())
    val beanStarList: LiveData<List<RatingManager.Star>> = _beanStarList



    fun initialize(id: Long, ratingManager: RatingManager) {
        // RatingManagerを先に初期化する！（アプリ落ちる）
        _ratingManager = ratingManager

        viewModelScope.launch {
            val selectedBean = beanDao.getBeanById(id)

            _selectedBean.value = selectedBean

            updateRatingState(selectedBean.rating)
        }
    }

    fun updateRatingState(selectedRate: Int) {
        _ratingManager.changeRatingState(selectedRate)

        _beanCurrentRating.value = _ratingManager.currentRating
        _beanStarList.value      = _ratingManager.starList
    }
}

class EditBeanViewModelFactory(
    private val beanDao  : BeanDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditBeanViewModel::class.java)) {
            return EditBeanViewModel(beanDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
    }
}