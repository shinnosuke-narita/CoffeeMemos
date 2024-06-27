package com.withapp.coffeememo.presentation.search.recipe.adapter.`interface`

import android.view.View
import com.withapp.coffeememo.domain.model.recipe.SearchRecipeModel

interface OnFavoriteClickListener {
    fun onFavoriteClick(view: View, position: Int, recipe: SearchRecipeModel)
}