package com.example.coffeememos.create.recipe.domain.use_case

import com.example.coffeememos.entity.Taste

interface CreateTasteUseCase {
    fun handle(taste: Taste)
}