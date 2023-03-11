package com.example.coffeememos.home.bean.presentation.view_model

import androidx.lifecycle.*
import com.example.coffeememos.home.bean.presentation.controller.HomeBeanController
import com.example.coffeememos.home.bean.presentation.model.HomeBeanCardData
import com.example.coffeememos.home.bean.presentation.model.HomeBeanOutput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeBeanViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var controller: HomeBeanController

    // 新しい順コーヒー豆
    private val _newBeans: MutableLiveData<List<HomeBeanCardData>> =
        MutableLiveData(listOf())
    val newBeans: LiveData<List<HomeBeanCardData>> = _newBeans

    // お気に入りコーヒー豆
    private val _favoriteBeans: MutableLiveData<List<HomeBeanCardData>> =
        MutableLiveData(listOf())
    val favoriteBeans: LiveData<List<HomeBeanCardData>> = _favoriteBeans

    // 高評価順コーヒー豆
    private val _highRatingBeans: MutableLiveData<List<HomeBeanCardData>> =
        MutableLiveData(listOf())
    val highRatingBeans: LiveData<List<HomeBeanCardData>> = _highRatingBeans

    // 今日のコーヒー豆数
    private val _todayBeanCount: MutableLiveData<String> = MutableLiveData("")
    val todayBeanCount: LiveData<String> = _todayBeanCount

    // 総コーヒー豆数
    private val _totalBeanCount: MutableLiveData<String> = MutableLiveData("")
    val totalBeanCount: LiveData<String> = _totalBeanCount

    // お気に入りコーヒー豆数
    val favoriteBeanCount: LiveData<String> = favoriteBeans.map { list ->
        return@map list.size.toString()
    }

    // コーヒー豆データがあるか
    private val _beanExists: MutableLiveData<Boolean> = MutableLiveData(false)
    val beanExists: LiveData<Boolean> = _beanExists

    fun updateHomeBeanData(beanId: Long, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedFavoriteFlag = !isFavorite
            val homeBeanOutput: HomeBeanOutput =
                controller.updateBeanData(beanId, updatedFavoriteFlag)

            setHomeBeanData(homeBeanOutput)
        }
    }

    // データ取得
    fun getHomeBeanData() {
        viewModelScope.launch {
            val homeBeanOutput: HomeBeanOutput =
                controller.getHomeBeanData()

            setHomeBeanData(homeBeanOutput)
        }
    }

    // LiveDataにデータセット
    private fun setHomeBeanData(output: HomeBeanOutput) {
        _newBeans.postValue(output.newBeans)
        _favoriteBeans.postValue(output.favoriteBeans)
        _highRatingBeans.postValue(output.highRatingBeans)
        _totalBeanCount.postValue(output.totalCount)
        _todayBeanCount.postValue(output.todayCount)
        _beanExists.postValue(output.beanExists)
    }
}

