package com.withapp.coffeememo.home.bean.presentation.view_model

import androidx.lifecycle.*
import com.withapp.coffeememo.home.bean.domain.model.HomeBeanModel
import com.withapp.coffeememo.home.bean.domain.model.HomeBeanSource
import com.withapp.coffeememo.home.bean.presentation.controller.HomeBeanController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeBeanViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var controller: HomeBeanController

    // 新しい順コーヒー豆
    private val _newBeans: MutableLiveData<List<HomeBeanModel>> =
        MutableLiveData(listOf())
    val newBeans: LiveData<List<HomeBeanModel>> = _newBeans

    // お気に入りコーヒー豆
    private val _favoriteBeans: MutableLiveData<List<HomeBeanModel>> =
        MutableLiveData(listOf())
    val favoriteBeans: LiveData<List<HomeBeanModel>> = _favoriteBeans

    // 高評価順コーヒー豆
    private val _highRatingBeans: MutableLiveData<List<HomeBeanModel>> =
        MutableLiveData(listOf())
    val highRatingBeans: LiveData<List<HomeBeanModel>> = _highRatingBeans

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
    val beanExists: LiveData<Boolean> = _totalBeanCount.map { count ->
        return@map count != "0"
    }

    fun updateHomeBeanData(beanId: Long, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedFavoriteFlag = !isFavorite
            val homeBeanOutput: HomeBeanSource =
                controller.updateBeanData(beanId, updatedFavoriteFlag)

            setHomeBeanData(homeBeanOutput)
        }
    }

    // データ取得
    fun getHomeBeanData() {
        viewModelScope.launch {
            val homeBeanOutput: HomeBeanSource =
                controller.getHomeBeanData()

            setHomeBeanData(homeBeanOutput)
        }
    }

    // LiveDataにデータセット
    private fun setHomeBeanData(output: HomeBeanSource) {
        _newBeans.postValue(output.newBeans)
        _favoriteBeans.postValue(output.favoriteBeans)
        _highRatingBeans.postValue(output.highRatingBeans)
        _totalBeanCount.postValue(output.totalCount.toString())
        _todayBeanCount.postValue(output.todayCount.toString())
    }
}

