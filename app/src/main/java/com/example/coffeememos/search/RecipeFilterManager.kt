package com.example.coffeememos.search

import com.example.coffeememos.CustomRecipe

class SearchFilterManager : BaseSearchFilterManager<CustomRecipe>() {
    private var _sourValues     : MutableList<Int> = mutableListOf()
    val sourValues: List<Int> get() = _sourValues
    private var _bitterValues   : MutableList<Int> = mutableListOf()
    val bitterValues: List<Int> get() = _bitterValues
    private var _sweetValues    : MutableList<Int> = mutableListOf()
    val sweetValues: List<Int> get() = _sweetValues
    private var _flavorValues   : MutableList<Int> = mutableListOf()
    val flavorValues: List<Int> get() = _flavorValues
    private var _richValues     : MutableList<Int> = mutableListOf()
    val richValues: List<Int> get() = _richValues
    private var _grindSizeValues: MutableList<Int> = mutableListOf()
    val grindSizeValues: List<Int> get() = _grindSizeValues
    private var _roastValues    : MutableList<Int> = mutableListOf()
    val roastValues: List<Int> get() = _roastValues
    private var _ratingValues   : MutableList<Int> = mutableListOf()
    val ratingValues: List<Int> get() = _ratingValues
    private var _countryValues  : MutableList<String> = mutableListOf()
    val countryValues: List<String> get() = _countryValues
    private var _toolValues     : MutableList<String> = mutableListOf()
    val toolValues: List<String> get() = _toolValues


    // データ収集 処理
    fun collectCountryValue(values: List<String>) {
        if (values.isEmpty()) return

        _countryValues.addAll(values)
    }
    fun collectToolValue(values: List<String>) {
        if (values.isEmpty()) return

        _toolValues.addAll(values)
    }
    fun collectRatingValue(values: List<Boolean>) {
        addValue(values) { index ->
            val value = index + 1
            _sourValues.add(value)
        }
    }
    fun collectSourValue(values: List<Boolean>) {
        addValue(values) { index ->
            val value = index + 1
            _sourValues.add(value)
        }
    }
    fun collectSweetValue(values: List<Boolean>) {
        addValue(values) { index ->
            val value = index + 1
            _sweetValues.add(value)
        }
    }
    fun collectBitterValue(values: List<Boolean>) {
        addValue(values) { index ->
            val value = index + 1
            _bitterValues.add(value)
        }
    }
    fun collectFlavorValue(values: List<Boolean>) {
        addValue(values) { index ->
            val value = index + 1
            _flavorValues.add(value)
        }
    }
    fun collectRichValue(values: List<Boolean>) {
        addValue(values) { index ->
            val value = index + 1
            _richValues.add(value)
        }
    }
    fun collectRoastValue(values: List<Boolean>) {
        addValue(values) { index ->
            _roastValues.add(index)
        }
    }
    fun collectGrindSizeValue(values: List<Boolean>) {
        addValue(values) { index ->
            _grindSizeValues.add(index)
        }
    }
    // データ収集メソッドの共通ロジック
    private fun addValue(values: List<Boolean>, addProcess: (index: Int) -> Unit) {
        for ((i, isSelected) in values.withIndex()) {
            if (isSelected) {
                addProcess(i)
            }
        }
    }

    fun resetList() {
        _roastValues = mutableListOf()
        _grindSizeValues = mutableListOf()
        _ratingValues = mutableListOf()
        _sourValues = mutableListOf()
        _bitterValues = mutableListOf()
        _sweetValues = mutableListOf()
        _flavorValues = mutableListOf()
        _richValues = mutableListOf()
    }

    override fun filter(currentSearchResult: List<CustomRecipe>) {
        for (recipe in currentSearchResult) {
            if (addItemIfPassCheck(recipe, recipe.sour, _sourValues)) continue
            if (addItemIfPassCheck(recipe, recipe.bitter, _bitterValues)) continue
            if (addItemIfPassCheck(recipe, recipe.sweet, _sweetValues)) continue
            if (addItemIfPassCheck(recipe, recipe.flavor, _flavorValues)) continue
            if (addItemIfPassCheck(recipe, recipe.rich, _richValues)) continue
            if (addItemIfPassCheck(recipe, recipe.roast, _roastValues)) continue
            if (addItemIfPassCheck(recipe, recipe.grindSize, _grindSizeValues)) continue
            if (addItemIfPassCheck(recipe, recipe.rating, _ratingValues)) continue
            if (addItemIfPassCheck(recipe, recipe.country, _countryValues)) continue
            addItemIfPassCheck(recipe, recipe.tool, _toolValues)
        }
    }

    override fun filteringElementsCountIsZero(): Boolean {
        if (_sourValues.isNotEmpty()) return false
        if (_bitterValues.isNotEmpty()) return false
        if (_sweetValues.isNotEmpty()) return false
        if (_flavorValues.isNotEmpty()) return false
        if (_richValues.isNotEmpty()) return false
        if (_grindSizeValues.isNotEmpty()) return false
        if (_roastValues.isNotEmpty()) return false
        if (_ratingValues.isNotEmpty()) return false
        if (_countryValues.isNotEmpty()) return false
        if (_toolValues.isNotEmpty()) return false
        return true
    }
}