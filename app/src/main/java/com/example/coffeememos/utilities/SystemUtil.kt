package com.example.coffeememos.utilities

import android.content.Context
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContentProviderCompat.requireContext

class SystemUtil {
    companion object {
        fun hideKeyBoard(context: Context, rootView: ViewGroup) {
            val inputMService = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMService.hideSoftInputFromWindow(rootView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}