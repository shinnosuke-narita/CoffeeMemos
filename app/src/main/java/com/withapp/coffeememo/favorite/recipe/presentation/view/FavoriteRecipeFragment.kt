package com.withapp.coffeememo.favorite.recipe.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.withapp.coffeememo.R
import com.withapp.coffeememo.databinding.FragmentFavoriteRecipeBinding
import com.withapp.coffeememo.favorite.common.presentation.view.BaseFavoriteFragmentDirections
import com.withapp.coffeememo.favorite.recipe.presentation.adapter.FavoriteRecipeAdapter
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipeFragment : Fragment() {
    // viewBinding
    private var _binding: FragmentFavoriteRecipeBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: FavoriteRecipeViewModel by viewModels()

    private lateinit var favoriteRecipeAdapter: FavoriteRecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteRecipeBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()

        // お気に入りレシピ一覧
        viewModel.favoriteRecipes.observe(
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
            binding.currentSort.text = sortType.getSortName()
        }
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
            onFavoriteClick = { recipe, view ->
                // 連打防止
                viewModel.disableFavoriteBtn(view)
                // snackbar 表示
                showSnackBar(recipe)
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

    private fun showSnackBar(recipe: FavoriteRecipeModel) {
        Snackbar.make(
            binding.snackBarPlace,
            getString(R.string.favorite_delete_message),
            Snackbar.LENGTH_LONG
        ).apply {
            setAction(
                getString(R.string.favorite_snack_bar_btn_message)
            ) {
                // db 更新
                viewModel.deleteFavoriteRecipe(recipe)
            }
            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.shadow_color
                )
            )
            setActionTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.link_color
                )
            )
            view.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }.show()

    }
}