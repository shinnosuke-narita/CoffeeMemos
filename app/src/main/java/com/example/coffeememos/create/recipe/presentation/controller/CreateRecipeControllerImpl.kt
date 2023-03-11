package com.example.coffeememos.create.recipe.presentation.controller

class CreateRecipeControllerImpl : CreateRecipeController {
    override fun createRecipe(
        beanId: Long,
        country: String,
        tool: String,
        roast: Int,
        extractionTime: Int,
        preInfusionTime: Int,
        amountExtraction: Int,
        temperature: Int,
        grindSize: Int,
        amountOfBean: Int,
        comment: String,
        isFavorite: Boolean,
        rating: Int
    ) {
        val createdAt: Long = System.currentTimeMillis()


    }
}