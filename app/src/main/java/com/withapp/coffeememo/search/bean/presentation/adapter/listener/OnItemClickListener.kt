package com.withapp.coffeememo.search.bean.presentation.adapter.listener

import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel

interface OnItemClickListener {
   fun onItemClick(bean: SearchBeanModel)
}