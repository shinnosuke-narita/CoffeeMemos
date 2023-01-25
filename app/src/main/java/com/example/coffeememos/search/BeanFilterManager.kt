package com.example.coffeememos.search

import com.example.coffeememos.CustomRecipe
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.CustomBean
import com.example.coffeememos.entity.RecipeWithTaste

class BeanFilterManager(private val beanDao: BeanDao) : BaseSearchFilterManager<CustomBean>() {
    private var _keyword: String = ""

    suspend fun freeWordSearch(keyword: String): List<CustomBean> {
        _keyword = keyword

        return beanDao.getCustomBeanByKeyword(keyword)
    }

    suspend fun initSearchResult(): List<CustomBean> {
        return beanDao.getCustomBean()
    }

    suspend fun searchAndFilter(): List<CustomBean> {
        val customBeanList: List<CustomBean> =
            if (_keyword.isEmpty()) {
                beanDao.getCustomBean()
            } else {
                beanDao.getCustomBeanByKeyword(_keyword)
            }


        return makeList(customBeanList)
    }

    private var _countryValues: MutableList<String> = mutableListOf()
    val countryValues: List<String> get() = _countryValues

    private var _farmValues: MutableList<String> = mutableListOf()
    val farmValues: List<String> get() = _farmValues

    private var _districtValues: MutableList<String> = mutableListOf()
    val districtValues: List<String> get() = _districtValues

    private var _storeValues: MutableList<String> = mutableListOf()
    val storeValues: List<String> get() = _storeValues

    private var _speciesValues: MutableList<String> = mutableListOf()
    val speciesValues: List<String> get() = _speciesValues

    private var _ratingValues: MutableList<Int> = mutableListOf()
    val ratingValues: List<Int> get() = _ratingValues

    private var _processValues: MutableList<Int> = mutableListOf()
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
        filteredResult = currentSearchResult.toMutableList()

        // 原産地
        if (isEmptyAfterFiltering(_countryValues) { bean, res ->
                addItemIfPossible(bean, bean.country, _countryValues, res) }
        ) { return } // 早期リターン
        // 農園
        if (isEmptyAfterFiltering(_farmValues) { bean, res ->
                addItemIfPossible(bean, bean.farm, _farmValues, res) }
        ) { return } // 早期リターン
        // 地区
        if (isEmptyAfterFiltering(_districtValues) { bean, res ->
                addItemIfPossible(bean, bean.district, _districtValues, res) }
        ) { return } // 早期リターン
        // 購入店
        if (isEmptyAfterFiltering(_storeValues) { bean, res ->
                addItemIfPossible(bean, bean.store, _storeValues, res) }
        ) { return } // 早期リターン
        // 品種
        if (isEmptyAfterFiltering(_speciesValues) { bean, res ->
                addItemIfPossible(bean, bean.species, _speciesValues, res) }
        ) { return } // 早期リターン
        // 評価
        if (isEmptyAfterFiltering(_ratingValues) { bean, res ->
                addItemIfPossible(bean, bean.rating, _ratingValues, res) }
        ) { return } // 早期リターン
        // 精製法
        isEmptyAfterFiltering(_processValues) { bean, res ->
                addItemIfPossible(bean, bean.process, _processValues, res) }
    }
    // 絞り込み処理後、絞り込み結果が空だったらtrue
    private fun isEmptyAfterFiltering(
        filteringElements: List<*>,
        addItemProcess: (bean: CustomBean, res: MutableList<CustomBean>) -> Unit
    ): Boolean {
        // 絞り込み要素が無かったら、絞り込み結果が空にはなり得ない
        if (filteringElements.isEmpty()) return false

        val res = mutableListOf<CustomBean>()
        for (bean in filteredResult) {
            addItemProcess(bean, res)
        }
        filteredResult = res

        if (filteredResult.isNotEmpty()) return false
        // 絞り込み結果該当なし
        return true
    }
}