package com.withapp.coffeememo.search.bean.domain.iterator

import com.withapp.coffeememo.search.bean.domain.cache.BeanMemoryCache
import com.withapp.coffeememo.search.bean.domain.use_case.DeleteFilterBeanInputDataUseCase
import javax.inject.Inject

/**
 *  保持しているフィルター要素の削除ユースケース
 */
class DeleteFilterBeanInputDataIterator @Inject constructor()
    : DeleteFilterBeanInputDataUseCase {
    @Inject
    lateinit var memoryCache: BeanMemoryCache

    override fun handle(key: String) {
        if(key.isEmpty()) return

        memoryCache.removeData(key)
    }
}