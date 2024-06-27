package com.withapp.coffeememo.domain.usecase.bean.deletefilterelement

import com.withapp.coffeememo.search.bean.domain.cache.BeanCacheRepository
import javax.inject.Inject

/**
 *  保持しているフィルター要素の削除ユースケース
 */
class DeleteFilterBeanInputDataIterator @Inject constructor()
    : DeleteFilterBeanInputDataUseCase {
    @Inject
    lateinit var memoryCache: BeanCacheRepository

    override fun handle(key: String) {
        if(key.isEmpty()) return

        memoryCache.removeData(key)
    }
}