package com.withapp.coffeememo.search.common.presentation.fragment

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.withapp.coffeememo.R
import com.withapp.coffeememo.state.MenuState
import com.withapp.coffeememo.utilities.AnimUtil
import com.withapp.coffeememo.utilities.SystemUtil

open class BaseFilterFragment : Fragment() {

    private fun addFilterElementView(elementTxt: String, filterContainer: ViewGroup, deleteValueProcess: (String) -> Unit) {
        val itemView = layoutInflater.inflate(R.layout.filtered_element_text, null)
        itemView.findViewById<TextView>(R.id.valueText).text = elementTxt
        itemView.findViewById<ImageView>(R.id.deleteBtn).setOnClickListener {
            deleteValueProcess(elementTxt)
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

    private fun setUpEditTextContainer(filterContainer: ViewGroup, dataList: List<String>, deleteValueProcess: (String) -> Unit) {
        if (dataList.isEmpty()) return

        for (text in dataList) {
            addFilterElementView(text, filterContainer, deleteValueProcess)
        }
    }

    protected fun remakeView(container: ViewGroup, valuesList: List<String>, deleteValueProcess: (String) -> Unit) {
        container.removeAllViews()

        if (valuesList.isEmpty())  return

        for (text in valuesList) {
            addFilterElementView(text, container, deleteValueProcess)
        }
    }

    private fun getWrapContentWidth(view: View): Int {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        return view.measuredWidth
    }

    private fun getMatchParentWidth(view: View): Int {
        val parentWidth = (view.parent as View).width

        view.measure(View.MeasureSpec.makeMeasureSpec(parentWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        return view.measuredWidth
    }

    private fun setUpLinearLayout(): ViewGroup {
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

    protected fun updateCurrentFilterElementText(text: String, textView: TextView) {
        if (text.isEmpty()) {
            textView.visibility = View.GONE
            return
        }

        textView.visibility = View.VISIBLE
        textView.text = text
    }

    protected fun setRadioBtnResource(btnStateList: List<Boolean>, getRadioBtnView: (Int) -> ImageView) {
        for ((i, isSelected) in btnStateList.withIndex()) {
            val radioBtn = getRadioBtnView(i)
            if (isSelected) radioBtn.setImageResource(R.drawable.ic_baseline_radio_button_checked_24)
            else radioBtn.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
        }
    }

    protected fun expandOrCollapse(
        state: MenuState?,
        containerView: ViewGroup,
        toggleView: ImageView) {
        if (state == null) return

        if (state == MenuState.OPEN) {
            AnimUtil.expandMenu(containerView)
            toggleView.setImageResource(
                R.drawable.ic_baseline_horizontal_rule_24
            )
        }
        else {
            AnimUtil.collapseMenu(containerView)
            toggleView.setImageResource(
                R.drawable.ic_baseline_add_24
            )
        }
    }

    protected fun expandOrCollapse(
        state: MenuState?,
        parentContainer: ViewGroup,
        elementsContainer: ViewGroup,
        rootView: ViewGroup,
        toggleView: ImageView,
        data: List<String>,
        removeProcess: (element: String) -> Unit
    ) {
        if (state == null) return

        if (state == MenuState.OPEN) {
            setUpEditTextContainer(elementsContainer, data) {
                removeProcess(it)
            }
            AnimUtil.expandMenu(parentContainer)
            toggleView.setImageResource(
                R.drawable.ic_baseline_horizontal_rule_24
            )
        }
        else {
            // キーボード非表示
            SystemUtil.hideKeyBoard(requireContext(), rootView)
            AnimUtil.collapseMenu(parentContainer, elementsContainer)
            toggleView.setImageResource(
                R.drawable.ic_baseline_add_24
            )
        }
    }
}