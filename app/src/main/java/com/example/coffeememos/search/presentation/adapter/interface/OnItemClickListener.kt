package com.example.coffeememos.search.presentation.adapter.`interface`

import com.example.coffeememos.search.domain.model.SearchRecipeModel

interface OnItemClickListener {
   fun onItemClick(recipe: SearchRecipeModel)
}