package com.withapp.coffeememo.listener

import android.view.View

interface OnItemClickListener<T> {
    fun onClick(view: View, selectedItem: T)
}