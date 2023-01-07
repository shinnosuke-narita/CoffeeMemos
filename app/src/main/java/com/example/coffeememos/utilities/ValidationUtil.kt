package com.example.coffeememos.utilities

import android.content.Context
import com.example.coffeememos.Constants
import com.example.coffeememos.R

class ValidationUtil {
    companion object {
        const val TASTE_MAX_VALUE: Int = 5
        const val TASTE_MINIMUM_VALUE: Int = 1

        fun tasteValidate(context: Context, value: Int): String  =
            if (value == 0) {
                context.getString(R.string.taste_minimum_value_validation, TASTE_MINIMUM_VALUE)
            } else if (value > TASTE_MAX_VALUE) {
                context.getString(R.string.taste_max_value_validation, TASTE_MAX_VALUE)
            } else ""





    }
}