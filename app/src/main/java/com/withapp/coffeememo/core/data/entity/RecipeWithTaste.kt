package com.withapp.coffeememo.core.data.entity

import androidx.annotation.Nullable
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