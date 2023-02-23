package com.example.coffeememos.listener

import android.view.View

interface OnFavoriteIconClickListener <T> {
    fun onFavoriteClick(view: View, data: T)
}
