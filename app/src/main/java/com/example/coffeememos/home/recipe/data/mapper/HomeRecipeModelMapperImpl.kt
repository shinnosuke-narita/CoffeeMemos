package com.example.coffeememos.home.recipe.data.mapper

import com.example.coffeememos.entity.RecipeWithBeans
import com.example.coffeememos.home.recipe.domain.model.HomeRecipeModel
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class HomeRecipeModelMapperImpl @Inject constructor()
    : HomeRecipeModelMapper {

    override fun execute(
        recipeWithBeans: List<RecipeWithBeans>
    ) : List<HomeRecipeModel> {
        val result: MutableList<HomeRecipeModel> = mutableListOf()

        for (recipeWithBean in recipeWithBeans) {
            val bean = recipeWithBean.bean
            for (recipe in recipeWithBean.recipes) {
                // LocalDateTime型に変換
                val createdAt =
                    convertLocalDate(recipe.createdAt)
                    ?: return listOf()

                result.add(
                    HomeRecipeModel(
                        recipe.id,
                        recipe.beanId,
                        bean.country,
                        createdAt,
                        recipe.tool,
                        recipe.roast,
                        recipe.rating,
                        recipe.isFavorite
                    )
                )
            }
        }

        return result
    }

    private fun convertLocalDate(createdAt: Long): LocalDateTime? {
        val createdDateTime: LocalDateTime?
            try {
                createdDateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(createdAt),
                    ZoneId.systemDefault()
                )
            } catch (e: DateTimeException) {
                e.printStackTrace()
                return null
            }

        return createdDateTime
    }
}