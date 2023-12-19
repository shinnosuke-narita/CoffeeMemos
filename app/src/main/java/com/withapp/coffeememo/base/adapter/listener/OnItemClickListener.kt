package com.withapp.coffeememo.base.adapter.listener

import android.view.View

interface OnItemClickListener<T> {
    fun onClick(view: View, selectedItem: T)
}