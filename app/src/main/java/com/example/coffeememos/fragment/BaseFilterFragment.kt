package com.example.coffeememos.fragment

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.coffeememos.R
import com.example.coffeememos.state.MenuState
import com.example.coffeememos.utilities.AnimUtil

open class BaseFilterFragment : Fragment() {

    protected fun addFilterElementView(elementTxt: String, filterContainer: ViewGroup, dataList: List<String>, deleteValueProcess: (String) -> Unit) {
        val itemView = layoutInflater.inflate(R.layout.filtered_element_text, null)
        itemView.findViewById<TextView>(R.id.valueText).text = elementTxt
        itemView.findViewById<ImageView>(R.id.deleteBtn).setOnClickListener {
            deleteValueProcess(elementTxt)
            remakeView(elementTxt, filterContainer, dataList, deleteValueProcess)
        }

        // 子ビューのwidthを計算
        val itemWidth = getWrapContentWidth(itemView)

        if (filterContainer.childCount > 0) {
            val lastIndex = filterContainer.childCount - 1
            val linearLayout = filterContainer.getChildAt(lastIndex) as ViewGroup

            var currentTotalChildViewWidth = 0
            for (index in 0 until linearLayout.childCount) {
                currentTotalChildViewWidth += getWrapContentWidth(linearLayout.getChildAt(index))
            }

            val currentMargin = getMatchParentWidth(linearLayout) - currentTotalChildViewWidth
            if (currentMargin > itemWidth) {
                linearLayout.addView(itemView)
            } else {
                val newLinearLayout = setUpLinearLayout()
                newLinearLayout.addView(itemView)
                filterContainer.addView(newLinearLayout)
            }
        } else {
            val newLinearLayout = setUpLinearLayout()
            newLinearLayout.addView(itemView)
            filterContainer.addView(newLinearLayout)
        }
    }

    protected fun setUpEditTextContainer(filterContainer: ViewGroup, dataList: List<String>, deleteValueProcess: (String) -> Unit) {
        if (dataList.isEmpty()) return

        for (text in dataList) {
            addFilterElementView(text, filterContainer, dataList, deleteValueProcess)
        }
    }

    protected fun remakeView(inputText: String, container: ViewGroup, valuesList: List<String>, deleteValueProcess: (String) -> Unit) {
        container.removeAllViews()

        if (valuesList.isEmpty())  return

        for (text in valuesList) {
            addFilterElementView(inputText, container, valuesList, deleteValueProcess)
        }
    }

    protected fun getWrapContentWidth(view: View): Int {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        return view.measuredWidth
    }

    protected fun getMatchParentWidth(view: View): Int {
        val parentWidth = (view.parent as View).width

        view.measure(View.MeasureSpec.makeMeasureSpec(parentWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        return view.measuredWidth
    }

    protected fun setUpLinearLayout(): ViewGroup {
        val newLinearLayout = LinearLayout(requireContext())
        newLinearLayout.orientation = LinearLayout.HORIZONTAL
        newLinearLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return newLinearLayout
    }

    protected fun setUpRadioBtnContainer(dataList: List<String>, containerView: ViewGroup, viewList: MutableList<View>, clickListenerProcess:(Int) -> Unit ) {
        for ((i, data) in dataList.withIndex()) {
            val itemView = layoutInflater.inflate(R.layout.radio_btn_item, null)
            itemView.findViewById<TextView>(R.id.name).text = data
            itemView.setOnClickListener {
                clickListenerProcess(i)
            }
            containerView.addView(itemView)

            viewList.add(itemView)
        }
    }

    protected fun updateFilteringView(text: String, view: TextView) {
        if (text.isEmpty()) {
            view.visibility = View.GONE
            return
        }

        view.visibility = View.VISIBLE
        view.text = text
    }

    protected fun setRadioBtnResource(btnStateList: List<Boolean>, getRadioBtnView: (Int) -> ImageView) {
        for ((i, isSelected) in btnStateList.withIndex()) {
            val radioBtn = getRadioBtnView(i)
            if (isSelected) radioBtn.setImageResource(R.drawable.ic_baseline_radio_button_checked_24)
            else radioBtn.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
        }
    }

    protected fun expandOrCollapse(state: MenuState?, containerView: ViewGroup) {
        if (state == null) return

        if (state == MenuState.OPEN) AnimUtil.expandMenu(containerView)
        else AnimUtil.collapseMenu(containerView)
    }
}