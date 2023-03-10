package com.example.coffeememos.home.recipe.presentation.mapper

import com.example.coffeememos.Constants
import com.example.coffeememos.home.recipe.domain.model.HomeRecipeModel
import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeCardData
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HomeRecipeCardModelMapperImpl @Inject constructor()
    : HomeRecipeCardModelMapper {
    private val pattern: String = "yyyy/MM/dd HH:mm"

    override fun execute(data: List<HomeRecipeModel>)
        : List<HomeRecipeCardData> {
        if (data.isEmpty()) return listOf()

        val result = mutableListOf<HomeRecipeCardData>()
        for (recipe in data) {
            // createdAt 変換
            val formatter = DateTimeFormatter.ofPattern(pattern)
            val createdAtStr = recipe.createdAt.format(formatter)
            // roast 変換
            val roastStr = Constants.roastList[recipe.roast]

            result.add(
                HomeRecipeCardData(
                    recipe.recipeId,
                    recipe.beanId,
                    recipe.country,
                    createdAtStr,
                    recipe.tool,
                    roastStr,
                    recipe.rating.toString(),
                    recipe.isFavorite
                )
            )
        }

        return result
    }
}