package com.withapp.coffeememo.presentation.home.recipe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.withapp.coffeememo.R
import com.withapp.coffeememo.databinding.FragmentHomeRecipeBinding
import com.withapp.coffeememo.presentation.home.recipe.adapter.RecipeAdapter
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

    // RecyclerView adapter
    private lateinit var _newRecipeAdapter: RecipeAdapter
    private lateinit var _highRatingAdapter: RecipeAdapter
    private lateinit var _favoriteRecipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeRecipeBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView セットアップ
        setUpRecyclerView()

        // 今日作成レシピ数
        viewModel.todayRecipeCount.observe(
            viewLifecycleOwner) { todayRecipeNum ->
            binding.todayRecipeNum.text = todayRecipeNum.toString()
        }

        // お気に入りレシピ数
        viewModel.favoriteRecipeCount.observe(
            viewLifecycleOwner) { favoriteRecipeNum ->
            binding.favoriteRecipeNum.text = favoriteRecipeNum
        }

        // 監視処理
        viewModel.newRecipes.observe(
            viewLifecycleOwner) { list ->
            _newRecipeAdapter.submitList(list)
        }

        viewModel.favoriteRecipes.observe(
            viewLifecycleOwner) { favoriteList ->
            _favoriteRecipeAdapter.submitList(favoriteList)
        }

        viewModel.highRatingRecipes.observe(
            viewLifecycleOwner) { highRatingList ->
            _highRatingAdapter.submitList(highRatingList)
        }


        // レシピの登録数 監視処理
        viewModel.totalRecipeCount.observe(
            viewLifecycleOwner) { recipeNum ->
            binding.recipeTotalNum.text = recipeNum.toString()

            if (recipeNum == 0) {
                binding.newRecipeHeader.visibility = View.GONE
                binding.highRatingRecipeHeader.visibility = View.GONE
                binding.favoriteRecipeHeader.visibility = View.GONE
            } else {
                binding.newRecipeHeader.visibility = View.VISIBLE
                binding.highRatingRecipeHeader.visibility = View.VISIBLE
                binding.favoriteRecipeHeader.visibility = View.VISIBLE
            }
        }

        // レシピ新規作成画面へ遷移
        binding.createRecipeBtn.setOnClickListener {
            findNavController().navigate(R.id.newRecipeFragment)
        }

        // 豆ホーム画面へ遷移
        binding.goToBeanBtn.setOnClickListener {
            findNavController().navigate(R.id.homeBeansFragment)
        }

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

    private fun setUpRecyclerView() {
        _newRecipeAdapter = getHomeRecipeAdapter()
        _favoriteRecipeAdapter = getHomeRecipeAdapter()
        _highRatingAdapter = getHomeRecipeAdapter()

        binding.newRecipeList.apply {
            adapter = _newRecipeAdapter
            layoutManager = getLinerLayoutManger()
        }
        binding.favoriteRecipeList.apply {
            adapter = _favoriteRecipeAdapter
            layoutManager = getLinerLayoutManger()
        }
        binding.highRatingRecipeList.apply {
            adapter = _highRatingAdapter
            layoutManager = getLinerLayoutManger()
        }
    }

    private fun getHomeRecipeAdapter(): RecipeAdapter {
       return RecipeAdapter(
           context = requireContext(),
           onFavoriteClick = { recipe ->
               viewModel.updateHomeData(
                   recipe.recipeId,
                   recipe.isFavorite)
           },
           onItemClick = { recipe ->
               val showDetailAction =
                   HomeRecipeFragmentDirections.showRecipeDetailAction()
                       .apply {
                           recipeId = recipe.recipeId
                           beanId   = recipe.beanId
                       }
               findNavController().navigate(showDetailAction)
           }
       )
    }

    private fun getLinerLayoutManger(): LinearLayoutManager {
       return LinearLayoutManager(
                requireContext(),
                RecyclerView.HORIZONTAL,
                false
            )
    }

}