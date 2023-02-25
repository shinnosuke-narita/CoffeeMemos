package com.example.coffeememos.search.bean.domain.use_case

import com.example.coffeememos.search.bean.domain.model.FilterBeanOutputData

interface GetFilterBeanOutputDataUseCase {
     fun execute(key: String): FilterBeanOutputData?
}