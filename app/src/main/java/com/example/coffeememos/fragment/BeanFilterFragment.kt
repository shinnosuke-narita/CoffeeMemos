package com.example.coffeememos.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentBeanFilterBinding
import com.example.coffeememos.databinding.FragmentFilterBinding
import com.example.coffeememos.viewModel.BeanFilterViewModel
import com.example.coffeememos.viewModel.FilterViewModel
import com.example.coffeememos.viewModel.SearchBeanViewModel
import com.example.coffeememos.viewModel.SearchRecipeViewModel

class BeanFilterFragment : Fragment() {
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

        val tag = binding.ratingContainer.root.tag

        setUpView()
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





    }
}