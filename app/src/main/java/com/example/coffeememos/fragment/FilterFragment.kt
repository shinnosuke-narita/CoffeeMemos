package com.example.coffeememos.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentFilterBinding
import com.example.coffeememos.search.SearchFilterManager
import com.example.coffeememos.state.MenuState
import com.example.coffeememos.utilities.AnimUtil.Companion.collapseMenu
import com.example.coffeememos.utilities.AnimUtil.Companion.expandMenu
import com.example.coffeememos.utilities.SystemUtil
import com.example.coffeememos.viewModel.FilterViewModel
import com.example.coffeememos.viewModel.SearchRecipeViewModel

class FilterFragment : BaseFilterFragment() {
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
        viewModel.initialize(filterManager)
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
            updateCurrentFilterElementText(selectedText, binding.selectedRoast)
        }

        viewModel.roastMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.roastContainer)
        }

        viewModel.grindSizeBtnStateList.observe(viewLifecycleOwner) { list ->
            setRadioBtnResource(list) { index -> grindSizeViewList[index].findViewById<ImageView>(R.id.radioBtn) }
        }

        viewModel.selectedGrindSizeText.observe(viewLifecycleOwner) { selectedText ->
            updateCurrentFilterElementText(selectedText, binding.selectedGrindSize)
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
                SystemUtil.hideKeyBoard(requireContext(), binding.root)
                collapseMenu(binding.countryContainer, binding.countryFilterElements)
            }
        }
        viewModel.inputCountriesText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.inputCountries)
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
                SystemUtil.hideKeyBoard(requireContext(), binding.root)
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
            updateCurrentFilterElementText(text, binding.selectedRating)
        }

        // 酸味 監視処理
        viewModel.sourMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.sourContainer.root)
        }
        viewModel.sourRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> sourRadioBtnList[index] }
        }
        viewModel.selectedSourText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.selectedSour)
        }
        // 苦味 監視処理
        viewModel.bitterMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.bitterContainer.root)
        }
        viewModel.bitterRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> bitterRadioBtnList[index] }
        }
        viewModel.selectedBitterText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.selectedBitter)
        }
        // 甘味 監視処理
        viewModel.sweetMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.sweetContainer.root)
        }
        viewModel.sweetRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> sweetRadioBtnList[index] }
        }
        viewModel.selectedSweetText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.selectedSweet)
        }
        // 香り 監視処理
        viewModel.flavorMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.flavorContainer.root)
        }
        viewModel.flavorRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> flavorRadioBtnList[index] }
        }
        viewModel.selectedFlavorText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.selectedFlavor)
        }
        // コク 監視処理
        viewModel.richMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.richContainer.root)
        }
        viewModel.richRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> richRadioBtnList[index] }
        }
        viewModel.selectedRichText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.selectedRich)
        }


        // MenuState 更新処理
        binding.roastWrapper.setOnClickListener {
            updateMenuState(viewModel.roastMenuState.value, binding.roastContainer,
                { viewModel.setRoastState(MenuState.OPEN) },
                { viewModel.setRoastState(MenuState.CLOSE) })
        }
        binding.grindSizeWrapper.setOnClickListener {
            updateMenuState(viewModel.grindSizeMenuState.value, binding.grindSizeContainer,
                { viewModel.setGrindSizeState(MenuState.OPEN) },
                { viewModel.setGrindSizeState(MenuState.CLOSE) })
        }
        binding.countryWrapper.setOnClickListener {
            updateMenuState(viewModel.countryMenuState.value, binding.countryContainer,
                { viewModel.setCountryState(MenuState.OPEN) },
                { viewModel.setCountryState(MenuState.CLOSE) })
        }
        binding.toolWrapper.setOnClickListener {
            updateMenuState(viewModel.toolMenuState.value, binding.toolContainer,
                { viewModel.setToolState(MenuState.OPEN) },
                { viewModel.setToolState(MenuState.CLOSE) })
        }
        binding.ratingWrapper.setOnClickListener {
            updateMenuState(viewModel.ratingMenuState.value, binding.ratingContainer.root,
                { viewModel.setRatingMenuState(MenuState.OPEN) },
                { viewModel.setRatingMenuState(MenuState.CLOSE) })
        }
        binding.sourWrapper.setOnClickListener {
            updateMenuState(viewModel.sourMenuState.value, binding.sourContainer.root,
                { viewModel.setSourState(MenuState.OPEN) },
                { viewModel.setSourState(MenuState.CLOSE) })
        }
        binding.bitterWrapper.setOnClickListener {
            updateMenuState(viewModel.bitterMenuState.value, binding.bitterContainer.root,
                { viewModel.setBitterState(MenuState.OPEN) },
                { viewModel.setBitterState(MenuState.CLOSE) })
        }
        binding.sweetWrapper.setOnClickListener {
            updateMenuState(viewModel.sweetMenuState.value, binding.sweetContainer.root,
                { viewModel.setSweetState(MenuState.OPEN) },
                { viewModel.setSweetState(MenuState.CLOSE) })
        }
        binding.flavorWrapper.setOnClickListener {
            updateMenuState(viewModel.flavorMenuState.value, binding.flavorContainer.root,
                { viewModel.setFlavorState(MenuState.OPEN) },
                { viewModel.setFlavorState(MenuState.CLOSE) })
        }
        binding.richWrapper.setOnClickListener {
            updateMenuState(viewModel.richMenuState.value, binding.richContainer.root,
                { viewModel.setRichState(MenuState.OPEN) },
                { viewModel.setRichState(MenuState.CLOSE) })
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
            viewModel.setUpFilterManagerData(filterManager)
            parentViewModel.filterSearchResult()

            parentViewModel.changeBottomSheetState()
            parentFragmentManager.popBackStack()
        }

        // backキー ハンドリング
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            parentViewModel.changeBottomSheetState()
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateMenuState(_currentState: MenuState?, containerView: ViewGroup, setOpenProcess: () -> Unit, setCloseProcess: () -> Unit) {
        val currentState = _currentState ?: MenuState.CLOSE
        if (currentState == MenuState.OPEN) setCloseProcess()
        else setOpenProcess()

        viewModel.updateMenuState(containerView, requireActivity())
    }
}
