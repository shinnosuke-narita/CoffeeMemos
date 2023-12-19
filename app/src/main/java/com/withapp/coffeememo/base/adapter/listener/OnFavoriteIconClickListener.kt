package com.withapp.coffeememo.base.adapter.listener

import android.view.View

interface OnFavoriteIconClickListener <T> {
    fun onFavoriteClick(view: View, data: T)
}
