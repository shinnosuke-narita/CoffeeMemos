package com.example.coffeememos.create.recipe.presentation.model

import com.example.coffeememos.state.InputType

data class PreInfusionTimeInfo(
    val inputType: InputType,
    val manualTime: Int,
    val autoTime: Long
)
