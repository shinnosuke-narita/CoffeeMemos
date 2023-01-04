package com.example.coffeememos.search

import com.example.coffeememos.entity.CustomBean

class BeanFilterManager : BaseSearchFilterManager<CustomBean>() {
    private var _countryValues  : MutableList<String> = mutableListOf()
    val countryValues: List<String> get() = _countryValues

    private var _farmValues     : MutableList<String> = mutableListOf()
    val farmValues: List<String> get() = _farmValues

    private var _districtValues : MutableList<String> = mutableListOf()
    val districtValues: List<String> get() = _districtValues

    private var _storeValues    : MutableList<String> = mutableListOf()
    val storeValues: List<String> get() = _storeValues

    private var _speciesValues  : MutableList<String> = mutableListOf()
    val speciesValues: List<String> get() = _speciesValues

    private var _ratingValues   : MutableList<Int> = mutableListOf()
    val ratingValues: List<Int> get() = _ratingValues

    private var _processValues  : MutableList<Int> = mutableListOf()
    val processValues: List<Int> get() = _processValues

    fun collectCountryValue(values: List<String>) {
        if (values.isEmpty()) return

        _countryValues.addAll(values)
    }
    fun collectFarmValue(values: List<String>) {
        if (values.isEmpty()) return

        _farmValues.addAll(values)
    }
    fun collectDistrictValue(values: List<String>) {
        if (values.isEmpty()) return

        _districtValues.addAll(values)
    }
    fun collectStoreValue(values: List<String>) {
        if (values.isEmpty()) return

        _storeValues.addAll(values)
    }
    fun collectSpeciesValue(values: List<String>) {
        if (values.isEmpty()) return

        _speciesValues.addAll(values)
    }
    fun collectRatingValue(values: List<Boolean>) {
         for ((i, isSelected) in values.withIndex()) {
            if (isSelected) {
                val ratingValue = i + 1
                _ratingValues.add(ratingValue)
            }
        }
    }
    fun collectProcessValue(values: List<Boolean>) {
         for ((i, isSelected) in values.withIndex()) {
            if (isSelected) {
                _processValues.add(i)
            }
        }
    }

    fun resetList() {
        _countryValues = mutableListOf()
        _farmValues = mutableListOf()
        _districtValues = mutableListOf()
        _storeValues = mutableListOf()
        _speciesValues = mutableListOf()
        _ratingValues = mutableListOf()
        _processValues = mutableListOf()
    }

    override fun filteringElementsCountIsZero(): Boolean {
        if (_countryValues.isNotEmpty()) return false
        if (_farmValues.isNotEmpty()) return false
        if (_districtValues.isNotEmpty()) return false
        if (_storeValues.isNotEmpty()) return false
        if (_speciesValues.isNotEmpty()) return false
        if (_ratingValues.isNotEmpty()) return false
        if (_processValues.isNotEmpty()) return false
        return true
    }

    override fun filter(currentSearchResult: List<CustomBean>) {
        for (bean in currentSearchResult) {
            if (addItemIfPassCheck(bean, bean.country, _countryValues)) continue
            if (addItemIfPassCheck(bean, bean.farm, _farmValues)) continue
            if (addItemIfPassCheck(bean, bean.district, _districtValues)) continue
            if (addItemIfPassCheck(bean, bean.store, _storeValues)) continue
            if (addItemIfPassCheck(bean, bean.species, _speciesValues)) continue
            if (addItemIfPassCheck(bean, bean.process, _processValues)) continue
            addItemIfPassCheck(bean, bean.rating, _ratingValues)
        }
    }
}