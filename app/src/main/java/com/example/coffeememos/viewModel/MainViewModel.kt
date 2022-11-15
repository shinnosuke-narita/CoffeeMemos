package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.state.InputType

class MainViewModel : ViewModel() {
    private val _selectedBean: MutableLiveData<Bean> = MutableLiveData()
    val selectedBean: LiveData<Bean> = _selectedBean

    fun setBean(bean: Bean) {
        _selectedBean.value = bean
    }


    // 抽出時間
    private val _extractionTime: MutableLiveData<Long> = MutableLiveData(0L)
    val extractionTime: LiveData<Long> = _extractionTime

    val formattedExtractionTime: LiveData<String> = _extractionTime.map { extractionTime ->
        String.format(
            "%02d分%02d秒",
            getMinutes(extractionTime),
            getSeconds(extractionTime)
        )
    }

    fun setExtractionTime(extractionTime: Long) {
        _extractionTime.value = extractionTime
    }

    // 蒸らし時間
    private val _preInfusionTime: MutableLiveData<Long> = MutableLiveData(0L)
    val preInfusionTime: LiveData<Long> = _preInfusionTime

    val formattedPreInfusionTime: LiveData<String> = _preInfusionTime.map { preInfusionTime ->
        String.format("%d秒", getSeconds(preInfusionTime))
    }

    val preInfusionTimeSeconds: LiveData<String> = _preInfusionTime.map { preInfusionTime ->
        (preInfusionTime / 1000).toString()
    }

    fun setPreInfusionTime(preInfusionTime: Long) {
        _preInfusionTime.value = preInfusionTime
    }


    private fun getMinutes(timeMills: Long) = timeMills / 1000 / 60
    private fun getSeconds(timeMills: Long) = timeMills / 1000 % 60
}