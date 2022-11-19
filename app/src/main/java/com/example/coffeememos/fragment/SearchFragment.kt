package com.example.coffeememos.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffeememos.R
import com.example.coffeememos.adapter.SearchViewPagerAdapter
import com.example.coffeememos.databinding.FragmentHomeRecipeBinding
import com.example.coffeememos.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayoutMediator


class SearchFragment : Fragment() {
    // viewBinding
    private  var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!

    private val tabTitles: List<String> = listOf("Recipe", "Bean")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList: List<Fragment> = listOf(SearchRecipeFragment(), SearchBeanFragment())

        binding.searchViewPager.adapter = SearchViewPagerAdapter(this, fragmentList)

        TabLayoutMediator(binding.tabLayout, binding.searchViewPager) {tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}