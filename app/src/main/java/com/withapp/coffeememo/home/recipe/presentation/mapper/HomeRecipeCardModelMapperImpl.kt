package com.withapp.coffeememo.home.recipe.presentation.mapper

import com.withapp.coffeememo.core.ad_mob.locale.LocalizationManager
import com.withapp.coffeememo.home.recipe.domain.model.HomeRecipeModel
import com.withapp.coffeememo.home.recipe.presentation.model.HomeRecipeCardData
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HomeRecipeCardModelMapperImpl @Inject constructor()
    : HomeRecipeCardModelMapper {
    override fun execute(data: List<HomeRecipeModel>)
        : List<HomeRecipeCardData> {
        if (data.isEmpty()) return listOf()

        val result = mutableListOf<HomeRecipeCardData>()
        for (recipe in data) {
            // roast 変換
            val roastList: List<String> =
                LocalizationManager.getRoastList()
            val roastStr = roastList[recipe.roast]

            result.add(
                HomeRecipeCardData(
                    recipe.recipeId,
                    recipe.beanId,
                    recipe.country,
                    recipe.createdAt,
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