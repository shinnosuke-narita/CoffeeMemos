package com.withapp.coffeememo.search.bean.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.withapp.coffeememo.R
import com.withapp.coffeememo.core.ad_mob.locale.LocalizationManager
import com.withapp.coffeememo.databinding.FragmentBeanFilterBinding
import com.withapp.coffeememo.search.common.presentation.fragment.BaseFilterFragment
import com.withapp.coffeememo.state.MenuState
import com.withapp.coffeememo.search.bean.presentation.view_model.BeanFilterViewModel
import com.withapp.coffeememo.search.bean.presentation.view_model.SearchBeanViewModel

class BeanFilterFragment : BaseFilterFragment() {
    private var _binding: FragmentBeanFilterBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: BeanFilterViewModel by viewModels()
    private val parentViewModel: SearchBeanViewModel by viewModels({ requireParentFragment() })

    private val processViewList: MutableList<View> = mutableListOf()
    private lateinit var ratingRadioBtnList: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // viewModel 初期化
        viewModel.initialize(parentViewModel.getBeanOutputDataUseCase)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBeanFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // view セットアップ
        setUpView()

        viewModel.countryMenuState.observe(viewLifecycleOwner) { state ->
           expandOrCollapse(
               state,
               binding.countryContainer.root,
               binding.countryContainer.inputtedFilterElements,
               binding.root,
               binding.countryTitleWrapper.icon,
               viewModel.countryValues.value!!
           ) { viewModel.removeCountryValue(it) }
        }

        viewModel.countryValues.observe(viewLifecycleOwner) { list ->
            if (viewModel.countryMenuState.value == MenuState.CLOSE || viewModel.countryMenuState.value == null) return@observe

            remakeView(binding.countryContainer.inputtedFilterElements, list) {
                viewModel.removeCountryValue(it)
            }
        }

