package com.example.coffeememos.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.withapp.coffeememo.domain.model.recipe.HomeRecipeModel
import com.withapp.coffeememo.domain.model.recipe.HomeRecipeSource
import com.withapp.coffeememo.domain.usecase.recipe.gethomerecipe.GetHomeRecipeDataUseCase
import com.withapp.coffeememo.domain.usecase.recipe.updatefavorite.UpdateFavoriteUseCase
import com.withapp.coffeememo.presentation.home.recipe.mapper.HomeRecipeCardModelMapperImpl
import com.withapp.coffeememo.presentation.home.recipe.model.HomeRecipeCardData
import com.withapp.coffeememo.presentation.home.recipe.view_model.HomeRecipeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class HomeRecipeViewModelTest {
    companion object {
        private val recipe = HomeRecipeModel(
            recipeId = 0L,
            beanId   = 0L,
            country = "",
            createdAt = LocalDateTime.now(),
            tool     = "",
            roast    = 1,
            rating   = 1,
            isFavorite = true
        )
        val sampleData = HomeRecipeSource(
            newRecipes = listOf(recipe),
            highRatingRecipes = listOf(recipe),
            favoriteRecipes = listOf(recipe, recipe),
            totalCount = 1,
            todayCount = 2
        )
    }

    private lateinit var _viewModel: HomeRecipeViewModel
    private lateinit var  _newRecipesObserver: Observer<List<HomeRecipeCardData>>
    private lateinit var  _highRatingRecipesObserver: Observer<List<HomeRecipeCardData>>
    private lateinit var  _favoriteRecipesObserver: Observer<List<HomeRecipeCardData>>
    private lateinit var  _favoriteRecipeCountObserver: Observer<String>
    private lateinit var  _todayRecipeCountObserver: Observer<Int>
    private lateinit var  _totalRecipeCountObserver: Observer<Int>

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        val getHomeRecipeDataUseCase = mockk<GetHomeRecipeDataUseCase>(relaxed = true)
        val updateFavoriteUseCase = mockk<UpdateFavoriteUseCase>(relaxed = true)
        coEvery { getHomeRecipeDataUseCase.handle() } returns sampleData
        val mapper = HomeRecipeCardModelMapperImpl()
        _viewModel = HomeRecipeViewModel(getHomeRecipeDataUseCase, updateFavoriteUseCase, mapper)

        setUpObserver()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_getHomeRecipeData() {
        _viewModel.getHomeRecipeData()

        /** call function check */
        checkObserver()
    }

    @Test
    fun test_updateHomeData() {
        val recipeIdParam = 0L
        val isFavoriteParam = false
        _viewModel.updateHomeData(recipeIdParam, isFavoriteParam)

        /** call function check */
        checkObserver()
    }

    private fun setUpObserver() {
        _newRecipesObserver = mockk<Observer<List<HomeRecipeCardData>>>(relaxed = true)
        _highRatingRecipesObserver = mockk<Observer<List<HomeRecipeCardData>>>(relaxed = true)
        _favoriteRecipesObserver = mockk<Observer<List<HomeRecipeCardData>>>(relaxed = true)
        _favoriteRecipeCountObserver = mockk<Observer<String>>(relaxed = true)
        _todayRecipeCountObserver = mockk<Observer<Int>>(relaxed = true)
        _totalRecipeCountObserver = mockk<Observer<Int>>(relaxed = true)

        _viewModel.newRecipes.observeForever(_newRecipesObserver)
        _viewModel.favoriteRecipes.observeForever(_favoriteRecipesObserver)
        _viewModel.highRatingRecipes.observeForever(_highRatingRecipesObserver)
        _viewModel.favoriteRecipeCount.observeForever(_favoriteRecipeCountObserver)
        _viewModel.todayRecipeCount.observeForever(_todayRecipeCountObserver)
        _viewModel.totalRecipeCount.observeForever(_totalRecipeCountObserver)
    }

    private fun checkObserver() {
        /** observer check */
        val mapper = HomeRecipeCardModelMapperImpl()
        verify(exactly = 1) { _newRecipesObserver.onChanged(mapper.execute(sampleData.newRecipes)) }
        verify(exactly = 1) { _favoriteRecipesObserver.onChanged(mapper.execute(sampleData.favoriteRecipes)) }
        verify(exactly = 1) { _highRatingRecipesObserver.onChanged(mapper.execute(sampleData.highRatingRecipes)) }
        verify(exactly = 1) { _favoriteRecipeCountObserver.onChanged(sampleData.favoriteRecipes.size.toString()) }
        verify(exactly = 1) { _todayRecipeCountObserver.onChanged(sampleData.todayCount) }
        verify(exactly = 1) { _totalRecipeCountObserver.onChanged(sampleData.totalCount) }
    }
}