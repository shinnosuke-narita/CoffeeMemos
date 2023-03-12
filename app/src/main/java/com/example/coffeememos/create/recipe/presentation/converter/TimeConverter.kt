package com.example.coffeememos.create.recipe.presentation.converter

interface TimeConverter {
    fun convertPreInfusionTime(seconds: Int): Long
    fun convertExtractionTime(minutes: Int, seconds: Int): Long
}