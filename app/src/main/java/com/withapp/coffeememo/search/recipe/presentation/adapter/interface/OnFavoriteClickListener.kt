package com.withapp.coffeememo.search.recipe.presentation.adapter.`interface`

import android.view.View
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel

interface OnFavoriteClickListener {
    fun onFavoriteClick(view: View, position: Int, recipe: SearchRecipeModel)
}