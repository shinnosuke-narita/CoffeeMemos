package com.example.coffeememos.search

import com.example.coffeememos.CustomRecipe

class SearchFilterManager : BaseSearchFilterManager<CustomRecipe>() {
    val sourValues     : MutableList<Int> = mutableListOf()
    val bitterValues   : MutableList<Int> = mutableListOf()
    val sweetValues    : MutableList<Int> = mutableListOf()
    val flavorValues   : MutableList<Int> = mutableListOf()
    val richValues     : MutableList<Int> = mutableListOf()
    val grindSizeValues: MutableList<Int> = mutableListOf()
    val roastValues    : MutableList<Int> = mutableListOf()
    val ratingValues   : MutableList<Int> = mutableListOf()
    val countryValues  : MutableList<String> = mutableListOf()
    val toolValues     : MutableList<String> = mutableListOf()

    fun addSourValue(value: Int) {
        sourValues.add(value)
    }
    fun addBitterValue(value: Int) {
        bitterValues.add(value)
    }
    fun addSweetValue(value: Int) {
        sweetValues.add(value)
    }
    fun addFlavorValue(value: Int) {
        flavorValues.add(value)
    }
    fun addRichValue(value: Int) {
        richValues.add(value)
    }
    fun addGrindSizeValue(value: Int) {
        grindSizeValues.add(value)
    }
    fun addRoastValue(value: Int) {
        roastValues.add(value)
    }
    fun addRatingValue(value: Int) {
        ratingValues.add(value)
    }
    fun addCountryValue(value: String) {
        countryValues.add(value)
    }
    fun removeCountryValue(value: String) {
        countryValues.remove(value)
    }
    fun addToolValue(value: String)  {
        toolValues.add(value)
    }
    fun removeToolValue(value: String) {
        toolValues.remove(value)
    }

    fun resetList() {
        roastValues.clear()
        grindSizeValues.clear()
        ratingValues.clear()
        sourValues.clear()
        bitterValues.clear()
        sweetValues.clear()
        flavorValues.clear()
        richValues.clear()
    }

    override fun filter(currentSearchResult: List<CustomRecipe>) {
        for (recipe in currentSearchResult) {
            if (addItemIfPassCheck(recipe, recipe.sour, sourValues)) continue
            if (addItemIfPassCheck(recipe, recipe.bitter, bitterValues)) continue
            if (addItemIfPassCheck(recipe, recipe.sweet, sweetValues)) continue
            if (addItemIfPassCheck(recipe, recipe.flavor, flavorValues)) continue
            if (addItemIfPassCheck(recipe, recipe.rich, richValues)) continue
            if (addItemIfPassCheck(recipe, recipe.roast, roastValues)) continue
            if (addItemIfPassCheck(recipe, recipe.grindSize, grindSizeValues)) continue
            if (addItemIfPassCheck(recipe, recipe.rating, ratingValues)) continue
            if (addItemIfPassCheck(recipe, recipe.country, countryValues)) continue
            addItemIfPassCheck(recipe, recipe.tool, toolValues)
        }
    }

    override fun filteringElementsCountIsZero(): Boolean {
        if (sourValues.isNotEmpty()) return false
        if (bitterValues.isNotEmpty()) return false
        if (sweetValues.isNotEmpty()) return false
        if (flavorValues.isNotEmpty()) return false
        if (richValues.isNotEmpty()) return false
        if (grindSizeValues.isNotEmpty()) return false
        if (roastValues.isNotEmpty()) return false
        if (ratingValues.isNotEmpty()) return false
        if (countryValues.isNotEmpty()) return false
        if (toolValues.isNotEmpty()) return false
        return true
    }
}