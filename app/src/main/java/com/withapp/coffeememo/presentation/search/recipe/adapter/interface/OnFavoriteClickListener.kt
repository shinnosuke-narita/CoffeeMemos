package com.withapp.coffeememo.presentation.search.recipe.adapter.`interface`

import android.view.View
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel

interface OnFavoriteClickListener {
    fun onFavoriteClick(view: View, position: Int, recipe: SearchRecipeModel)
}