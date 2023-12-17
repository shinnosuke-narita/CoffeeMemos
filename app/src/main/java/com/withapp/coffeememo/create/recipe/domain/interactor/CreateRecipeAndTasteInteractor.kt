package com.withapp.coffeememo.create.recipe.domain.interactor

import com.withapp.coffeememo.create.recipe.domain.model.InputData
import com.withapp.coffeememo.create.recipe.domain.repository.StorageRepository
import com.withapp.coffeememo.create.recipe.domain.use_case.CreateRecipeAndTasteUseCase
import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.core.data.entity.Taste
import javax.inject.Inject

class CreateRecipeAndTasteInteractor @Inject constructor()
    : CreateRecipeAndTasteUseCase {
    @Inject
    lateinit var repository: StorageRepository

    override suspend fun handle(
        inputData: InputData
    ) {
        // レシピ保存処理
        repository.createRecipe(
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
        val recipeId = repository.getNewestRecipeId()

        // Taste 保存処理
        repository.createTaste(
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