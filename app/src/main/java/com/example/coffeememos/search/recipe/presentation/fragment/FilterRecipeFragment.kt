package com.example.coffeememos.search.recipe.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentFilterBinding
import com.example.coffeememos.search.common.presentation.fragment.BaseFilterFragment
import com.example.coffeememos.state.MenuState
import com.example.coffeememos.search.recipe.presentation.view_model.RecipeFilterViewModel
import com.example.coffeememos.search.recipe.presentation.view_model.SearchRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterRecipeFragment : BaseFilterFragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: RecipeFilterViewModel by viewModels()
    private val parentViewModel: SearchRecipeViewModel by viewModels({ requireParentFragment() })

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

        // viewModel 初期化
        viewModel.initialize(parentViewModel.searchRecipeController)
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

        // RadioButton 状態監視処理
        viewModel.roastBtnStateList.observe(viewLifecycleOwner) { list ->
           setRadioBtnResource(list) { index -> roastViewList[index].findViewById(R.id.radioBtn) }
        }
        viewModel.grindSizeBtnStateList.observe(viewLifecycleOwner) { list ->
            setRadioBtnResource(list) { index -> grindSizeViewList[index].findViewById<ImageView>(R.id.radioBtn) }
        }
        viewModel.ratingRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> ratingRadioBtnList[index] }
        }
        viewModel.sourRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> sourRadioBtnList[index] }
        }
        viewModel.bitterRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> bitterRadioBtnList[index] }
        }
        viewModel.sweetRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> sweetRadioBtnList[index] }
        }
        viewModel.flavorRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> flavorRadioBtnList[index] }
        }
        viewModel.richRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> richRadioBtnList[index] }
        }


        // Menu開閉状態 監視処理
        viewModel.roastMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.roastContainer)
        }
        viewModel.grindSizeMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.grindSizeContainer)
        }
        viewModel.countryMenuState.observe(viewLifecycleOwner) { state ->
            openOrCollapse(
                state,
                binding.countryContainer,
                binding.countryFilterElements,
                binding.root,
                viewModel.countryValues.value!!,
            ) { viewModel.removeCountryValue(it) }
        }
        viewModel.toolMenuState.observe(viewLifecycleOwner) { state ->
            openOrCollapse(
                state,
                binding.toolContainer,
                binding.toolFilterElements,
                binding.root,
                viewModel.toolValues.value!!,
            ) { viewModel.removeToolValue(it) }
        }
        viewModel.ratingMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.ratingContainer.root)
        }
        viewModel.sourMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.sourContainer.root)
        }
        viewModel.bitterMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.bitterContainer.root)
        }
        viewModel.flavorMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.flavorContainer.root)
        }
        viewModel.sweetMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.sweetContainer.root)
        }
        viewModel.richMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.richContainer.root)
        }

        // 絞り込みデータ監視処理
        viewModel.countryValues.observe(viewLifecycleOwner) { list ->
            if (viewModel.countryMenuState.value == MenuState.CLOSE || viewModel.countryMenuState.value == null) return@observe

            remakeView(binding.countryFilterElements, list) {
                viewModel.removeCountryValue(it)
            }
        }
        viewModel.toolValues.observe(viewLifecycleOwner) { list ->
            if (viewModel.toolMenuState.value == MenuState.CLOSE || viewModel.toolMenuState.value == null) return@observe

            remakeView(binding.toolFilterElements, list) {
                viewModel.removeToolValue(it)
            }
        }

        // 絞り込み要素表示テキスト 監視処理
        viewModel.selectedRoastText.observe(viewLifecycleOwner) { selectedText ->
            updateCurrentFilterElementText(selectedText, binding.selectedRoast)
        }
        viewModel.selectedGrindSizeText.observe(viewLifecycleOwner) { selectedText ->
            updateCurrentFilterElementText(selectedText, binding.selectedGrindSize)
        }
        viewModel.inputCountriesText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.inputCountries)
        }
        viewModel.inputToolsText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.inputTools)
        }
        viewModel.selectedRatingText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.selectedRating)
        }
        viewModel.selectedSourText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.selectedSour)
        }
        viewModel.selectedBitterText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.selectedBitter)
        }
        viewModel.selectedSweetText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.selectedSweet)
        }
        viewModel.selectedFlavorText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.selectedFlavor)
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

        // 絞り込み要素の入力完了ボタン クリックリスナー
        binding.countryDoneBtn.setOnClickListener {
            val inputText = binding.countryInputText.text.toString()
            viewModel.addCountryValue(inputText)
        }

        binding.toolDoneBtn.setOnClickListener {
            val inputText = binding.toolInputText.text.toString()
            viewModel.addToolValue(inputText)
        }

        // 閉じる処理
        binding.closeIcon.setOnClickListener {
            parentViewModel.filterSearchResult(
                viewModel.roastBtnStateList.value!!,
                viewModel.grindSizeBtnStateList.value!!,
                viewModel.ratingRadioBtnState.value!!,
                viewModel.sourRadioBtnState.value!!,
                viewModel.bitterRadioBtnState.value!!,
                viewModel.sweetRadioBtnState.value!!,
                viewModel.flavorRadioBtnState.value!!,
                viewModel.richRadioBtnState.value!!,
                viewModel.countryValues.value!!,
                viewModel.toolValues.value!!,
            )

            setFragmentResult("filterResult", Bundle())
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
