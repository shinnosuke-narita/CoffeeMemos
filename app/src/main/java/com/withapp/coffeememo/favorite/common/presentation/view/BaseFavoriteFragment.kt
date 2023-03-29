package com.withapp.coffeememo.favorite.common.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.withapp.coffeememo.R
import com.withapp.coffeememo.databinding.FragmentBaseFavoriteBinding
import com.withapp.coffeememo.favorite.bean.presentation.view.FavoriteBeanFragment
import com.withapp.coffeememo.favorite.common.presentation.adapter.FavoriteViewPagerAdapter
import com.withapp.coffeememo.favorite.recipe.presentation.view.FavoriteRecipeFragment
import com.withapp.coffeememo.utilities.SnackBarUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseFavoriteFragment : Fragment() {
    // viewBinding
    private  var _binding: FragmentBaseFavoriteBinding? = null
    private val binding
        get() = _binding!!

    private val tabTitles: List<String> = listOf("Recipe", "Bean")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentBaseFavoriteBinding.inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setUpHeader()
        setUpViewPager()

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

    // header セッティング
    private fun setUpHeader() {
        binding.header.headerTitle.text =
            getString(R.string.favorite_header)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpViewPager() {
        val fragmentList: List<Fragment> =
            listOf(FavoriteRecipeFragment(), FavoriteBeanFragment())

        binding.favoriteViewPager.adapter =
            FavoriteViewPagerAdapter(this, fragmentList)

        TabLayoutMediator(
            binding.tabLayout,
            binding.favoriteViewPager
        ) {tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}