package com.example.coffeememos.search.recipe.presentation.adapter.`interface`

import android.view.View
import com.example.coffeememos.search.recipe.domain.model.SearchRecipeModel

interface OnFavoriteClickListener {
    fun onFavoriteClick(view: View, position: Int, recipe: SearchRecipeModel)
}