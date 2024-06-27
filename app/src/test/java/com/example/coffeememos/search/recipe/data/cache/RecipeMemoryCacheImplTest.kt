package com.withapp.coffeememo.search.recipe.data.cache

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

internal class RecipeMemoryCacheImplTest {
    lateinit var target: RecipeCacheRepositoryImpl
    private val initialDataKey: String = "initialKey"
    private val initialDataValue: String = "initialValue"

    @Before
    fun setup() {
        target = RecipeCacheRepositoryImpl
        target.setData(initialDataKey, initialDataValue)
    }

    @Test
    fun should_return_add_data() {
        val key = "key_one"
        val value = "value_one"
        target.setData(key, value)
        val res = target.cacheData[key]

        Truth.assertThat(res).isEqualTo(value)
    }

    @Test
    fun should_return_initial_data() {
        val res = target.getData(initialDataKey)

        Truth.assertThat(res).isEqualTo(initialDataValue)
    }

    @Test
    fun should_return_emptyString_when_get_removedData() {
        target.removeData(initialDataKey)
        val res = target.cacheData[initialDataKey]

        Truth.assertThat(res).isNull()
    }

    @Test
    fun should_do_nothing_when_set_empty_key() {
        target.setData("", "001")
        val res = target.cacheData[""]

        Truth.assertThat(res).isNull()
    }

    @Test
    fun should_do_nothing_when_getData_by_empty_key() {
        val res = target.getData("")

        Truth.assertThat(res).isEmpty()
    }

    @Test
    fun should_return_null_when_remove_by_empty_key() {
        val res = target.removeData("")

        Truth.assertThat(res).isNull()
    }
}