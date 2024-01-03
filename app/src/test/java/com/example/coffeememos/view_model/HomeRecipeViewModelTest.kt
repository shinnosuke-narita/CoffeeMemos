package com.example.coffeememos.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.withapp.coffeememo.home.recipe.presentation.controller.HomeRecipeController
import com.withapp.coffeememo.home.recipe.presentation.model.HomeRecipeCardData
import com.withapp.coffeememo.home.recipe.presentation.model.HomeRecipeOutput
import com.withapp.coffeememo.home.recipe.presentation.presenter.HomeRecipePresenter
import com.withapp.coffeememo.home.recipe.presentation.view_model.HomeRecipeViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
            highRatingRecipes = listOf(),
            favoriteRecipes = listOf(),
            totalCount = 0,
            todayCount = 0,
            favoriteCount = 0
        )
    }

    @RelaxedMockK
    private lateinit var observer: Observer<List<HomeRecipeCardData>>
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
        observer = mockk<Observer<List<HomeRecipeCardData>>>(relaxed = true)
        _viewModel.newRecipes.observeForever(observer)

        _viewModel.getHomeRecipeData()

        verify(exactly = 1) { observer.onChanged(sampleData.newRecipes) }
    }
}