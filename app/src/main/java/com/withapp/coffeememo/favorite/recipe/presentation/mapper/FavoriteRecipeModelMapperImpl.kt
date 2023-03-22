package com.withapp.coffeememo.favorite.recipe.presentation.mapper

import com.withapp.coffeememo.Constants
import com.withapp.coffeememo.entity.Recipe
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel
import com.withapp.coffeememo.utilities.DateUtil
import javax.inject.Inject

class FavoriteRecipeModelMapperImpl @Inject constructor()
    : FavoriteRecipeModelMapper {

    override fun execute(recipe: Recipe): FavoriteRecipeModel {
        // createdAt 変換
        val createdAt: String =
            DateUtil.formatEpochTimeMills(
                recipe.createdAt,
                DateUtil.pattern
            )
        // 抽出時間 変換
        val extractionTime: String =
            DateUtil.formatExtractionTime(recipe.extractionTime)
        // 蒸らし時間 変換
        val preInfusionTime: String =
            DateUtil.formatPreInfusionTime(recipe.preInfusionTime)
        // 焙煎度 変換
        val roast: String =
            Constants.roastList[recipe.roast]
        // 粒度 変換
        val grindSize: String =
            Constants.grindSizeList[recipe.grindSize]

        return FavoriteRecipeModel(
            recipe.id,
            recipe.beanId,
            recipe.country,
            recipe.tool,
            roast,
            extractionTime,
            preInfusionTime,
            recipe.amountExtraction.toString(),
            recipe.temperature.toString(),
            grindSize,
            recipe.amountOfBeans.toString(),
            recipe.comment,
            recipe.isFavorite,
            recipe.rating,
            createdAt
        )

    }
}