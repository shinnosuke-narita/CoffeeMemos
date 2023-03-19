package com.withapp.coffeememo.validate

import android.content.Context
import com.withapp.coffeememo.R

class BeanValidationLogic {
    companion object {
        fun validateCountry(context: Context, value: String): String {
            var validateMessage = ""
            if (value.trim().isEmpty()) {
                validateMessage = context.getString(R.string.country_validation_message)
            }

            return validateMessage
        }
    }
}