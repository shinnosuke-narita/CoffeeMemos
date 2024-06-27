package com.withapp.coffeememo.presentation.search.common.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.withapp.coffeememo.presentation.search.recipe.model.SearchKeyWord
import com.withapp.coffeememo.presentation.search.recipe.model.SearchType
import com.withapp.coffeememo.presentation.search.common.adapter.SearchViewPagerAdapter
import com.withapp.coffeememo.databinding.FragmentSearchBinding
import com.withapp.coffeememo.presentation.search.recipe.fragment.SearchRecipeFragment
import com.withapp.coffeememo.search.bean.presentation.fragment.SearchBeanFragment
import com.withapp.coffeememo.presentation.search.common.view_model.MainSearchViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.withapp.coffeememo.R
import com.withapp.coffeememo.utilities.SnackBarUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    // viewBinding
    private  var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: MainSearchViewModel by viewModels()

    private val tabTitles: List<String> = listOf("Recipe", "Bean")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        binding.searchIcon.setOnClickListener {
            if (binding.searchViewPager.currentItem == 0) {
                viewModel.setSearchKeyWord(
                    SearchKeyWord(binding.searchInput.text.toString(), SearchType.RECIPE)
                )
            } else {
                viewModel.setSearchKeyWord(
                    SearchKeyWord(binding.searchInput.text.toString(), SearchType.BEAN)
                )
            }
        }

        binding.backBtn.setOnClickListener{
            findNavController().popBackStack()
        }

        // コーヒー豆削除処理のリスナー
        setFragmentResultListener("deleteBean") { _, _ ->
            SnackBarUtil.showFinishDeleteSnackBar(
                requireContext(),
                binding.snackBarPlace,
                getString(R.string.bean_finish_delete_message),
            )
        }
        // レシピ削除処理のリスナー
        setFragmentResultListener("deleteRecipe") { _, _ ->
            SnackBarUtil.showFinishDeleteSnackBar(
                requireContext(),
                binding.snackBarPlace,
                getString(R.string.recipe_finish_delete_message),
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}