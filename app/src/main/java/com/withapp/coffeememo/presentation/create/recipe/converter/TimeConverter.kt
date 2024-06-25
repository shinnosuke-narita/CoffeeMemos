package com.withapp.coffeememo.presentation.create.recipe.converter

interface TimeConverter {
    fun convertPreInfusionTime(seconds: Int): Long
    fun convertExtractionTime(minutes: Int, seconds: Int): Long
}