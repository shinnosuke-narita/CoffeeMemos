package com.example.coffeememos.fragment

import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.coffeememos.utilities.ViewUtil
import com.example.coffeememos.validate.ValidationInfo
import com.example.coffeememos.validate.ValidationState

open class BaseFragment : Fragment() {
    protected fun setUpValidationMessage(validationInfo: ValidationInfo, scrollView: ScrollView, header: View, messageView: TextView, titleView: View) {
        if (validationInfo.state == ValidationState.ERROR) {
            // scroll 処理
            ViewUtil.scrollToTargetView(requireActivity(), scrollView, header, titleView)

            messageView.visibility = View.VISIBLE
            messageView.text = validationInfo.message
        } else {
            messageView.visibility = View.GONE
            messageView.text = validationInfo.message
        }
    }
}