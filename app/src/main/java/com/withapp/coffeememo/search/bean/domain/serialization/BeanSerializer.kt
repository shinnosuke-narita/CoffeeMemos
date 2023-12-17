package com.withapp.coffeememo.search.bean.domain.serialization

import com.withapp.coffeememo.search.bean.domain.model.FilterBeanInputData

interface BeanSerializer {
    fun serialize(filterBeanInputData: FilterBeanInputData): String
    fun deserialize(json: String): FilterBeanInputData?
}