package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.Util
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.Bean
import kotlinx.coroutines.launch

class NewBeanViewModel(val beanDao: BeanDao) : ViewModel() {
    // 現在のrate(初期値3)
    private var currentRating: Int = 3

    /**
     * rateの状態管理フラグ
     */
    private var _starFirst: MutableLiveData<Boolean> = MutableLiveData(true)
    val starFirst: LiveData<Boolean> = _starFirst

    private var _starSecond: MutableLiveData<Boolean> = MutableLiveData(false)
    val starSecond: LiveData<Boolean> = _starSecond

    private var _starThird: MutableLiveData<Boolean> = MutableLiveData(false)
    val starThird: LiveData<Boolean> = _starThird

    private var _starFourth: MutableLiveData<Boolean> = MutableLiveData(false)
    val starFourth: LiveData<Boolean> = _starFourth

    private var _starFifth: MutableLiveData<Boolean> = MutableLiveData(false)
    val starFifth: LiveData<Boolean> = _starFifth

    private val starList = listOf(_starFirst, _starSecond, _starThird, _starFourth, _starFifth)

    // view のリセットフラグ
    private var _shouldResetView: MutableLiveData<Boolean> = MutableLiveData(false)
    val shouldResetView: LiveData<Boolean> = _shouldResetView

    // 現在の精製法
    private var _selectedProcess: MutableLiveData<Int> = MutableLiveData(0)
    val selectedProcess: LiveData<Int> = _selectedProcess


    fun setProcessIndex(index: Int) {
        _selectedProcess.value = index
    }

    fun setResetFlag(flag: Boolean) {
        _shouldResetView.value = flag
    }

    // rateのstate変更メソッド
    fun changeRatingState(selectedRate: Int) {
        currentRating = selectedRate
        for ((index, star) in starList.withIndex()) {
            star.value = index < selectedRate
        }
    }

    fun createNewBean(
        country: String,
        farm: String,
        district: String,
        species: String,
        elevationFrom: String,
        elevationTo: String,
        purchaseStore: String,
        comment: String
    ) {
        viewModelScope.launch {
            val iElevationFrom = Util.convertStringIntoIntIfPossible(elevationFrom)
            val iElevationTo = Util.convertStringIntoIntIfPossible(elevationTo)

            val createdAt = System.currentTimeMillis()

            beanDao.insert(
                Bean(
                    0,
                    country,
                    farm,
                    district,
                    species,
                    iElevationFrom,
                    iElevationTo,
                    _selectedProcess.value ?: 0,
                    purchaseStore,
                    comment,
                    currentRating,
                    0,
                    true,
                    createdAt
                )
            )
        }

    }
}


class NewBeanViewModelFactory(private val beanDao: BeanDao)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewBeanViewModel::class.java)) {
            return NewBeanViewModel(beanDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_NEWBEANVIEWMODEL")
    }
}

