package com.example.coffeememos.search

import com.example.coffeememos.search.domain.model.SearchRecipeModel
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.entity.RecipeWithTaste

class SearchFilterManager(private val recipeDao: RecipeDao, private val customRecipeMapper: CustomRecipeMapper) : BaseSearchFilterManager<SearchRecipeModel>() {
    private var _keyword: String = ""

    suspend fun freeWordSearch(keyword: String): List<SearchRecipeModel> {
       _keyword = keyword

        val recipeWithTasteList: List<RecipeWithTaste> = recipeDao.getRecipeWithTasteByKeyword(_keyword)
        return customRecipeMapper.execute(recipeWithTasteList)
    }

    suspend fun initSearchResult(): List<SearchRecipeModel> {
        val recipeWithTasteList = recipeDao.getRecipeWithTaste()
        return customRecipeMapper.execute(recipeWithTasteList)
    }

    suspend fun searchAndFilter(): List<SearchRecipeModel> {
        val recipeWithTasteList: List<RecipeWithTaste> =
            if (_keyword.isEmpty()) {
                recipeDao.getRecipeWithTaste()
            } else {
                recipeDao.getRecipeWithTasteByKeyword(_keyword)
            }

        val mappedRes = customRecipeMapper.execute(recipeWithTasteList)

        return makeList(mappedRes)
    }

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
        _countryValues = mutableListOf()
        _toolValues = mutableListOf()
        _roastValues = mutableListOf()
        _grindSizeValues = mutableListOf()
        _ratingValues = mutableListOf()
        _sourValues = mutableListOf()
        _bitterValues = mutableListOf()
        _sweetValues = mutableListOf()
        _flavorValues = mutableListOf()
        _richValues = mutableListOf()
    }

    override fun filter(currentSearchResult: List<SearchRecipeModel>) {
        filteredResult = currentSearchResult.toMutableList()

        // 原産地
        if (isEmptyAfterFiltering(_countryValues) { recipe, res ->
                addItemIfPossible(recipe, recipe.country, _countryValues, res) }
        ) { return } // 早期リターン
        // 抽出器具
        if (isEmptyAfterFiltering(_toolValues) { recipe, res ->
                addItemIfPossible(recipe, recipe.tool, _toolValues, res) }
        ) { return } // 早期リターン
        // 焙煎度
        if (isEmptyAfterFiltering(_roastValues) { recipe, res ->
                addItemIfPossible(recipe, recipe.roast, _roastValues, res) }
        ) { return } // 早期リターン
        // グラインド
        if (isEmptyAfterFiltering(_grindSizeValues) { recipe, res ->
                addItemIfPossible(recipe, recipe.grindSize, _grindSizeValues, res) }
        ) { return } // 早期リターン
        // 酸味
        if (isEmptyAfterFiltering(_sourValues) { recipe, res ->
                addItemIfPossible(recipe, recipe.sour, _sourValues, res) }
        ) { return } // 早期リターン
        // 苦味
        if (isEmptyAfterFiltering(_bitterValues) { recipe, res ->
                addItemIfPossible(recipe, recipe.bitter, _bitterValues, res) }
        ) { return } // 早期リターン
        // 甘味
        if (isEmptyAfterFiltering(_sweetValues) { recipe, res ->
                addItemIfPossible(recipe, recipe.sweet, _sweetValues, res) }
        ) { return } // 早期リターン
        // 香り
        if (isEmptyAfterFiltering(_flavorValues) { recipe, res ->
                addItemIfPossible(recipe, recipe.flavor, _flavorValues, res) }
        ) { return } // 早期リターン
        // コク
        if (isEmptyAfterFiltering(_richValues) { recipe, res ->
                addItemIfPossible(recipe, recipe.rich, _richValues, res) }
        ) { return } // 早期リターン
        // 評価
        isEmptyAfterFiltering(_ratingValues) { recipe, res ->
                addItemIfPossible(recipe, recipe.rating, _ratingValues, res) }
    }

    // 絞り込み処理後、絞り込み結果が空だったらtrue
    private fun isEmptyAfterFiltering(
        filteringElements: List<*>,
        addItemProcess: (recipe: SearchRecipeModel, res: MutableList<SearchRecipeModel>) -> Unit
    ): Boolean {
        // 絞り込み要素が無かったら、絞り込み結果が空にはなり得ない
        if (filteringElements.isEmpty()) return false

        val res = mutableListOf<SearchRecipeModel>()
        for (recipe in filteredResult) {
            addItemProcess(recipe, res)
        }
        filteredResult = res

        if (filteredResult.isNotEmpty()) return false
        // 絞り込み結果該当なし
        return true
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