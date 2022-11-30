package com.example.coffeememos.manager

import com.example.coffeememos.CustomRecipe
import java.util.*

class SearchFilterManager() {
    private val filteredRecipeList: MutableList<CustomRecipe> = mutableListOf()

    val sourValues     : MutableList<Int> = mutableListOf()
    val bitterValues   : MutableList<Int> = mutableListOf()
    val sweetValues    : MutableList<Int> = mutableListOf()
    val flavorValues   : MutableList<Int> = mutableListOf()
    val richValues     : MutableList<Int> = mutableListOf()
    val grindSizeValues: MutableList<Int> = mutableListOf()
    val roastValues    : MutableList<Int> = mutableListOf()
    val ratingValues   : MutableList<Int> = mutableListOf()
    val countryValues   : MutableList<String> = mutableListOf()
    val toolValues      : MutableList<String> = mutableListOf()

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

    fun filerList(currentSearchResult: List<CustomRecipe>): List<CustomRecipe> {
        filteredRecipeList.clear()

        for (recipe in currentSearchResult) {
            if (addRecipeIfPassCheck(recipe, recipe.sour, sourValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.bitter, bitterValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.sweet, sweetValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.flavor, flavorValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.rich, richValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.roast, roastValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.grindSize, grindSizeValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.rating, ratingValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.country, countryValues)) continue
            addRecipeIfPassCheck(recipe, recipe.tool, toolValues)
        }

        return filteredRecipeList
    }

    // recipeを追加したら、trueを返す
    private fun addRecipeIfPassCheck(recipe: CustomRecipe, recipeValue: Int, selectedValues: List<Int>): Boolean {
        if (selectedValues.isEmpty()) return false

        for (selectedValue in selectedValues) {
            if (recipeValue == selectedValue) {
                filteredRecipeList.add(recipe)
                return true
            }
        }
        return false
    }

    // recipeを追加したら、trueを返す
    private fun addRecipeIfPassCheck(recipe: CustomRecipe, recipeValue: String, selectedValues: List<String>): Boolean {
        if (selectedValues.isEmpty()) return false

        for (selectedValue in selectedValues) {
            if (recipeValue.contains(selectedValue)) {
                filteredRecipeList.add(recipe)
                return true
            }
        }
        return false
    }
}