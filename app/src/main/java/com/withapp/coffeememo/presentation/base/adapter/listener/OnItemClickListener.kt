package com.withapp.coffeememo.presentation.base.adapter.listener

import android.view.View

interface OnItemClickListener<T> {
    fun onClick(view: View, selectedItem: T)
}