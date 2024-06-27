package com.withapp.coffeememo.presentation.search.recipe.adapter.`interface`

import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel

interface OnItemClickListener {
   fun onItemClick(recipe: SearchRecipeModel)
}