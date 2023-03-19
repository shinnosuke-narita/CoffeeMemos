package com.withapp.coffeememo.create.recipe.presentation.converter

interface TimeConverter {
    fun convertPreInfusionTime(seconds: Int): Long
    fun convertExtractionTime(minutes: Int, seconds: Int): Long
}