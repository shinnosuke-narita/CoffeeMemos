package com.withapp.coffeememo.presentation.create.recipe.model

import com.withapp.coffeememo.presentation.state.InputType

data class PreInfusionTimeInfo(
    val inputType: InputType,
    val manualTime: Int,
    val autoTime: Long
)
