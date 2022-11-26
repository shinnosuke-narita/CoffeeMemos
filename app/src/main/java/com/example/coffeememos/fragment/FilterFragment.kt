package com.example.coffeememos.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentFilterBinding
import com.example.coffeememos.databinding.FragmentSortBinding
import com.example.coffeememos.viewModel.FilterViewModel
import com.example.coffeememos.viewModel.SortViewModel

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: FilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 隠れたメニューの高さ取得
        binding.countryContainer.root.post {
            viewModel.inputTextContainerHeight = binding.countryContainer.root.height
        }
        // 隠れたメニューの高さ取得
        binding.ratingContainer.root.post {
            viewModel.radioGroupContainerHeight = binding.ratingContainer.root.height
        }


        binding.countryTitle.




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}