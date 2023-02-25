package com.example.coffeememos.search.bean.domain.serialization

import com.example.coffeememos.search.bean.domain.model.FilterBeanInputData

interface BeanSerializer {
    fun serialize(filterBeanInputData: FilterBeanInputData): String
    fun deserialize(json: String): FilterBeanInputData?
}