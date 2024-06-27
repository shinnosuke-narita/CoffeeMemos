package com.withapp.coffeememo.infra.data.entity

import androidx.room.*

data class RecipeWithBeans(
    @Embedded val bean: Bean,
    @Relation(
        entity = Recipe::class,
        parentColumn = "bean_id",
        entityColumn = "recipe_bean_id"
    )
    val recipes: List<Recipe>
)