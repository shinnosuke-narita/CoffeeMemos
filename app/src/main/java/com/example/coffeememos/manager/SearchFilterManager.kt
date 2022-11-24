package com.example.coffeememos.manager

import com.example.coffeememos.CustomRecipe
import java.util.*

class SearchFilterManager(private val currentSearchResult: List<CustomRecipe>) {
    private val filteredRecipeList: MutableList<CustomRecipe> = mutableListOf()

    private val sourValues     : MutableList<Int> = mutableListOf()
    private val bitterValues   : MutableList<Int> = mutableListOf()
    private val sweetValues    : MutableList<Int> = mutableListOf()
    private val flavorValues   : MutableList<Int> = mutableListOf()
    private val richValues     : MutableList<Int> = mutableListOf()
    private val grindSizeValues: MutableList<Int> = mutableListOf()
    private val roastValues    : MutableList<Int> = mutableListOf()
    private val ratingValues   : MutableList<Int> = mutableListOf()
    private var inputCountry   : String = ""
    private var inputTool      : String = ""

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
    fun setCountry(country: String) {
        inputCountry = country
    }
    fun setTool(tool: String)  {
        inputTool = tool
    }

    fun filerList() {
        for (recipe in currentSearchResult) {
            if (addRecipeIfPassCheck(recipe, recipe.sour, sourValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.bitter, bitterValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.sweet, sweetValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.flavor, flavorValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.rich, richValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.roast, roastValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.grindSize, grindSizeValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.rating, ratingValues)) continue
            if (addRecipeIfPassCheck(recipe, recipe.country, inputCountry)) continue
            addRecipeIfPassCheck(recipe, recipe.tool, inputTool)
        }
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
    private fun addRecipeIfPassCheck(recipe: CustomRecipe, recipeValue: String, inputValue: String): Boolean {
        if (inputValue.isEmpty()) return false

        if (recipeValue == inputValue) {
            filteredRecipeList.add(recipe)
            return true
        }
        return false
    }
}