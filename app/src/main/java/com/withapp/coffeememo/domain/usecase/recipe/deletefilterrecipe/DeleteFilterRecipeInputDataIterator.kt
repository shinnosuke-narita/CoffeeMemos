package com.withapp.coffeememo.domain.usecase.recipe.deletefilterrecipe

import com.withapp.coffeememo.search.recipe.domain.cache.RecipeMemoryCache
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