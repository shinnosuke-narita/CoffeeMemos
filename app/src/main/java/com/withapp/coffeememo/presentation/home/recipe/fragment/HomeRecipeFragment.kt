package com.withapp.coffeememo.presentation.home.recipe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.withapp.coffeememo.R
import com.withapp.coffeememo.databinding.FragmentHomeRecipeBinding
import com.withapp.coffeememo.presentation.components.ThemeComposable
import com.withapp.coffeememo.presentation.home.recipe.compose.HomeRecipeScreen
import com.withapp.coffeememo.presentation.home.recipe.view_model.HomeRecipeViewModel
import com.withapp.coffeememo.presentation.utilities.SnackBarUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeRecipeFragment : Fragment() {
    // viewBinding
    private  var _binding: FragmentHomeRecipeBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: HomeRecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeRecipeBinding
            .inflate(inflater, container, false)
            .apply {
                composeView.setContent {
                    ThemeComposable {
                        HomeRecipeScreen(
                            onCreateButton = {
                                findNavController().navigate(R.id.newRecipeFragment)
                            },
                            onClickHomeBeanFAB = {
                                findNavController().navigate(R.id.homeBeansFragment)
                            },
                            onCardClick = { recipe ->
                                val showDetailAction =
                                    HomeRecipeFragmentDirections.showRecipeDetailAction()
                                        .apply {
                                            recipeId = recipe.recipeId
                                            beanId   = recipe.beanId
                                        }
                                findNavController().navigate(showDetailAction)
                            },
                            onFavoriteClick = { recipe ->
                                viewModel.updateHomeData( recipe.recipeId, recipe.isFavorite)
                            }
                        )
                    }
                }
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // メニュー画面へ遷移
        binding.menuBtn.setOnClickListener {
            findNavController()
                .navigate(R.id.action_homeRecipeFragment_to_menuFragment)
        }

        setFragmentResultListener("deleteRecipe") { _, _ ->
            SnackBarUtil.showFinishDeleteSnackBar(
                requireContext(),
                binding.snackBarPlace,
                getString(R.string.recipe_finish_delete_message)
            )
        }

        setFragmentResultListener("createRecipe") {_, _ ->
            SnackBarUtil.showSimpleSnackBar(
                requireContext(),
                binding.snackBarPlace,
                getString(R.string.recipe_finish_save_message)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        // recipeデータ取得
        viewModel.getHomeRecipeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}