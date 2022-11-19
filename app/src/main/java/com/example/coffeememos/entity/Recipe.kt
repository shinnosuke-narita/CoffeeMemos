package com.example.coffeememos.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(
    tableName = "recipe",
    foreignKeys = [
        ForeignKey(
            entity = Bean::class,
            parentColumns = ["bean_id"],
            childColumns = ["recipe_bean_id"],
            onDelete = CASCADE
        )
    ])
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipe_id" ) val id: Long,
    @ColumnInfo(
        name = "recipe_bean_id",
        index = true
    )
    var beanId: Long,
    val tool: String,
    val roast: Int,
    val extractionTime: Long,
    val preInfusionTime: Long,
    val amountExtraction: Int,
    val temperature: Int,
    val grindSize: Int,
    val amountOfBeans: Int,
    val comment: String,
    val isFavorite: Boolean,
    val rating: Int,
    val createdAt: Long
)