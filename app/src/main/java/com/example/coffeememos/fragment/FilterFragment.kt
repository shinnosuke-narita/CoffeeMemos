package com.example.coffeememos.fragment

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentFilterBinding
import com.example.coffeememos.manager.SearchFilterManager
import com.example.coffeememos.state.MenuState
import com.example.coffeememos.viewModel.FilterViewModel
import com.example.coffeememos.viewModel.SearchRecipeViewModel

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: FilterViewModel by viewModels()

    private val parentViewModel: SearchRecipeViewModel by viewModels({ requireParentFragment() })

    private lateinit var filterManager: SearchFilterManager

    private lateinit var ratingRadioBtnList: List<ImageView>
    private val roastViewList: MutableList<View> = mutableListOf()
    private val grindSizeViewList: MutableList<View> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // フィルタリング管理マネージャー
        filterManager = parentViewModel.filterManager

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)

        binding.ratingContainer.root.tag = requireActivity().getString(R.string.review)
        ratingRadioBtnList = listOf(
            binding.ratingContainer.radioBtnFirst,
            binding.ratingContainer.radioBtnSecond,
            binding.ratingContainer.radioBtnThird,
            binding.ratingContainer.radioBtnFourth,
            binding.ratingContainer.radioBtnFifth)

        setUpRadioBtnContainer(Constants.roastList, binding.roastContainer, roastViewList) { index ->
            viewModel.setRoastRadioBtnState(index)
        }

        setUpRadioBtnContainer(Constants.grindSizeList, binding.grindSizeContainer, grindSizeViewList) { index ->
            viewModel.setGrindSizeRadioBtnState(index)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.roastBtnStateList.observe(viewLifecycleOwner) { list ->
            for ((i, isSelected) in list.withIndex()) {
                val radioBtn = roastViewList[i].findViewById<ImageView>(R.id.radioBtn)
                if (isSelected) radioBtn.setImageResource(R.drawable.ic_baseline_radio_button_checked_24)
                else radioBtn.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
            }
        }

        viewModel.selectedRoastText.observe(viewLifecycleOwner) { selectedText ->
            if (selectedText.isEmpty()) {
                binding.selectedRoast.visibility = View.GONE
                return@observe
            }

            binding.selectedRoast.visibility = View.VISIBLE
            binding.selectedRoast.text = selectedText
        }

        viewModel.roastMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN)  expandMenu(binding.roastContainer)
            else collapseMenu(binding.roastContainer)
        }

        viewModel.grindSizeBtnStateList.observe(viewLifecycleOwner) { list ->
            for ((i, isSelected) in list.withIndex()) {
                val radioBtn = grindSizeViewList[i].findViewById<ImageView>(R.id.radioBtn)
                if (isSelected) radioBtn.setImageResource(R.drawable.ic_baseline_radio_button_checked_24)
                else radioBtn.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
            }
        }

        viewModel.selectedGrindSizeText.observe(viewLifecycleOwner) { selectedText ->
            if (selectedText.isEmpty()) {
                binding.selectedGrindSize.visibility = View.GONE
                return@observe
            }

            binding.selectedGrindSize.visibility = View.VISIBLE
            binding.selectedGrindSize.text = selectedText
        }

        viewModel.grindSizeMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN)  expandMenu(binding.grindSizeContainer)
            else collapseMenu(binding.grindSizeContainer)
        }


        viewModel.countryMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) {
                setUpEditTextContainer(binding.countryFilterElements, filterManager.countryValues) {
                    filterManager.removeCountryValue(it)
                }
                expandMenu(binding.countryContainer)
            }
            else {
                // キーボード非表示
                hideKeyBoard()
                collapseMenu(binding.countryContainer, binding.countryFilterElements)
            }
        }
        viewModel.toolMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) {
                setUpEditTextContainer(binding.toolFilterElements, filterManager.toolValues) {
                    filterManager.removeToolValue(it)
                }
                expandMenu(binding.toolContainer)
            } else {
                // キーボード非表示
                hideKeyBoard()
                collapseMenu(binding.toolContainer, binding.toolFilterElements)
            }
        }
        viewModel.ratingMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) {
                expandMenu(binding.ratingContainer.root)
            } else {
                collapseMenu(binding.ratingContainer.root)
            }
        }

        viewModel.ratingRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            for ((i, isClicked) in stateList.withIndex()) {
                if (isClicked) ratingRadioBtnList[i].setImageResource(R.drawable.ic_baseline_radio_button_checked_24)
                else ratingRadioBtnList[i].setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
            }
        }

        viewModel.selectedRatingText.observe(viewLifecycleOwner) { text ->
            if (text.isEmpty()) {
                binding.selectedRatingTxt.visibility = View.GONE
                return@observe
            }

            binding.selectedRatingTxt.visibility = View.VISIBLE
            binding.selectedRatingTxt.text = text
        }

        for ((i, btn) in ratingRadioBtnList.withIndex()) {
            btn.setOnClickListener {
                viewModel.setRatingRadioBtnState(i)
            }
        }
