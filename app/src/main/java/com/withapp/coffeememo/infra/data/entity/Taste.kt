package com.withapp.coffeememo.infra.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey


@Entity(
    tableName = "taste",
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["recipe_id"],
            childColumns = ["taste_recipe_id"],
            onDelete = CASCADE
        )
    ]
)
data class Taste (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(
        name = "taste_recipe_id",
        index = true
    )
    var recipeId: Long,
    val sour: Int,
    val bitter: Int,
    val sweet: Int,
    val rich: Int,
    val flavor: Int)
