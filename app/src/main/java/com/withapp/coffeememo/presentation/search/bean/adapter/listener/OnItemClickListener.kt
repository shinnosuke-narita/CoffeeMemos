package com.withapp.coffeememo.presentation.search.bean.adapter.listener

import com.withapp.coffeememo.domain.model.bean.SearchBeanModel

interface OnItemClickListener {
   fun onItemClick(bean: SearchBeanModel)
}