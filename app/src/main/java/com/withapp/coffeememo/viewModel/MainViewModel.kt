package com.withapp.coffeememo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.utilities.DateUtil

class MainViewModel : ViewModel() {
    private val _selectedBean: MutableLiveData<SearchBeanModel?> = MutableLiveData(null)
    val selectedBean: LiveData<SearchBeanModel?> = _selectedBean

    fun setBean(bean: SearchBeanModel) {
        _selectedBean.value = bean
    }

    fun resetBean() {
        _selectedBean.value = null
    }

    // 抽出時間
    private val _extractionTime: MutableLiveData<Long> = MutableLiveData(0L)
    val extractionTime: LiveData<Long> = _extractionTime

    fun setExtractionTime(extractionTime: Long) {
        _extractionTime.value = extractionTime
    }

    // 蒸らし時間
    private val _preInfusionTime: MutableLiveData<Long> = MutableLiveData(0L)
    val preInfusionTime: LiveData<Long> = _preInfusionTime

    fun setPreInfusionTime(preInfusionTime: Long) {
        _preInfusionTime.value = preInfusionTime
    }

    fun resetTime() {
        _preInfusionTime.value = 0L
        _extractionTime.value = 0L
    }

    // タイマー画面が開かれた時、レシピ新規作成画面がバックスタックにあるかどうか
    private val _newRecipeFragmentExists: MutableLiveData<Boolean> = MutableLiveData(false)
    val newRecipeFragmentExists: LiveData<Boolean> = _newRecipeFragmentExists

    fun setNewRecipeExistsFlag(flag: Boolean) {
        _newRecipeFragmentExists.value = flag
    }

    private val _shouldShowAd: MutableLiveData<Boolean> = MutableLiveData(true)
    val shouldShowAd: LiveData<Boolean> = _shouldShowAd

    fun showAd() { _shouldShowAd.value = true }
    fun hideAd() { _shouldShowAd.value = false }
}