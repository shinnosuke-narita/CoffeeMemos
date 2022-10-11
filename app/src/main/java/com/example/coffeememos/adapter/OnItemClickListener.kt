package com.example.coffeememos.adapter

import android.view.View

interface OnItemClickListener<T> {
    fun onClick(view: View, selectedItem: T)
}