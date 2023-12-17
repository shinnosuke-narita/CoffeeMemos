package com.withapp.coffeememo.create.recipe.presentation.controller

import com.withapp.coffeememo.create.recipe.domain.model.InputData
import com.withapp.coffeememo.create.recipe.domain.use_case.CreateRecipeAndTasteUseCase
import com.withapp.coffeememo.create.recipe.domain.use_case.GetBeanCountUseCase
import com.withapp.coffeememo.create.recipe.presentation.converter.TimeConverter
import com.withapp.coffeememo.create.recipe.presentation.model.ExtractionTimeInfo
import com.withapp.coffeememo.create.recipe.presentation.model.PreInfusionTimeInfo
import com.withapp.coffeememo.state.InputType
import javax.inject.Inject

class CreateRecipeControllerImpl @Inject constructor()
    : CreateRecipeController {
    @Inject
    lateinit var createRecipeAndTasteUseCase: CreateRecipeAndTasteUseCase
    @Inject
    lateinit var converter: TimeConverter
    @Inject
    lateinit var getBeanCountUseCase: GetBeanCountUseCase

    override suspend fun createRecipeAntTaste(
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
    ) {
        // 作成日時
        val createdAt = System.currentTimeMillis()

        // 蒸らし時間
        val preInfusionTimeRes: Long =
            if (preInfusionTimeInfo.inputType ==  InputType.AUTO) {
               preInfusionTimeInfo.autoTime
            } else {
                converter.convertPreInfusionTime(
                    preInfusionTimeInfo.manualTime)
            }

        // 抽出時間
        val extractionTimeRes: Long =
            if (extractionTimeInfo.inputType == InputType.AUTO) {
                extractionTimeInfo.autoTime
            } else {
                converter.convertExtractionTime(
                    extractionTimeInfo.minutes,
                    extractionTimeInfo.seconds
                )
            }

        createRecipeAndTasteUseCase.handle(
            InputData(
                beanId = beanId,
                country = country,
                tool = tool,
                roast = roast,
                extractionTime = extractionTimeRes,
                preInfusionTime = preInfusionTimeRes,
                amountExtraction = amountExtraction,
                temperature = temperature,
                grindSize = grindSize,
                amountOfBean = amountOfBean,
                comment = comment,
                isFavorite = isFavorite,
                rating = rating,
                createdAt = createdAt,
                sour = sour,
                bitter = bitter,
                sweet = sweet,
                flavor = flavor,
                rich = rich
            )
        )
    }

    override suspend fun getBeanCount(): Int {
        return getBeanCountUseCase.handle()
    }
}