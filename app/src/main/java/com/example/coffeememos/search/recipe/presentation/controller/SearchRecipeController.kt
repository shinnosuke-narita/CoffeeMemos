package com.example.coffeememos.search.recipe.presentation.controller

import com.example.coffeememos.search.recipe.presentation.model.SearchKeyWord
import com.example.coffeememos.search.recipe.presentation.model.SearchType
import com.example.coffeememos.search.recipe.domain.model.FilterRecipeInputData
import com.example.coffeememos.search.recipe.domain.model.FilterRecipeOutputData
import com.example.coffeememos.search.recipe.domain.model.RecipeSortType
import com.example.coffeememos.search.recipe.domain.model.SearchRecipeModel
import com.example.coffeememos.search.recipe.domain.use_case.*
import javax.inject.Inject
import kotlin.Int as Int

class SearchRecipeController @Inject constructor() {
    @Inject
    lateinit var freeWordSearchUseCase: FreeWordSearchUseCase
    @Inject
    lateinit var sortRecipeUseCase: SortRecipeUseCase
    @Inject
    lateinit var filterRecipeUseCase: FilterRecipeUseCase
    @Inject
    lateinit var setRecipeInputDataUseCase: SetFilterRecipeInputDataUseCase
    @Inject
    lateinit var getRecipeOutputDataUseCase: GetFilterRecipeOutputDataUseCase
    @Inject
    lateinit var getAllRecipeUseCase: GetAllRecipeUseCase
    @Inject
    lateinit var deleteFilterInputDataUseCase: DeleteFilterRecipeInputDataUseCase
    @Inject
    lateinit var updateFavoriteUseCase: UpdateFavoriteUseCase

    suspend fun getAllRecipe(): List<SearchRecipeModel> {
        return getAllRecipeUseCase.getAllRecipe()
    }

    suspend fun freeWordSearch(freeWord: SearchKeyWord): List<SearchRecipeModel>? {
        if (freeWord.keyWord.isEmpty()) return null
        if (freeWord.type == SearchType.BEAN) return null

        return freeWordSearchUseCase.handle(freeWord.keyWord)
    }

    fun sortRecipe(sortType: RecipeSortType, list: List<SearchRecipeModel>): List<SearchRecipeModel> {
        return sortRecipeUseCase.sort(sortType, list)
    }

    suspend fun filter(
        searchWord: String,
        roastValues: List<Boolean>,
        grindSizeValues: List<Boolean>,
        ratingValues: List<Boolean>,
        sourValues: List<Boolean>,
        bitterValues: List<Boolean>,
        sweetValues: List<Boolean>,
        flavorValues: List<Boolean>,
        richValues: List<Boolean>,
        countryValues: List<String>,
        toolValues: List<String>): List<SearchRecipeModel> {

        val roastData = mutableListOf<Int>()
        for ((i, isSelected) in roastValues.withIndex()) {
           if (isSelected) { roastData.add(i) }
        }

        val grindSizeData = mutableListOf<Int>()
        for ((i, isSelected) in grindSizeValues.withIndex()) {
            if (isSelected) { grindSizeData.add(i) }
        }

        val ratingData = mutableListOf<Int>()
        for ((i, isSelected) in ratingValues.withIndex()) {
            if (isSelected) { ratingData.add(i + 1) }
        }

        val sourData = mutableListOf<Int>()
        for ((i, isSelected) in sourValues.withIndex()) {
            if (isSelected) { sourData.add(i + 1) }
        }

        val bitterData = mutableListOf<Int>()
        for ((i, isSelected) in bitterValues.withIndex()) {
            if (isSelected) { bitterData.add(i + 1) }
        }

        val sweetData = mutableListOf<Int>()
        for ((i, isSelected) in sweetValues.withIndex()) {
            if (isSelected) { sweetData.add(i + 1) }
        }

        val flavorData = mutableListOf<Int>()
        for ((i, isSelected) in flavorValues.withIndex()) {
            if (isSelected) { flavorData.add(i + 1) }
        }

        val richData = mutableListOf<Int>()
        for ((i, isSelected) in richValues.withIndex()) {
            if (isSelected) { richData.add(i + 1) }
        }

        val inputData = FilterRecipeInputData(
            keyWord = searchWord,
            countries = countryValues,
            tools = toolValues,
            roasts = roastData,
            grindSizes = grindSizeData,
            rating = ratingData,
            sour = sourData,
            bitter = bitterData,
            sweet = sweetData,
            flavor = flavorData,
            rich = richData
        )

        // 絞り込み要素を保存
        setRecipeInputDataUseCase.execute(inputData)

        // 絞り込み実行
        return filterRecipeUseCase.filterRecipe(inputData)
    }

    fun getRecipeOutPutData(key: String): FilterRecipeOutputData? {
        return getRecipeOutputDataUseCase.execute(key)
    }

    fun deleteRecipeInputData(key: String) {
        deleteFilterInputDataUseCase.handle(key)
    }

    suspend fun updateFavorite(recipeId: Long, isFavorite: Boolean) {
        updateFavoriteUseCase.handle(recipeId, isFavorite)
    }

}