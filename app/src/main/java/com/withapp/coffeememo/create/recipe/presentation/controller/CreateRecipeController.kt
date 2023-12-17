package com.withapp.coffeememo.create.recipe.presentation.controller

import com.withapp.coffeememo.create.recipe.presentation.model.ExtractionTimeInfo
import com.withapp.coffeememo.create.recipe.presentation.model.PreInfusionTimeInfo

interface CreateRecipeController {
    suspend fun createRecipeAntTaste(
       beanId: Long,
       country: String,
       tool: String,
       roast: Int,
       extractionTimeInfo: ExtractionTimeInfo,
       preInfusionTimeInfo: PreInfusionTimeInfo,
       amountExtraction: Int,
       temperature: Int,
       grindSize: Int,
       amountOfBean: Int,
       comment: String,
       isFavorite: Boolean,
       rating: Int,
       sour: Int,
       bitter: Int,
       sweet: Int,
       flavor: Int,
       rich: Int
    )

    suspend fun getBeanCount(): Int
}