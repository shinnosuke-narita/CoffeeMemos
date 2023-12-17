package com.withapp.coffeememo.search.recipe.presentation.adapter.`interface`

import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel

interface OnItemClickListener {
   fun onItemClick(recipe: SearchRecipeModel)
}