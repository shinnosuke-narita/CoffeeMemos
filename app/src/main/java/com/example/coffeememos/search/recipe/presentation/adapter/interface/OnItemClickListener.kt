package com.example.coffeememos.search.recipe.presentation.adapter.`interface`

import com.example.coffeememos.search.recipe.domain.model.SearchRecipeModel

interface OnItemClickListener {
   fun onItemClick(recipe: SearchRecipeModel)
}