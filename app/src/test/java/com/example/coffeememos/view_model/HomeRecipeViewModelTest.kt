package com.example.coffeememos.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.withapp.coffeememo.home.recipe.domain.use_case.GetHomeRecipeDataUseCase
import com.withapp.coffeememo.home.recipe.domain.use_case.UpdateFavoriteUseCase
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
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
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
        _viewModel = HomeRecipeViewModel(getHomeRecipeDataUseCase, updateFavoriteUseCase)
        _presenter = mockk<HomeRecipePresenter>()
        coEvery { _presenter.presentHomeRecipeData(any()) } returns sampleData
        _viewModel.presenter = _presenter

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
        verify(exactly = 1) { _presenter.presentHomeRecipeData(any()) }
        checkObserver()
    }

    @Test
    fun test_updateHomeData() = runTest {
        val recipeIdParam = 0L
        val isFavoriteParam = false
        _viewModel.updateHomeData(recipeIdParam, isFavoriteParam)
        advanceUntilIdle()

        /** call function check */
        // todo fix this
//        coVerify(exactly = 1) { _presenter.presentHomeRecipeData(any()) }
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
        verify(exactly = 1) { _newRecipesObserver.onChanged(sampleData.newRecipes) }
        verify(exactly = 1) { _favoriteRecipesObserver.onChanged(sampleData.favoriteRecipes) }
        verify(exactly = 1) { _highRatingRecipesObserver.onChanged(sampleData.highRatingRecipes) }
        verify(exactly = 1) { _favoriteRecipeCountObserver.onChanged(sampleData.favoriteRecipes.size.toString()) }
        verify(exactly = 1) { _todayRecipeCountObserver.onChanged(sampleData.todayCount) }
        verify(exactly = 1) { _totalRecipeCountObserver.onChanged(sampleData.totalCount) }
    }
}