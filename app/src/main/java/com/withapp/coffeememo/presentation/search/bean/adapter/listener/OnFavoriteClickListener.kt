package com.withapp.coffeememo.presentation.search.bean.adapter.listener

import android.view.View
import com.withapp.coffeememo.domain.model.bean.SearchBeanModel

interface OnFavoriteClickListener {
    fun onFavoriteClick(view: View, position: Int, bean: SearchBeanModel)
}