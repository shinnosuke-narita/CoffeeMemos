package com.withapp.coffeememo.favorite.common.presentation.view

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.withapp.coffeememo.R

class DeleteFavoriteSnackBar <T>{
    fun show(
        context: Context,
        snackbarPlace: View,
        model: T,
        deleteProcess: (model: T) -> Unit
    ) {
        Snackbar.make(
            snackbarPlace,
            context.getString(R.string.favorite_delete_message),
            Snackbar.LENGTH_LONG
        ).apply {
            setAction(
                context.getString(R.string.favorite_snack_bar_btn_message)
            ) {
                // db 更新
                deleteProcess(model)
            }
            setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.shadow_color
                )
            )
            setActionTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.link_color
                )
            )
            view.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
        }.show()

    }
}