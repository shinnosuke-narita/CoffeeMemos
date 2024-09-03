package com.example.coffeememos.domain.usecase.recipe.filter

import com.example.coffeememos.fixture.recipeFixture
import com.example.coffeememos.fixture.tasteFixture
import com.google.common.truth.Truth
import com.withapp.coffeememo.domain.mapper.SearchRecipeModelMapper
import com.withapp.coffeememo.domain.model.recipe.FilterRecipeInputData
import com.withapp.coffeememo.domain.model.recipe.SearchRecipeModel
import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.domain.usecase.recipe.filter.FilterRecipeInteractor
import com.withapp.coffeememo.domain.usecase.recipe.filter.FilterRecipeUseCase
import com.withapp.coffeememo.infra.data.entity.RecipeWithTaste
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FilterRecipeInteractorTest {
    private val recipeRepoStub = mockk<RecipeRepository>(relaxed = true)

    // sut
    private var filterRecipeInteractor: FilterRecipeUseCase =
        FilterRecipeInteractor(
            recipeRepo = recipeRepoStub,
            mapper = SearchRecipeModelMapper()
        )

    private val filterRecipeInputDataFixture =
        FilterRecipeInputData(
            keyWord = "",
            countries = listOf(),
            tools = listOf(),
            roasts = listOf(),
            grindSizes = listOf(),
            rating = listOf(),
            sour = listOf(),
            bitter = listOf(),
            sweet = listOf(),
            flavor = listOf(),
            rich = listOf()
        )

    private val stubRecipeData =
        listOf(
            createRecipeBySour(3),
            createRecipeBySour(4),
            createRecipeBySour(5),
            createRecipeByBitter(3),
            createRecipeByBitter(4),
            createRecipeByBitter(5),
            createRecipeBySweet(3),
            createRecipeBySweet(4),
            createRecipeBySweet(5),
            createRecipeByFlavor(3),
            createRecipeByFlavor(4),
            createRecipeByFlavor(5),
            createRecipeByRich(3),
            createRecipeByRich(4),
            createRecipeByRich(5),
            createRecipeByRating(3),
            createRecipeByRating(4),
            createRecipeByRating(5),
            createRecipeByGrindSize(3),
            createRecipeByGrindSize(4),
            createRecipeByGrindSize(5),
            createRecipeByCountry(BRAZIL),
            createRecipeByCountry(COLUMBIA),
            createRecipeByCountry(COLUMBIA),
            createRecipeByTool(HARIO),
            createRecipeByTool(KARITA),
            createRecipeByTool(MERITA),
        )

    @Before
    fun setUp() {
        setupRecipeRepoStub(stubRecipeData)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when input filtering data does not has sour value, expect result do not change`() =
        runTest {
            // When
            val result = filterRecipeInteractor.filterRecipe(filterRecipeInputDataFixture)

            // Then
            Truth.assertThat(result.size).isEqualTo(stubRecipeData.size)
        }

    @Test
    fun `when recipe is filtered by single sour value, expect correct result`() =
        runTest {
            // Given
            val sourValue = 3
            val filteringData = filterRecipeInputDataFixture.copy(sour = listOf(sourValue))

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.sour).isEqualTo(sourValue) }
        }

    @Test
    fun `when recipe is filtered by some sour values, expect correct result`() =
        runTest {
            // Given
            val filteringSourValues = listOf(3, 4)
            val filteringData = filterRecipeInputDataFixture.copy(sour = filteringSourValues)

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.sour).isIn(filteringSourValues) }
        }

    @Test
    fun `when recipe is filtered by single bitter value, expect correct result`() =
        runTest {
            // Given
            val bitterValue = 3
            val filteringData = filterRecipeInputDataFixture.copy(bitter = listOf(bitterValue))

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.bitter).isEqualTo(bitterValue) }
        }

    @Test
    fun `when recipe is filtered by some bitter values, expect correct result`() =
        runTest {
            // Given
            val filteringBitterValues = listOf(3, 4)
            val filteringData = filterRecipeInputDataFixture.copy(bitter = filteringBitterValues)

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.bitter).isIn(filteringBitterValues) }
        }

    @Test
    fun `when recipe is filtered by single sweet value, expect correct result`() =
        runTest {
            // Given
            val sweetValue = 3
            val filteringData = filterRecipeInputDataFixture.copy(sweet = listOf(sweetValue))

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.sweet).isEqualTo(sweetValue) }
        }

    @Test
    fun `when recipe is filtered by some sweet values, expect correct result`() =
        runTest {
            // Given
            val filteringSweetValues = listOf(3, 4)
            val filteringData = filterRecipeInputDataFixture.copy(sweet = filteringSweetValues)

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.sweet).isIn(filteringSweetValues) }
        }

    @Test
    fun `when recipe is filtered by single flavor value, expect correct result`() =
        runTest {
            // Given
            val flavorValue = 3
            val filteringData = filterRecipeInputDataFixture.copy(flavor = listOf(flavorValue))

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.flavor).isEqualTo(flavorValue) }
        }

    @Test
    fun `when recipe is filtered by some flavor values, expect correct result`() =
        runTest {
            // Given
            val filteringFlavorValues = listOf(3, 4)
            val filteringData = filterRecipeInputDataFixture.copy(flavor = filteringFlavorValues)

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.flavor).isIn(filteringFlavorValues) }
        }

    @Test
    fun `when recipe is filtered by single rich value, expect correct result`() =
        runTest {
            // Given
            val richValue = 3
            val filteringData = filterRecipeInputDataFixture.copy(rich = listOf(richValue))

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.rich).isEqualTo(richValue) }
        }

    @Test
    fun `when recipe is filtered by some rich values, expect correct result`() =
        runTest {
            // Given
            val filteringRichValues = listOf(3, 4)
            val filteringData = filterRecipeInputDataFixture.copy(rich = filteringRichValues)

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.rich).isIn(filteringRichValues) }
        }

    @Test
    fun `when recipe is filtered by single rating value, expect correct result`() =
        runTest {
            // Given
            val filteringRatingValue = 3
            val filteringData = filterRecipeInputDataFixture.copy(rating = listOf(filteringRatingValue))

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.rating).isEqualTo(filteringRatingValue) }
        }

    @Test
    fun `when recipe is filtered by some rating values, expect correct result`() =
        runTest {
            // Given
            val filteringRatingValues = listOf(3, 4)
            val filteringData = filterRecipeInputDataFixture.copy(rating = filteringRatingValues)

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.rating).isIn(filteringRatingValues) }
        }

    @Test
    fun `when recipe is filtered by single grind size, expect correct result`() =
        runTest {
            // Given
            val filteringGrindSize = 3
            val filteringData = filterRecipeInputDataFixture.copy(grindSizes = listOf(filteringGrindSize))

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.grindSize).isEqualTo(filteringGrindSize) }
        }

    @Test
    fun `when recipe is filtered by some grind size, expect correct result`() =
        runTest {
            // Given
            val filteringGrindSize = listOf(3, 4)
            val filteringData = filterRecipeInputDataFixture.copy(grindSizes = filteringGrindSize)

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.grindSize).isIn(filteringGrindSize) }
        }

    @Test
    fun `when recipe is filtered by single country, expect correct result`() =
        runTest {
            // Given
            val filteringCountry = BRAZIL
            val filteringData = filterRecipeInputDataFixture.copy(countries = listOf(filteringCountry))

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.country).isEqualTo(filteringCountry) }
        }

    @Test
    fun `when recipe is filtered by some country, expect correct result`() =
        runTest {
            // Given
            val filteringCountryValues = listOf(COLUMBIA, ETHIOPIA)
            val filteringData = filterRecipeInputDataFixture.copy(countries = filteringCountryValues)

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.country).isIn(filteringCountryValues) }
        }

    @Test
    fun `when recipe is filtered by single tool, expect correct result`() =
        runTest {
            // Given
            val filteringTool = HARIO
            val filteringData = filterRecipeInputDataFixture.copy(tools = listOf(filteringTool))

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.tool).isEqualTo(filteringTool) }
        }

    @Test
    fun `when recipe is filtered by some tool, expect correct result`() =
        runTest {
            // Given
            val filteringTools = listOf(HARIO, KARITA)
            val filteringData = filterRecipeInputDataFixture.copy(tools = filteringTools)

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.tool).isIn(filteringTools) }
        }

    @Test
    fun `when recipe is filtered by multiple condition, expect correct result`() =
        runTest {
            // Given
            val (sour, bitter, sweet, flavor, rich) = listOf(1, 2, 3, 4, 5)
            val rating = 3
            val grindSize = 4
            val country = BRAZIL
            val tool =  HARIO
            val filteringData =
                filterRecipeInputDataFixture.copy(
                    sour = listOf(sour),
                    bitter = listOf(bitter),
                    sweet = listOf(sweet),
                    flavor = listOf(flavor),
                    rich = listOf(rich),
                    rating = listOf(rating),
                    grindSizes = listOf(grindSize),
                    countries = listOf(country),
                    tools = listOf(tool)
                )

            val stubRecipes =
                listOf(
                    RecipeWithTaste(
                        recipe = recipeFixture.copy(
                            rating = rating,
                            grindSize = grindSize,
                            country = country,
                            tool = tool
                        ),
                        taste = tasteFixture.copy(
                            sour = sour,
                            bitter = bitter,
                            sweet = sweet,
                            flavor = flavor,
                            rich = rich,
                        )
                    )
                )

            setupRecipeRepoStub(stubRecipeData + stubRecipes)

            // When
            val result = filterRecipeInteractor.filterRecipe(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { recipe ->
                Truth.assertThat(recipe.sour).isIn(filteringData.sour)
                Truth.assertThat(recipe.bitter).isIn(filteringData.bitter)
                Truth.assertThat(recipe.sweet).isIn(filteringData.sweet)
                Truth.assertThat(recipe.flavor).isIn(filteringData.flavor)
                Truth.assertThat(recipe.rich).isIn(filteringData.rich)
                Truth.assertThat(recipe.rating).isIn(filteringData.rating)
                Truth.assertThat(recipe.grindSize).isIn(filteringData.grindSizes)
                Truth.assertThat(recipe.country).isIn(filteringData.countries)
                Truth.assertThat(recipe.tool).isIn(filteringData.tools)
            }
        }

    private fun createRecipeBySour(sourValue: Int): RecipeWithTaste =
        RecipeWithTaste(recipeFixture, tasteFixture.copy(sour = sourValue))

    private fun createRecipeByBitter(bitterValue: Int): RecipeWithTaste =
        RecipeWithTaste(recipeFixture, tasteFixture.copy(bitter = bitterValue))

    private fun createRecipeBySweet(sweetValue: Int): RecipeWithTaste =
        RecipeWithTaste(recipeFixture, tasteFixture.copy(sweet = sweetValue))

    private fun createRecipeByFlavor(flavorValue: Int): RecipeWithTaste =
        RecipeWithTaste(recipeFixture, tasteFixture.copy(flavor = flavorValue))

    private fun createRecipeByRich(richValue: Int): RecipeWithTaste =
        RecipeWithTaste(recipeFixture, tasteFixture.copy(sour = richValue))

    private fun createRecipeByRating(rating: Int): RecipeWithTaste =
        RecipeWithTaste(recipeFixture.copy(rating = rating), tasteFixture)

    private fun createRecipeByCountry(country: String): RecipeWithTaste =
        RecipeWithTaste(recipeFixture.copy(country = country), tasteFixture)

    private fun createRecipeByTool(tool: String): RecipeWithTaste =
        RecipeWithTaste(recipeFixture.copy(tool = tool), tasteFixture)

    private fun createRecipeByGrindSize(grindSize: Int): RecipeWithTaste =
        RecipeWithTaste(recipeFixture.copy(grindSize = grindSize), tasteFixture)

    private fun setupRecipeRepoStub(data: List<RecipeWithTaste>) {
        coEvery {
            recipeRepoStub.getRecipeWithTaste()
        } returns data

        coEvery {
            recipeRepoStub.getRecipeWithTasteByKeyword(any())
        } returns data
    }

    private fun verifyNotEmpty(result: List<SearchRecipeModel>) {
        Truth.assertThat(result.isEmpty()).isFalse()
    }

    companion object {
        private const val BRAZIL = "Brazil"
        private const val ETHIOPIA = "Ethiopia"
        private const val COLUMBIA = "Columbia"
        private const val HARIO = "Hario"
        private const val KARITA = "Karita"
        private const val MERITA = "Merita"
    }
}