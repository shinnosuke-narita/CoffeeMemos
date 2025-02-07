package com.withapp.coffeememo.presentation.home.bean.view_model

import androidx.lifecycle.*
import com.withapp.coffeememo.domain.model.bean.HomeBeanModel
import com.withapp.coffeememo.domain.model.bean.HomeBeanSource
import com.withapp.coffeememo.domain.usecase.bean.gethomebean.GetHomeBeanDataUseCase
import com.withapp.coffeememo.domain.usecase.bean.updatefavorite.UpdateFavoriteBeanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeBeanViewModel @Inject constructor(
    private val getHomeBeanDataUseCase: GetHomeBeanDataUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteBeanUseCase
) : ViewModel() {
    // 新しい順コーヒー豆
    private val _newBeans: MutableLiveData<List<HomeBeanModel>> = MutableLiveData(listOf())
    val newBeans: LiveData<List<HomeBeanModel>> = _newBeans

    // お気に入りコーヒー豆
    private val _favoriteBeans: MutableLiveData<List<HomeBeanModel>> = MutableLiveData(listOf())
    val favoriteBeans: LiveData<List<HomeBeanModel>> = _favoriteBeans

    // 高評価順コーヒー豆
    private val _highRatingBeans: MutableLiveData<List<HomeBeanModel>> = MutableLiveData(listOf())
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
            updateFavoriteUseCase.handle(beanId, updatedFavoriteFlag)
            setHomeBeanData(getHomeBeanDataUseCase.handle())
        }
    }

    // データ取得
    fun getHomeBeanData() {
        viewModelScope.launch {
            setHomeBeanData(getHomeBeanDataUseCase.handle())
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

