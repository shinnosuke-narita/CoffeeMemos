package com.example.coffeememos.listener

import android.view.View

interface OnItemClickListener<T> {
    fun onClick(view: View, selectedItem: T)
}