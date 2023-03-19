package com.withapp.coffeememo.search.bean.domain.use_case

import com.withapp.coffeememo.search.bean.domain.model.FilterBeanOutputData

interface GetFilterBeanOutputDataUseCase {
     fun execute(key: String): FilterBeanOutputData?
}