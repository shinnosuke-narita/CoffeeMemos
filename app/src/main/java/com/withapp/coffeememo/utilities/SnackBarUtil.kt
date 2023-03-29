package com.withapp.coffeememo.utilities

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.withapp.coffeememo.R

class SnackBarUtil {
    companion object {
        // 削除完了
        fun showFinishDeleteSnackBar(
            context: Context,
            snackBarPlace: View,
            message: String
        ) {
            Snackbar.make(
                snackBarPlace,
                message,
                Snackbar.LENGTH_SHORT
            ).apply {
                setTextColor(
                    ContextCompat.getColor(
                        context, R.color.delete_color
                    )
                )
                view.setBackgroundColor(
                    ContextCompat.getColor(
                        context, R.color.white
                    )
                )
            }.show()
        }

        // 保存完了時や、更新完了時表示
        fun showSimpleSnackBar(
            context: Context,
            snackBarPlace: View,
            message: String
        ) {
            Snackbar.make(
                snackBarPlace,
                message,
                Snackbar.LENGTH_SHORT
            ).apply {
                setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.pink_dark
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
}