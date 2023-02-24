package com.example.coffeememos.search.recipe.domain.iterator

import com.example.coffeememos.search.recipe.domain.cache.RecipeMemoryCache
import com.example.coffeememos.search.recipe.domain.use_case.DeleteFilterRecipeInputDataUseCase
import javax.inject.Inject

/**
 *  保持しているフィルター要素の削除ユースケース
 */
class DeleteFilterRecipeInputDataIterator @Inject constructor()
    : DeleteFilterRecipeInputDataUseCase {
    @Inject
    lateinit var memoryCache: RecipeMemoryCache

    override fun handle(key: String) {
        if(key.isEmpty()) return

        memoryCache.removeData(key)
    }
}