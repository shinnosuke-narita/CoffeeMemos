package com.withapp.coffeememo.favorite.recipe.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.withapp.coffeememo.R
import com.withapp.coffeememo.databinding.FavoriteContentsBinding
import com.withapp.coffeememo.databinding.FragmentFavoriteRecipeBinding
import com.withapp.coffeememo.base.dialog.ListDialogFragment
import com.withapp.coffeememo.favorite.common.presentation.view.BaseFavoriteFragmentDirections
import com.withapp.coffeememo.favorite.common.presentation.view.DeleteFavoriteSnackBar
import com.withapp.coffeememo.favorite.recipe.presentation.adapter.FavoriteRecipeAdapter
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipeFragment : Fragment() {
    // viewBinding
    private var _mainBinding: FragmentFavoriteRecipeBinding? = null
    private val mainBinding get() = _mainBinding!!
    // merge tag 使用
    private var _binding: FavoriteContentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteRecipeViewModel by viewModels()

    private lateinit var favoriteRecipeAdapter: FavoriteRecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mainBinding = FragmentFavoriteRecipeBinding
            .inflate(inflater, container, false)
        _binding = FavoriteContentsBinding.bind(mainBinding.root)
        return mainBinding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()

        // お気に入りレシピ一覧
        viewModel.sortedFavoriteRecipes.observe(
            viewLifecycleOwner
        ) { recipes ->
           favoriteRecipeAdapter.submitList(recipes)
        }

        // お気に入りレシピ数
        viewModel.favoriteRecipeCount.observe(
            viewLifecycleOwner) { countStr ->
            binding.itemCount.text = countStr
        }

        // 現在のソート
        viewModel.currentSort.observe(
            viewLifecycleOwner) { sortType ->
            val sortStrings: Array<String> =
                requireContext()
                    .resources
                    .getStringArray(R.array.favorite_recipe_sort_types)

            binding.currentSort.text = sortStrings[sortType.index]
        }

        // sortボタン
        binding.sortBtnWrapper.setOnClickListener {
            // dialogデータ取得
            val currentIndex: Int = viewModel.currentSort.value!!.index
            val sortTypes: Array<String> =
                requireContext()
                    .resources
                    .getStringArray(R.array.favorite_recipe_sort_types)

            ListDialogFragment
                .create(
                    currentIndex,
                    getString(R.string.favorite_sort_dialog_title),
                    "changeSort",
                    sortTypes
                )
                .show(
                    childFragmentManager,
                    ListDialogFragment::class.simpleName
                )
        }

        // SortDialogからの結果を受信
        childFragmentManager.setFragmentResultListener(
            "changeSort",
            viewLifecycleOwner
        ) {_, bundle ->
            viewModel.updateSortType(
                bundle.getInt("newIndex", 0)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initialize()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpAdapter() {
        favoriteRecipeAdapter = getFavoriteRecipeAdapter()

        binding.favoriteRV.adapter = favoriteRecipeAdapter
        binding.favoriteRV.layoutManager =
            LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
    }

    private fun getFavoriteRecipeAdapter(): FavoriteRecipeAdapter {
        return FavoriteRecipeAdapter(
            requireContext(),
            onFavoriteClick = { recipe, view ->
                // 連打防止
                viewModel.disableFavoriteBtn(view)
                // snackbar 表示
                DeleteFavoriteSnackBar<FavoriteRecipeModel>()
                    .show(
                        requireContext(),
                        binding.snackBarPlace,
                        recipe
                    ) { model ->
                        viewModel.deleteFavoriteRecipe(model)
                    }
            },
            onItemClick = { recipe ->
                val showDetailAction =
                    BaseFavoriteFragmentDirections
                        .actionBaseFavoriteFragmentToRecipeDetailFragment()
                        .apply {
                            recipeId = recipe.id
                            beanId   = recipe.beanId
                        }
                findNavController().navigate(showDetailAction)
            }
        )
    }
}