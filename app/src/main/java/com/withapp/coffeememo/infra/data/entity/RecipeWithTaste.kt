package com.withapp.coffeememo.infra.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithTaste(
    @Embedded val recipe: Recipe,
    @Relation(
        entity = Taste::class,
        parentColumn = "recipe_id",
        entityColumn = "taste_recipe_id"
    )
    val taste: Taste
)