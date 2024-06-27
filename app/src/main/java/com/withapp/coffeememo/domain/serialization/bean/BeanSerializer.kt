package com.withapp.coffeememo.domain.serialization.bean

import com.withapp.coffeememo.domain.model.bean.FilterBeanInputData

interface BeanSerializer {
    fun serialize(filterBeanInputData: FilterBeanInputData): String
    fun deserialize(json: String): FilterBeanInputData?
}