package com.withapp.coffeememo.domain.usecase.bean.getFilterElements

import com.withapp.coffeememo.domain.model.bean.FilterBeanOutputData

interface GetFilterBeanOutputDataUseCase {
     fun execute(key: String): FilterBeanOutputData?
}