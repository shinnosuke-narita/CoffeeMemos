package com.example.coffeememos.search

import com.example.coffeememos.entity.CustomBean

class BeanFilterManager : BaseSearchFilterManager<CustomBean>() {
    val countryValues  : MutableList<String> = mutableListOf()
    val farmValues     : MutableList<String> = mutableListOf()
    val districtValues : MutableList<String> = mutableListOf()
    val storeValues    : MutableList<String> = mutableListOf()
    val speciesValues  : MutableList<String> = mutableListOf()
    val ratingValues   : MutableList<Int> = mutableListOf()
    val processValues  : MutableList<Int> = mutableListOf()

    fun addCountryValue(value: String) {
        countryValues.add(value)
    }
    fun addFarmValue(value: String) {
        farmValues.add(value)
    }
    fun addDistrictValue(value: String) {
        districtValues.add(value)
    }
    fun addStoreValue(value: String) {
        storeValues.add(value)
    }
    fun addSpeciesValue(value: String) {
        speciesValues.add(value)
    }
    fun addRatingValue(value: Int) {
        ratingValues.add(value)
    }
    fun addProcessValue(value: Int) {
        processValues.add(value)
    }

    fun resetList() {
        countryValues.clear()
        farmValues.clear()
        districtValues.clear()
        storeValues.clear()
        speciesValues.clear()
        ratingValues.clear()
        processValues.clear()
    }

    override fun filteringElementsCountIsZero(): Boolean {
        if (countryValues.isNotEmpty()) return false
        if (farmValues.isNotEmpty()) return false
        if (districtValues.isNotEmpty()) return false
        if (storeValues.isNotEmpty()) return false
        if (speciesValues.isNotEmpty()) return false
        if (ratingValues.isNotEmpty()) return false
        if (processValues.isNotEmpty()) return false
        return true
    }

    override fun filter(currentSearchResult: List<CustomBean>) {
        for (bean in currentSearchResult) {
            if (addItemIfPassCheck(bean, bean.country, countryValues)) continue
            if (addItemIfPassCheck(bean, bean.farm, farmValues)) continue
            if (addItemIfPassCheck(bean, bean.district, districtValues)) continue
            if (addItemIfPassCheck(bean, bean.store, storeValues)) continue
            if (addItemIfPassCheck(bean, bean.species, speciesValues)) continue
            if (addItemIfPassCheck(bean, bean.process, processValues)) continue
            addItemIfPassCheck(bean, bean.rating, ratingValues)
        }
    }
}