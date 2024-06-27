package com.withapp.coffeememo.domain.usecase.bean.getFilterElements

import com.withapp.coffeememo.search.bean.domain.model.FilterBeanOutputData

interface GetFilterBeanOutputDataUseCase {
     fun execute(key: String): FilterBeanOutputData?
}