package com.example.coffeememos.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentBeanFilterBinding
import com.example.coffeememos.databinding.FragmentFilterBinding
import com.example.coffeememos.state.MenuState
import com.example.coffeememos.viewModel.BeanFilterViewModel
import com.example.coffeememos.viewModel.FilterViewModel
import com.example.coffeememos.viewModel.SearchBeanViewModel
import com.example.coffeememos.viewModel.SearchRecipeViewModel

class BeanFilterFragment : BaseFilterFragment() {
    private var _binding: FragmentBeanFilterBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: BeanFilterViewModel by viewModels()
    private val parentViewModel: SearchBeanViewModel by viewModels({ requireParentFragment() })

    private val processViewList: MutableList<View> = mutableListOf()


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
            expandOrCollapse(state, binding.countryContainer.root)
        }

        viewModel.farmMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.farmContainer.root)
        }

        viewModel.districtMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.districtContainer.root)
        }

        viewModel.storeMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.storeContainer.root)
        }

        viewModel.speciesMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.speciesContainer.root)
        }

        viewModel.ratingMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.ratingContainer.root)
        }

        viewModel.processMenuState.observe(viewLifecycleOwner) { state ->
            expandOrCollapse(state, binding.processContainer)
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
        setUpRadioBtnContainer(Constants.processList, binding.processContainer, processViewList) { index ->
            viewModel.setProcessBtnState(index)
        }
    }

    private fun updateMenuState(_currentState: MenuState?, containerView: ViewGroup, setOpenProcess: () -> Unit, setCloseProcess: () -> Unit) {
        val currentState = _currentState ?: MenuState.CLOSE
        if (currentState == MenuState.OPEN) setCloseProcess()
        else setOpenProcess()

        viewModel.updateMenuState(containerView, requireActivity())
    }
}