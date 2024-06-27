package com.withapp.coffeememo.presentation.favorite.recipe.mapper

import com.withapp.coffeememo.infra.ad_mob.locale.LocalizationManager
import com.withapp.coffeememo.infra.data.entity.Recipe
import com.withapp.coffeememo.presentation.favorite.recipe.model.FavoriteRecipeModel
import javax.inject.Inject

class FavoriteRecipeModelMapperImpl @Inject constructor()
    : FavoriteRecipeModelMapper {

    override fun execute(recipe: Recipe): FavoriteRecipeModel {
        // 焙煎度 変換
        val roastList: List<String> =
            LocalizationManager.getRoastList()
        val roast: String = roastList[recipe.roast]
        // 粒度 変換
        val grindSizeList: List<String> =
            LocalizationManager.getGrindSizeList()
        val grindSize: String = grindSizeList[recipe.grindSize]

        return FavoriteRecipeModel(
            recipe.id,
            recipe.beanId,
            recipe.country,
            recipe.tool,
            roast,
            recipe.extractionTime,
            recipe.preInfusionTime,
            recipe.amountExtraction.toString(),
            recipe.temperature.toString(),
            grindSize,
            recipe.amountOfBeans.toString(),
            recipe.comment,
            recipe.isFavorite,
            recipe.rating,
            recipe.createdAt
        )

    }
}