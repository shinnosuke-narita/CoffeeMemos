package com.withapp.coffeememo.domain.usecase.recipe.createcrecipe

import com.withapp.coffeememo.infra.data.entity.Recipe
import com.withapp.coffeememo.infra.data.entity.Taste
import com.withapp.coffeememo.entity.InputData
import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.domain.repository.TasteRepository
import javax.inject.Inject

class CreateRecipeAndTasteInteractor @Inject constructor(
    private val recipeRepo: RecipeRepository,
    private val tasteRepo: TasteRepository
) : CreateRecipeAndTasteUseCase {
    override suspend fun handle(
        inputData: InputData
    ) {
        // レシピ保存処理
        recipeRepo.insert(
            Recipe(
                id = 0,
                beanId = inputData.beanId,
                country = inputData.country,
                tool = inputData.tool,
                roast = inputData.roast,
                extractionTime = inputData.extractionTime,
                preInfusionTime = inputData.preInfusionTime,
                amountExtraction = inputData.amountExtraction,
                temperature = inputData.temperature,
                grindSize = inputData.grindSize,
                amountOfBeans = inputData.amountOfBean,
                comment =inputData.comment,
                isFavorite = inputData.isFavorite,
                rating = inputData.rating,
                createdAt = inputData.createdAt
            )
        )

        // TasteのレシピID取得
        val recipeId = recipeRepo.getNewestRecipeId()

        // Taste 保存処理
        tasteRepo.insert(
            Taste(
                id = 0,
                recipeId = recipeId,
                sour = inputData.sour,
                bitter = inputData.bitter,
                sweet = inputData.sweet,
                flavor = inputData.flavor,
                rich = inputData.rich
            )
        )
    }
}