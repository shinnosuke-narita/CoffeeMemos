package com.withapp.coffeememo.presentation.search.recipe.adapter.`interface`

import com.withapp.coffeememo.domain.model.recipe.SearchRecipeModel

interface OnItemClickListener {
   fun onItemClick(recipe: SearchRecipeModel)
}