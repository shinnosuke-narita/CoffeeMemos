package com.example.coffeememos.search.domain.iterator

import com.example.coffeememos.search.domain.cache.RecipeMemoryCache
import com.example.coffeememos.search.domain.presenter.SearchRecipePresenter
import com.example.coffeememos.search.domain.repository.SearchRecipeDiskRepository
import com.example.coffeememos.search.domain.use_case.FreeWordSearchUseCase
import com.example.coffeememos.search.domain.model.SearchRecipeModel
import com.example.coffeememos.search.domain.use_case.DeleteFilterRecipeInputDataUseCase
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