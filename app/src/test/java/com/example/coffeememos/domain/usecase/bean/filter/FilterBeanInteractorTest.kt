package com.example.coffeememos.domain.usecase.bean.filter

import com.example.coffeememos.fixture.StubData.Country.Brazil
import com.example.coffeememos.fixture.StubData.Country.Columbia
import com.example.coffeememos.fixture.StubData.Country.Ethiopia
import com.example.coffeememos.fixture.searchBeanModelFixture
import com.google.common.truth.Truth
import com.withapp.coffeememo.domain.model.bean.FilterBeanInputData
import com.withapp.coffeememo.domain.model.bean.SearchBeanModel
import com.withapp.coffeememo.domain.model.recipe.SearchRecipeModel
import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.domain.usecase.bean.filter.FilterBeanInteractor
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FilterBeanInteractorTest {
    private val beanRepoStub = mockk<BeanRepository>(relaxed = true)

    // sut
    private val filterBeanInteractor = FilterBeanInteractor(beanRepoStub)

    private val filterBeanInputDataFixture =
        FilterBeanInputData(
            keyWord = "",
            countries = listOf(),
            farms = listOf(),
            districts = listOf(),
            stores = listOf(),
            species = listOf(),
            rating = listOf(),
            process = listOf()
        )

    private val stubData =
        listOf(
            createBeanByCountry(Brazil.toString()),
            createBeanByCountry(Columbia.toString()),
            createBeanByCountry(Ethiopia.toString()),
        )

    @Before
    fun setUp() {
        setupBeanRepoStub(stubData)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun setupBeanRepoStub(stubData: List<SearchBeanModel>) {
        coEvery { beanRepoStub.getSearchBeanModel() } returns stubData
        coEvery { beanRepoStub.getSearchBeanModelByKeyword(any()) } returns stubData
    }

    @Test
    fun `when input filtering data has no values, expect result do not change`() =
        runTest {
            // When
            val result = filterBeanInteractor.filterBean(filterBeanInputDataFixture)

            // Then
            Truth.assertThat(result).isEqualTo(stubData)
        }

    @Test
    fun `when bean is filtered by single country value, expect correct result`() =
        runTest {
            // Given
            val filteringData = filterBeanInputDataFixture.copy(countries = listOf(Brazil.toString()))

            // When
            val result = filterBeanInteractor.filterBean(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.country).isIn(filteringData.countries) }
        }

    @Test
    fun `when bean is filtered by multiple country values, expect correct result`() =
        runTest {
            // Given
            val filteringData =
                filterBeanInputDataFixture.copy(
                    countries = listOf(Brazil.toString(), Ethiopia.toString())
                )

            // When
            val result = filterBeanInteractor.filterBean(filteringData)

            // Then
            verifyNotEmpty(result)
            result.forEach { Truth.assertThat(it.country).isIn(filteringData.countries) }
        }

    private fun verifyNotEmpty(result: List<SearchBeanModel>) {
        Truth.assertThat(result.isEmpty()).isFalse()
    }

    private fun createBeanByCountry(country: String): SearchBeanModel =
        searchBeanModelFixture.copy(country = country)

    private fun createBeanBy(farm: String): SearchBeanModel =
        searchBeanModelFixture.copy(farm = farm)
}