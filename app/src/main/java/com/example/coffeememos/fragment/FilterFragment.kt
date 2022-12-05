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
import com.example.coffeememos.utilities.AnimUtil.Companion.collapseMenu
import com.example.coffeememos.utilities.AnimUtil.Companion.expandMenu
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
    private lateinit var sourRadioBtnList: List<ImageView>
    private lateinit var bitterRadioBtnList: List<ImageView>
    private lateinit var sweetRadioBtnList: List<ImageView>
    private lateinit var flavorRadioBtnList: List<ImageView>
    private lateinit var richRadioBtnList: List<ImageView>
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
        binding.sourContainer.root.tag = requireActivity().getString(R.string.sour)
        binding.bitterContainer.root.tag = requireActivity().getString(R.string.bitter)
        binding.sweetContainer.root.tag = requireActivity().getString(R.string.sweet)
        binding.flavorContainer.root.tag = requireActivity().getString(R.string.flavor)
        binding.richContainer.root.tag = requireActivity().getString(R.string.rich)

        ratingRadioBtnList = listOf(
            binding.ratingContainer.radioBtnFirst,
            binding.ratingContainer.radioBtnSecond,
            binding.ratingContainer.radioBtnThird,
            binding.ratingContainer.radioBtnFourth,
            binding.ratingContainer.radioBtnFifth)
        sourRadioBtnList = listOf(
            binding.sourContainer.radioBtnFirst,
            binding.sourContainer.radioBtnSecond,
            binding.sourContainer.radioBtnThird,
            binding.sourContainer.radioBtnFourth,
            binding.sourContainer.radioBtnFifth
        )
        bitterRadioBtnList = listOf(
            binding.bitterContainer.radioBtnFirst,
            binding.bitterContainer.radioBtnSecond,
            binding.bitterContainer.radioBtnThird,
            binding.bitterContainer.radioBtnFourth,
            binding.bitterContainer.radioBtnFifth
        )
        sweetRadioBtnList = listOf(
            binding.sweetContainer.radioBtnFirst,
            binding.sweetContainer.radioBtnSecond,
            binding.sweetContainer.radioBtnThird,
            binding.sweetContainer.radioBtnFourth,
            binding.sweetContainer.radioBtnFifth
        )
        flavorRadioBtnList = listOf(
            binding.flavorContainer.radioBtnFirst,
            binding.flavorContainer.radioBtnSecond,
            binding.flavorContainer.radioBtnThird,
            binding.flavorContainer.radioBtnFourth,
            binding.flavorContainer.radioBtnFifth
        )
        richRadioBtnList = listOf(
            binding.richContainer.radioBtnFirst,
            binding.richContainer.radioBtnSecond,
            binding.richContainer.radioBtnThird,
            binding.richContainer.radioBtnFourth,
            binding.richContainer.radioBtnFifth
        )

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
           setRadioBtnResource(list) { index -> roastViewList[index].findViewById(R.id.radioBtn) }
        }

        viewModel.selectedRoastText.observe(viewLifecycleOwner) { selectedText ->
            updateFilteringView(selectedText, binding.selectedRoast)
        }

        viewModel.roastMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.roastContainer)
        }

        viewModel.grindSizeBtnStateList.observe(viewLifecycleOwner) { list ->
            setRadioBtnResource(list) { index -> grindSizeViewList[index].findViewById<ImageView>(R.id.radioBtn) }
        }

        viewModel.selectedGrindSizeText.observe(viewLifecycleOwner) { selectedText ->
            updateFilteringView(selectedText, binding.selectedGrindSize)
        }

        viewModel.grindSizeMenuState.observe(viewLifecycleOwner) { state ->
           expandOrCollapse(state, binding.grindSizeContainer)
        }
        // 原産地 監視処理
        viewModel.countryMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) {
                setUpEditTextContainer(binding.countryFilterElements, filterManager.countryValues) {
                    filterManager.removeCountryValue(it)
                    viewModel.updateInputCountriesText(filterManager.countryValues)
                }
                expandMenu(binding.countryContainer)
            }
            else {
                // キーボード非表示
                hideKeyBoard()
                collapseMenu(binding.countryContainer, binding.countryFilterElements)
            }
        }
        viewModel.inputCountriesText.observe(viewLifecycleOwner) { text ->
            updateFilteringView(text, binding.inputCountries)
        }

        // 抽出器具 監視処理
        viewModel.toolMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) {
                setUpEditTextContainer(binding.toolFilterElements, filterManager.toolValues) {
                    filterManager.removeToolValue(it)
                    viewModel.updateInputToolsText(filterManager.toolValues)
                }
                expandMenu(binding.toolContainer)
            } else {
                // キーボード非表示
                hideKeyBoard()
                collapseMenu(binding.toolContainer, binding.toolFilterElements)
            }
        }
        viewModel.inputToolsText.observe(viewLifecycleOwner) { text ->
            if (text.isEmpty()) {
                binding.inputTools.visibility = View.GONE
                return@observe
            }

            binding.inputTools.visibility = View.VISIBLE
            binding.inputTools.text = text
        }

        // 評価 監視処理
        viewModel.ratingMenuState.observe(viewLifecycleOwner) { state ->
           expandOrCollapse(state, binding.ratingContainer.root)
        }
        viewModel.ratingRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> ratingRadioBtnList[index] }
        }
        viewModel.selectedRatingText.observe(viewLifecycleOwner) { text ->
            updateFilteringView(text, binding.selectedRating)
        }

        // 酸味 監視処理
        viewModel.sourMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.sourContainer.root)
        }
        viewModel.sourRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> sourRadioBtnList[index] }
        }
        viewModel.selectedSourText.observe(viewLifecycleOwner) { text ->
            updateFilteringView(text, binding.selectedSour)
        }
        // 苦味 監視処理
        viewModel.bitterMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.bitterContainer.root)
        }
        viewModel.bitterRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> bitterRadioBtnList[index] }
        }
        viewModel.selectedBitterText.observe(viewLifecycleOwner) { text ->
            updateFilteringView(text, binding.selectedBitter)
        }
        // 甘味 監視処理
        viewModel.sweetMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.sweetContainer.root)
        }
        viewModel.sweetRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> sweetRadioBtnList[index] }
        }
        viewModel.selectedSweetText.observe(viewLifecycleOwner) { text ->
            updateFilteringView(text, binding.selectedSweet)
        }
        // 香り 監視処理
        viewModel.flavorMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.flavorContainer.root)
        }
        viewModel.flavorRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> flavorRadioBtnList[index] }
        }
        viewModel.selectedFlavorText.observe(viewLifecycleOwner) { text ->
            updateFilteringView(text, binding.selectedFlavor)
        }
        // コク 監視処理
        viewModel.richMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.richContainer.root)
        }
        viewModel.richRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> richRadioBtnList[index] }
        }
        viewModel.selectedRichText.observe(viewLifecycleOwner) { text ->
            updateFilteringView(text, binding.selectedRich)
        }

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

            if (currentState == MenuState.OPEN) viewModel.setRatingMenuState(MenuState.CLOSE)
            else viewModel.setRatingMenuState(MenuState.OPEN)

            viewModel.updateMenuState(binding.ratingContainer.root, requireActivity())
        }
        binding.sourWrapper.setOnClickListener {
            val currentState = viewModel.sourMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setSourState(MenuState.CLOSE)
            else viewModel.setSourState(MenuState.OPEN)

            viewModel.updateMenuState(binding.sourContainer.root, requireActivity())
        }

        binding.bitterWrapper.setOnClickListener {
            val currentState = viewModel.bitterMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setBitterState(MenuState.CLOSE)
            else viewModel.setBitterState(MenuState.OPEN)

            viewModel.updateMenuState(binding.bitterContainer.root, requireActivity())
        }

        binding.sweetWrapper.setOnClickListener {
            val currentState = viewModel.sweetMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setSweetState(MenuState.CLOSE)
            else viewModel.setSweetState(MenuState.OPEN)

            viewModel.updateMenuState(binding.sweetContainer.root, requireActivity())
        }

        binding.flavorWrapper.setOnClickListener {
            val currentState = viewModel.flavorMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setFlavorState(MenuState.CLOSE)
            else viewModel.setFlavorState(MenuState.OPEN)

            viewModel.updateMenuState(binding.flavorContainer.root, requireActivity())
        }

        binding.richWrapper.setOnClickListener {
            val currentState = viewModel.richMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setRichState(MenuState.CLOSE)
            else viewModel.setRichState(MenuState.OPEN)

            viewModel.updateMenuState(binding.richContainer.root, requireActivity())
        }

        // ラジオボタン クリックリスナ―
        for ((i, btn) in ratingRadioBtnList.withIndex()) {
            btn.setOnClickListener {
                viewModel.setRatingRadioBtnState(i)
            }
        }
        for ((i, btn) in sourRadioBtnList.withIndex()) {
            btn.setOnClickListener {
                viewModel.setSourRadioBtnState(i)
            }
        }
        for ((i, btn) in bitterRadioBtnList.withIndex()) {
            btn.setOnClickListener {
                viewModel.setBitterRadioBtnState(i)
            }
        }
        for ((i, btn) in sweetRadioBtnList.withIndex()) {
            btn.setOnClickListener {
                viewModel.setSweetRadioBtnState(i)
            }
        }
        for ((i, btn) in flavorRadioBtnList.withIndex()) {
            btn.setOnClickListener {
                viewModel.setFlavorRadioBtnState(i)
            }
        }
        for ((i, btn) in richRadioBtnList.withIndex()) {
            btn.setOnClickListener {
                viewModel.setRichRadioBtnState(i)
            }
        }

        binding.countryDoneBtn.setOnClickListener {
            val inputText = binding.countryInputText.text.toString()

            addFilterElementView(inputText, binding.countryFilterElements, filterManager.countryValues) {
                filterManager.removeCountryValue(it)
                viewModel.updateInputCountriesText(filterManager.countryValues)
            }

            filterManager.addCountryValue(inputText)
            viewModel.updateInputCountriesText(filterManager.countryValues)
        }

        binding.toolDoneBtn.setOnClickListener {
            val inputText = binding.toolInputText.text.toString()

            addFilterElementView(inputText, binding.toolFilterElements, filterManager.toolValues) {
                filterManager.removeToolValue(it)
                viewModel.updateInputToolsText(filterManager.toolValues)
            }

            // filterManagerのデータ更新
            filterManager.addToolValue(inputText)
            viewModel.updateInputToolsText(filterManager.toolValues)
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

    private fun updateFilteringView(text: String, view: TextView) {
        if (text.isEmpty()) {
            view.visibility = View.GONE
            return
        }

        view.visibility = View.VISIBLE
        view.text = text
    }

    private fun setRadioBtnResource(btnStateList: List<Boolean>, getRadioBtnView: (Int) -> ImageView) {
        for ((i, isSelected) in btnStateList.withIndex()) {
            val radioBtn = getRadioBtnView(i)
            if (isSelected) radioBtn.setImageResource(R.drawable.ic_baseline_radio_button_checked_24)
            else radioBtn.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
        }
    }

    private fun expandOrCollapse(state: MenuState?, containerView: ViewGroup) {
        if (state == null) return

        if (state == MenuState.OPEN)  expandMenu(containerView)
        else collapseMenu(containerView)
    }
}
