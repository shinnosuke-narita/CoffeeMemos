package com.withapp.coffeememo.create.recipe.presentation.model

import com.withapp.coffeememo.state.InputType

data class PreInfusionTimeInfo(
    val inputType: InputType,
    val manualTime: Int,
    val autoTime: Long
)