//        viewModel.sourMenuState.observe(viewLifecycleOwner) { state ->
//            if (state == null) return@observe
//
//            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.sourContainer)
//            else collapseMenu(binding.sourContainer)
//        }
//        viewModel.bitterMenuState.observe(viewLifecycleOwner) { state ->
//            if (state == null) return@observe
//
//            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.bitterContainer)
//            else collapseMenu(binding.bitterContainer)
//        }
//        viewModel.sweetMenuState.observe(viewLifecycleOwner) { state ->
//            if (state == null) return@observe
//
//            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.sweetContainer)
//            else collapseMenu(binding.sweetContainer)
//        }
//        viewModel.flavorMenuState.observe(viewLifecycleOwner) { state ->
//            if (state == null) return@observe
//
//            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.flavorContainer)
//            else collapseMenu(binding.flavorContainer)
//        }
//        viewModel.richMenuState.observe(viewLifecycleOwner) { state ->
//            if (state == null) return@observe
//
//            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.richContainer)
//            else collapseMenu(binding.richContainer)
//        }

        binding.roastWrapper.setOnClickListener {
            val currentState = viewModel.roastMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setRoastState(MenuState.CLOSE)
            else viewModel.setRoastState(MenuState.OPEN)

            viewModel.updateMenuState(binding.roastContainer, requireActivity())
        }

        binding.grindSizeWrapper.setOnClickListener {
            val currentState = viewModel.grindSizeMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setGrindSizeState(MenuState.CLOSE)
            else viewModel.setGrindSizeState(MenuState.OPEN)

            viewModel.updateMenuState(binding.grindSizeContainer, requireActivity())
        }


        binding.countryWrapper.setOnClickListener {
            val currentState = viewModel.countryMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setCountryState(MenuState.CLOSE)
            else viewModel.setCountryState(MenuState.OPEN)

            viewModel.updateMenuState(binding.countryContainer, requireActivity())
        }
        binding.toolWrapper.setOnClickListener {
            val currentState = viewModel.toolMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setToolState(MenuState.CLOSE)
            else viewModel.setToolState(MenuState.OPEN)

            viewModel.updateMenuState(binding.toolContainer, requireActivity())
        }
        binding.ratingWrapper.setOnClickListener {
            val currentState = viewModel.ratingMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setRatingState(MenuState.CLOSE)
            else viewModel.setRatingState(MenuState.OPEN)

            viewModel.updateMenuState(binding.ratingContainer.root, requireActivity())
        }
        binding.sourWrapper.setOnClickListener {
            val currentState = viewModel.sourMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setSourState(MenuState.CLOSE)
            else viewModel.setSourState(MenuState.OPEN)

            viewModel.updateMenuState(binding.sourContainer, requireActivity())
        }

        binding.bitterWrapper.setOnClickListener {
            val currentState = viewModel.bitterMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setBitterState(MenuState.CLOSE)
            else viewModel.setBitterState(MenuState.OPEN)

            viewModel.updateMenuState(binding.bitterContainer, requireActivity())
        }

        binding.sweetWrapper.setOnClickListener {
            val currentState = viewModel.sweetMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setSweetState(MenuState.CLOSE)
            else viewModel.setSweetState(MenuState.OPEN)

            viewModel.updateMenuState(binding.sweetContainer, requireActivity())
        }

        binding.flavorWrapper.setOnClickListener {
            val currentState = viewModel.flavorMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setFlavorState(MenuState.CLOSE)
            else viewModel.setFlavorState(MenuState.OPEN)

            viewModel.updateMenuState(binding.flavorContainer, requireActivity())
        }

        binding.richWrapper.setOnClickListener {
            val currentState = viewModel.richMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setRichState(MenuState.CLOSE)
            else viewModel.setRichState(MenuState.OPEN)

            viewModel.updateMenuState(binding.richContainer, requireActivity())
        }

        binding.countryDoneBtn.setOnClickListener {
            val inputText = binding.countryInputText.text.toString()

            addFilterElementView(inputText, binding.countryFilterElements, filterManager.countryValues) {
                filterManager.removeCountryValue(it)
            }

            // filterManagerのデータ更新
            filterManager.addCountryValue(inputText)
        }

        binding.toolDoneBtn.setOnClickListener {
            val inputText = binding.toolInputText.text.toString()

            addFilterElementView(inputText, binding.toolFilterElements, filterManager.toolValues) {
                filterManager.removeToolValue(it)
            }

            // filterManagerのデータ更新
            filterManager.addToolValue(inputText)
        }

        // 閉じる処理
        binding.closeIcon.setOnClickListener {
            //parentViewModel.filterSearchResult()
            parentViewModel.changeBottomSheetState()
            parentFragmentManager.popBackStack()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun expandMenu(containerView: View) {
        // viewの大きさを計測
        containerView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        val containerHeight: Int = containerView.measuredHeight

        containerView.layoutParams.height = 0
        val anim = ValueAnimator.ofInt(0, containerHeight).apply {
            addUpdateListener {
                val updateValue = it.animatedValue as Int
                when (updateValue) {
                    0               -> containerView.visibility = View.VISIBLE
                    containerHeight -> containerView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    else            -> containerView.layoutParams.height = updateValue
                }

                containerView.requestLayout()
            }
            duration = 300
        }
        anim.start()
    }

    private fun collapseMenu(parentView: ViewGroup, filterElementView: ViewGroup) {
        ValueAnimator.ofInt(parentView.height, 0).apply {
            duration = 300
            addUpdateListener {
                if (it.animatedValue == 0) {
                    parentView.visibility = View.GONE
                    filterElementView.removeAllViews()
                }
                parentView.layoutParams.height = it.animatedValue as Int
                parentView.requestLayout()
            }
            start()
        }
    }

    private fun collapseMenu(parentView: ViewGroup) {
        ValueAnimator.ofInt(parentView.height, 0).apply {
            duration = 300
            addUpdateListener {
                if (it.animatedValue == 0) {
                    parentView.visibility = View.GONE
                }
                parentView.layoutParams.height = it.animatedValue as Int
                parentView.requestLayout()
            }
            start()
        }
    }

    private fun addFilterElementView(elementTxt: String, filterContainer: ViewGroup, dataList: List<String>, deleteValueProcess: (String) -> Unit) {
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

    private fun setUpEditTextContainer(filterContainer: ViewGroup, dataList: List<String>, deleteValueProcess: (String) -> Unit) {
        if (dataList.isEmpty()) return

        for (text in dataList) {
            addFilterElementView(text, filterContainer, dataList, deleteValueProcess)
        }
    }

    private fun remakeView(inputText: String, container: ViewGroup, valuesList: List<String>, deleteValueProcess: (String) -> Unit) {
        container.removeAllViews()

        if (valuesList.isEmpty())  return

        for (text in valuesList) {
            addFilterElementView(inputText, container, valuesList, deleteValueProcess)
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

    private fun hideKeyBoard() {
        val inputMService = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMService.hideSoftInputFromWindow(binding.root.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun setUpLinearLayout(): ViewGroup {
        val newLinearLayout = LinearLayout(requireContext())
        newLinearLayout.orientation = LinearLayout.HORIZONTAL
        newLinearLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return newLinearLayout
    }

    private fun setUpRadioBtnContainer(dataList: List<String>, containerView: ViewGroup, viewList: MutableList<View>, clickListenerProcess:(Int) -> Unit ) {
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
}
