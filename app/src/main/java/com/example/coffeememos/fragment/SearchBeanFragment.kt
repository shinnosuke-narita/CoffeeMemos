package com.example.coffeememos.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentSearchBeanBinding
import com.example.coffeememos.databinding.FragmentSearchRecipeBinding
import com.example.coffeememos.databinding.SearchContentsBinding
import com.example.coffeememos.viewModel.MainSearchViewModel
import com.example.coffeememos.viewModel.SearchBeanViewModel
import com.example.coffeememos.viewModel.SearchRecipeViewModel

class SearchBeanFragment : Fragment() {
    // viewBinding
    private var _mainBinding: FragmentSearchBeanBinding? = null
    private val mainBinding get() = _mainBinding!!
    // merge tag 使用
    private var _binding: SearchContentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchBeanViewModel by viewModels {
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        SearchBeanViewModel.SearchBeanViewModelFactory(db.beanDao())
    }

    // 共有viewModel
    private val sharedViewModel: MainSearchViewModel by viewModels({ requireParentFragment() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mainBinding = FragmentSearchBeanBinding.inflate(inflater, container, false)
        _binding = SearchContentsBinding.bind(mainBinding.root)
        return mainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}