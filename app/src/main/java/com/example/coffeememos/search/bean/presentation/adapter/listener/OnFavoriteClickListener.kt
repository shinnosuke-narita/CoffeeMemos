package com.example.coffeememos.search.bean.presentation.adapter.listener

import android.view.View
import com.example.coffeememos.search.bean.domain.model.SearchBeanModel

interface OnFavoriteClickListener {
    fun onFavoriteClick(view: View, position: Int, bean: SearchBeanModel)
}