package com.example.coffeememos.fragment

import android.os.Bundle
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBeanFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }

    private fun setUpView() {
        binding.countryTitleWrapper.title.text = getString(R.string.country)
        binding.farmTitleWrapper.title.text = getString(R.string.farm)
        binding.districtTitleWrapper.title.text = getString(R.string.district)
        binding.storeTitleWrapper.title.text = getString(R.string.store)
        binding.speciesTitleWrapper.title.text = getString(R.string.species)
        binding.ratingTitleWrapper.title.text = getString(R.string.review)
        binding.processTitleWrapper.title.text = getString(R.string.process)
    }
}