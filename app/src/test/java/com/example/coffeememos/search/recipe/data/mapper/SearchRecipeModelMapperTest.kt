package com.withapp.coffeememo.search.recipe.data.mapper

import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.core.data.entity.RecipeWithTaste
import com.withapp.coffeememo.core.data.entity.Taste
import com.withapp.coffeememo.domain.model.recipe.SearchRecipeModel
import com.google.common.truth.Truth
import com.withapp.coffeememo.domain.mapper.SearchRecipeModelMapper
import org.junit.Before
import org.junit.Test


internal class SearchRecipeModelMapperTest {
    // テスト対象
    lateinit var target: SearchRecipeModelMapper

    // data
    lateinit var recipe: Recipe
    lateinit var taste: Taste
    lateinit var recipeWithTaste: RecipeWithTaste

    @Before
    fun setUp() {
        target = SearchRecipeModelMapper()

        // データのマッピングのテストのため、実際にはありえない数値が入っている
        recipe = Recipe(
            1111,
            11,
            "ブラジル",
            "hario",
            22,
            33,
            44,
            55,
            66,
            77,
            88,
            "おいしい！",
            true,
            99,
            111
        )
        taste = Taste(
            99,
            1111,
            1,
            2,
            3,
            4,
            5
        )
        recipeWithTaste = RecipeWithTaste(recipe, taste)
    }

    @Test
    fun test_return_searchRecipeModel_instance() {
        val result: SearchRecipeModel =
            target.execute(recipeWithTaste)

        Truth
            .assertThat(result)
            .isInstanceOf(SearchRecipeModel::class.java)
    }

    @Test
    fun test_execute() {
        val result: SearchRecipeModel =
            target.execute(recipeWithTaste)

        Truth.assertThat(result.recipeId).isEqualTo(1111)
        Truth.assertThat(result.beanId).isEqualTo(11)
        Truth.assertThat(result.country).isEqualTo("ブラジル")
        Truth.assertThat(result.tool).isEqualTo("hario")
        Truth.assertThat(result.roast).isEqualTo(22)
        Truth.assertThat(result.grindSize).isEqualTo(77)
        Truth.assertThat(result.createdAt).isEqualTo(111)
        Truth.assertThat(result.sour).isEqualTo(1)
        Truth.assertThat(result.bitter).isEqualTo(2)
        Truth.assertThat(result.sweet).isEqualTo(3)
        Truth.assertThat(result.rich).isEqualTo(4)
        Truth.assertThat(result.flavor).isEqualTo(5)
        Truth.assertThat(result.rating).isEqualTo(99)
        Truth.assertThat(result.isFavorite).isEqualTo(true)
    }
}