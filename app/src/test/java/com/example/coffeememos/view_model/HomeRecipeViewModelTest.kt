package com.example.coffeememos.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.withapp.coffeememo.home.recipe.presentation.controller.HomeRecipeController
import com.withapp.coffeememo.home.recipe.presentation.model.HomeRecipeCardData
import com.withapp.coffeememo.home.recipe.presentation.model.HomeRecipeOutput
import com.withapp.coffeememo.home.recipe.presentation.presenter.HomeRecipePresenter
import com.withapp.coffeememo.home.recipe.presentation.view_model.HomeRecipeViewModel
import io.mockk.coEvery
import io.mockk.coVerify
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
        private val recipe = HomeRecipeCardData(
            recipeId = 0,
            beanId   = 0,
            country = "",
            createdAt = LocalDateTime.now(),
            tool     = "",
            roast    = "",
            rating   = "",
            isFavorite = true
        )
        val sampleData = HomeRecipeOutput(
            newRecipes = listOf(recipe),
            highRatingRecipes = listOf(recipe),
            favoriteRecipes = listOf(recipe, recipe),
            totalCount = 1,
            todayCount = 2
        )
    }

    private lateinit var _viewModel: HomeRecipeViewModel
    private lateinit var _presenter: HomeRecipePresenter
    private lateinit var _controller: HomeRecipeController
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        _viewModel = HomeRecipeViewModel()
        _controller  = mockk(relaxed = true)

        _presenter = mockk<HomeRecipePresenter>()
        coEvery { _presenter.presentHomeRecipeData(any()) } returns sampleData

        _viewModel.presenter = _presenter
        _viewModel.controller = _controller
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_getHomeRecipeData() {
        val newRecipesObserver = mockk<Observer<List<HomeRecipeCardData>>>(relaxed = true)
        val highRatingRecipesObserver = mockk<Observer<List<HomeRecipeCardData>>>(relaxed = true)
        val favoriteRecipesObserver = mockk<Observer<List<HomeRecipeCardData>>>(relaxed = true)
        val favoriteRecipeCountObserver = mockk<Observer<String>>(relaxed = true)
        val todayRecipeCountObserver = mockk<Observer<Int>>(relaxed = true)
        val totalRecipeCountObserver = mockk<Observer<Int>>(relaxed = true)

        _viewModel.newRecipes.observeForever(newRecipesObserver)
        _viewModel.favoriteRecipes.observeForever(favoriteRecipesObserver)
        _viewModel.highRatingRecipes.observeForever(highRatingRecipesObserver)
        _viewModel.favoriteRecipeCount.observeForever(favoriteRecipeCountObserver)
        _viewModel.todayRecipeCount.observeForever(todayRecipeCountObserver)
        _viewModel.totalRecipeCount.observeForever(totalRecipeCountObserver)

        _viewModel.getHomeRecipeData()

        /** observer check */
        verify(exactly = 1) { newRecipesObserver.onChanged(sampleData.newRecipes) }
        verify(exactly = 1) { favoriteRecipesObserver.onChanged(sampleData.favoriteRecipes) }
        verify(exactly = 1) { highRatingRecipesObserver.onChanged(sampleData.highRatingRecipes) }
        verify(exactly = 1) { favoriteRecipeCountObserver.onChanged(sampleData.favoriteRecipes.size.toString()) }
        verify(exactly = 1) { todayRecipeCountObserver.onChanged(sampleData.todayCount) }
        verify(exactly = 1) { totalRecipeCountObserver.onChanged(sampleData.totalCount) }

        /** call function check */
        verify(exactly = 1) { _presenter.presentHomeRecipeData(any()) }
        coVerify(exactly = 1) { _controller.getHomeRecipeData() }
    }
}