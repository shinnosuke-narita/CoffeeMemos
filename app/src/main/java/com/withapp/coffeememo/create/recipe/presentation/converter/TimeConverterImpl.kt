package com.withapp.coffeememo.create.recipe.presentation.converter

import javax.inject.Inject

class TimeConverterImpl @Inject constructor()
    : TimeConverter {
    override fun convertPreInfusionTime(seconds: Int): Long {
        return (seconds * 1000).toLong()

    }

    override fun convertExtractionTime(
        minutes: Int,
        seconds: Int
    ): Long {
        val totalMills = (minutes * 60 + seconds) * 1000

        return totalMills.toLong()
    }
}