        viewModel.inputCountriesText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.countryTitleWrapper.selectedItem)
        }

        viewModel.farmMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(
                state,
                binding.farmContainer.root,
                binding.farmContainer.inputtedFilterElements,
                binding.root,
                binding.farmTitleWrapper.icon,
                viewModel.farmValues.value!!
           ) { viewModel.removeFarmValue(it) }
        }
        viewModel.farmValues.observe(viewLifecycleOwner) { list ->
            if (viewModel.farmMenuState.value == MenuState.CLOSE || viewModel.farmMenuState.value == null) return@observe

            remakeView(binding.farmContainer.inputtedFilterElements, list) {
                viewModel.removeFarmValue(it)
            }
        }

        viewModel.inputFarmText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.farmTitleWrapper.selectedItem)
        }

        viewModel.districtMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(
               state,
               binding.districtContainer.root,
               binding.districtContainer.inputtedFilterElements,
                binding.root,
                binding.districtTitleWrapper.icon,
               viewModel.districtValues.value!!
           ) { viewModel.removeDistrictValue(it) }
        }

        viewModel.districtValues.observe(viewLifecycleOwner) { list ->
            if (viewModel.districtMenuState.value == MenuState.CLOSE || viewModel.districtMenuState.value == null) return@observe

            remakeView(binding.districtContainer.inputtedFilterElements, list) {
                viewModel.removeDistrictValue(it)
            }
        }

        viewModel.inputDistrictText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.districtTitleWrapper.selectedItem)
        }

        viewModel.storeMenuState.observe(viewLifecycleOwner) { state ->
           expandOrCollapse(
               state,
               binding.storeContainer.root,
               binding.storeContainer.inputtedFilterElements,
               binding.root,
               binding.storeTitleWrapper.icon,
               viewModel.storeValues.value!!
           ) { viewModel.removeStoreValue(it) }
        }


        viewModel.storeValues.observe(viewLifecycleOwner) { list ->
            if (viewModel.storeMenuState.value == MenuState.CLOSE || viewModel.storeMenuState.value == null) return@observe

            remakeView(binding.storeContainer.inputtedFilterElements, list) {
                viewModel.removeStoreValue(it)
            }
        }

        viewModel.inputStoreText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.storeTitleWrapper.selectedItem)
        }

        viewModel.speciesMenuState.observe(viewLifecycleOwner) { state ->
           expandOrCollapse(
               state,
               binding.speciesContainer.root,
               binding.speciesContainer.inputtedFilterElements,
               binding.root,
               binding.speciesTitleWrapper.icon,
               viewModel.speciesValues.value!!
           ) { viewModel.removeSpeciesValue(it) }
        }

        viewModel.speciesValues.observe(viewLifecycleOwner) { list ->
            if (viewModel.speciesMenuState.value == MenuState.CLOSE || viewModel.speciesMenuState.value == null) return@observe

            remakeView(binding.speciesContainer.inputtedFilterElements, list) {
                viewModel.removeSpeciesValue(it)
            }
        }

        viewModel.inputSpeciesText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.speciesTitleWrapper.selectedItem)
        }

        viewModel.ratingMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(
                state,
                binding.ratingContainer.root,
                binding.ratingTitleWrapper.icon
            )
        }
        viewModel.ratingRadioBtnState.observe(viewLifecycleOwner) { stateList ->
            setRadioBtnResource(stateList) { index -> ratingRadioBtnList[index] }
        }
        viewModel.selectedRatingText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.ratingTitleWrapper.selectedItem)
        }

        viewModel.processMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(
                state,
                binding.processContainer,
                binding.processTitleWrapper.icon
            )
        }

        viewModel.processBtnStateList.observe(viewLifecycleOwner) { list ->
            setRadioBtnResource(list) { index -> processViewList[index].findViewById(R.id.radioBtn) }
        }

        viewModel.selectedProcessText.observe(viewLifecycleOwner) { text ->
            updateCurrentFilterElementText(text, binding.processTitleWrapper.selectedItem)
        }

        binding.countryTitleWrapper.root.setOnClickListener {
            updateMenuState(viewModel.countryMenuState.value, binding.countryContainer.root,
                { viewModel.setCountryMenuState(MenuState.OPEN)},
                { viewModel.setCountryMenuState(MenuState.CLOSE)}
            )
        }

        binding.farmTitleWrapper.root.setOnClickListener {
            updateMenuState(viewModel.farmMenuState.value, binding.farmContainer.root,
                { viewModel.setFarmMenuState(MenuState.OPEN)},
                { viewModel.setFarmMenuState(MenuState.CLOSE)}
            )
        }

        binding.districtTitleWrapper.root.setOnClickListener {
            updateMenuState(viewModel.districtMenuState.value, binding.districtContainer.root,
                { viewModel.setDistrictMenuState(MenuState.OPEN)},
                { viewModel.setDistrictMenuState(MenuState.CLOSE)}
            )
        }

        binding.storeTitleWrapper.root.setOnClickListener {
            updateMenuState(viewModel.storeMenuState.value, binding.storeContainer.root,
                { viewModel.setStoreMenuState(MenuState.OPEN)},
                { viewModel.setStoreMenuState(MenuState.CLOSE)}
            )
        }

        binding.speciesTitleWrapper.root.setOnClickListener {
            updateMenuState(viewModel.speciesMenuState.value, binding.speciesContainer.root,
                { viewModel.setSpeciesMenuState(MenuState.OPEN)},
                { viewModel.setSpeciesMenuState(MenuState.CLOSE)}
            )
        }

        binding.processTitleWrapper.root.setOnClickListener {
            updateMenuState(viewModel.processMenuState.value, binding.processContainer,
                { viewModel.setProcessMenuState(MenuState.OPEN)},
                { viewModel.setProcessMenuState(MenuState.CLOSE)}
            )
        }

        binding.ratingTitleWrapper.root.setOnClickListener {
            updateMenuState(viewModel.ratingMenuState.value, binding.ratingContainer.root,
                { viewModel.setRatingMenuState(MenuState.OPEN)},
                { viewModel.setRatingMenuState(MenuState.CLOSE)}
            )
        }

        binding.countryContainer.doneBtn.setOnClickListener {
            val inputText = binding.countryContainer.inputText.text.toString()
            viewModel.addCountryValue(inputText)
        }
        binding.farmContainer.doneBtn.setOnClickListener {
            val inputText = binding.farmContainer.inputText.text.toString()
            viewModel.addFarmValue(inputText)
        }
        binding.districtContainer.doneBtn.setOnClickListener {
            val inputText = binding.districtContainer.inputText.text.toString()
            viewModel.addDistrictValue(inputText)
        }
        binding.storeContainer.doneBtn.setOnClickListener {
            val inputText = binding.storeContainer.inputText.text.toString()
            viewModel.addStoreValue(inputText)
        }
        binding.speciesContainer.doneBtn.setOnClickListener {
            val inputText = binding.speciesContainer.inputText.text.toString()
            viewModel.addSpeciesValue(inputText)
        }
        binding.farmContainer.doneBtn.setOnClickListener {
            val inputText = binding.farmContainer.inputText.text.toString()
            viewModel.addFarmValue(inputText)
        }
        // 評価のラジオボタンのクリックリスナ―
        for ((i, btn) in ratingRadioBtnList.withIndex()) {
            btn.setOnClickListener {
                viewModel.setRatingRadioBtnState(i)
            }
        }

        binding.closeIcon.setOnClickListener {
            parentViewModel.filterSearchResult(
                viewModel.countryValues.value!!,
                viewModel.farmValues.value!!,
                viewModel.districtValues.value!!,
                viewModel.storeValues.value!!,
                viewModel.speciesValues.value!!,
                viewModel.ratingRadioBtnState.value!!,
                viewModel.processBtnStateList.value!!
            )

           setFragmentResult("filterResult", Bundle())
            parentFragmentManager.popBackStack()
        }
    }

    private fun setUpView() {
        // title セット
        binding.countryTitleWrapper.title.text = getString(R.string.country)
        binding.farmTitleWrapper.title.text = getString(R.string.farm)
        binding.districtTitleWrapper.title.text = getString(R.string.district)
        binding.storeTitleWrapper.title.text = getString(R.string.store)
        binding.speciesTitleWrapper.title.text = getString(R.string.species)
        binding.ratingTitleWrapper.title.text = getString(R.string.review)
        binding.processTitleWrapper.title.text = getString(R.string.process)

        // tag セット
        binding.countryContainer.root.tag = getString(R.string.country)
        binding.farmContainer.root.tag = getString(R.string.farm)
        binding.districtContainer.root.tag = getString(R.string.district)
        binding.storeContainer.root.tag = getString(R.string.store)
        binding.speciesContainer.root.tag = getString(R.string.species)
        binding.ratingContainer.root.tag = getString(R.string.review)
        binding.processContainer.tag = getString(R.string.process)

        // コーヒー精製法コンテナ セットアップ
        val processList: List<String> =
            LocalizationManager.getProcessList()
        setUpRadioBtnContainer(
            processList,
            binding.processContainer,
            processViewList) { index ->
            viewModel.setProcessBtnState(index)
        }

       // 評価のRadioButton 保持
        ratingRadioBtnList = listOf(
            binding.ratingContainer.radioBtnFirst,
            binding.ratingContainer.radioBtnSecond,
            binding.ratingContainer.radioBtnThird,
            binding.ratingContainer.radioBtnFourth,
            binding.ratingContainer.radioBtnFifth)
    }

    private fun updateMenuState(_currentState: MenuState?, containerView: ViewGroup, setOpenProcess: () -> Unit, setCloseProcess: () -> Unit) {
        val currentState = _currentState ?: MenuState.CLOSE
        if (currentState == MenuState.OPEN) setCloseProcess()
        else setOpenProcess()

        viewModel.updateMenuState(containerView, requireActivity())
    }
}