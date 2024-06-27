package com.withapp.coffeememo.presentation.search.recipe.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.withapp.coffeememo.R
import com.withapp.coffeememo.databinding.FragmentSearchRecipeBinding
import com.withapp.coffeememo.databinding.SearchContentsBinding
import com.withapp.coffeememo.presentation.search.common.fragment.SortFragment
import com.withapp.coffeememo.domain.model.recipe.RecipeSortType
import com.withapp.coffeememo.domain.model.recipe.SearchRecipeModel
import com.withapp.coffeememo.presentation.search.recipe.adapter.RecipeDetailAdapter
import com.withapp.coffeememo.presentation.search.recipe.adapter.`interface`.OnFavoriteClickListener
import com.withapp.coffeememo.presentation.search.recipe.adapter.`interface`.OnItemClickListener
import com.withapp.coffeememo.presentation.search.common.view_model.MainSearchViewModel
import com.withapp.coffeememo.presentation.search.common.fragment.SearchFragmentDirections
import com.withapp.coffeememo.presentation.search.recipe.view_model.SearchRecipeViewModel
import com.withapp.coffeememo.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchRecipeFragment : Fragment() {
    // viewBinding
    private var _mainBinding: FragmentSearchRecipeBinding? = null
    private val mainBinding get() = _mainBinding!!
    // merge tag 使用
    private var _binding: SearchContentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchRecipeViewModel by viewModels()

    // 共有viewModel
    private val sharedViewModel: MainSearchViewModel by viewModels({ requireParentFragment() })

    // MainViewModel
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initSearchResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mainBinding = FragmentSearchRecipeBinding.inflate(inflater, container, false)
        _binding = SearchContentsBinding.bind(mainBinding.root)
        return mainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // recyclerViewセットアップ
        setUpRecyclerView(requireContext(), binding.searchResultRV)

        // 検索結果 監視処理
        viewModel.sortedSearchResult.observe(viewLifecycleOwner) { list ->
            binding.searchResultRV.adapter = setUpAdapter(list)
        }

        // レシピ数 監視処理
        viewModel.recipeCount.observe(viewLifecycleOwner) { count ->
            binding.itemCount.text = requireContext().getString(R.string.recipeCount, count)
        }

        // 現在のソート 監視処理
        viewModel.currentSortType.observe(viewLifecycleOwner) { sortType ->
            binding.sortBtn.text = getSortTypeStrings()[sortType.index]
        }

        sharedViewModel.searchKeyWord.observe(viewLifecycleOwner) { keyWord ->
            viewModel.freeWordSearch(keyWord)
        }


        viewModel.isOpened.observe(viewLifecycleOwner) { isOpened ->
            if (isOpened) binding.wholeShadow.visibility = View.VISIBLE
            else binding.wholeShadow.visibility = View.GONE
        }


        // 並び替えボタン クリックリスナ―
        binding.sortBtn.setOnClickListener {
            // シャドウ表示
            viewModel.changeBottomSheetState()
            // 広告非表示
            mainViewModel.hideAd()

            val originData: Array<String> =
                requireContext()
                    .resources
                    .getStringArray(R.array.search_recipe_sort_types)

            childFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_from_bottom,
                    R.anim.go_down,
                    R.anim.enter_from_bottom,
                    R.anim.go_down
                )
                .replace(R.id.bottomSheet,
                    SortFragment.create(
                        viewModel.currentSortType.value!!.index,
                        originData
                    )
                )
                .addToBackStack(null)
                .commit()
        }

        childFragmentManager.setFragmentResultListener(
            "sortResult",
            viewLifecycleOwner) { _, bundle ->
            viewModel.changeBottomSheetState()
            mainViewModel.showAd()

            viewModel.setCurrentSortType(
                RecipeSortType.from(
                    bundle.getInt("selectedIndex")
                )
            )
        }

        // 絞り込みボタン クリックリスナ―
        binding.refineBtn.setOnClickListener {
            viewModel.changeBottomSheetState()
            mainViewModel.hideAd()

            childFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_from_bottom,
                    R.anim.go_down,
                    R.anim.enter_from_bottom,
                    R.anim.go_down
                )
                .replace(R.id.bottomSheet, FilterRecipeFragment())
                .addToBackStack(null)
                .commit()
        }
        // 絞り込み画面 リスナー
        childFragmentManager.setFragmentResultListener(
            "filterResult",
            viewLifecycleOwner) { _, _  ->
            viewModel.changeBottomSheetState()
            mainViewModel.showAd()
        }

        binding.clearBtn.setOnClickListener {
            viewModel.resetResult()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateSearchResult()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.deleteFilteringInputData()
    }

    private fun setUpRecyclerView(context: Context, rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
    }

    private fun setUpAdapter(list: List<SearchRecipeModel>): RecipeDetailAdapter {
        return RecipeDetailAdapter(requireContext(), list).apply {
            setFavoriteListener(object : OnFavoriteClickListener {
                override fun onFavoriteClick(
                    view: View,
                    position: Int,
                    recipe: SearchRecipeModel
                ) {
                    // 連打防止
                    viewModel.disableFavoriteIcon(view)

                    // db更新
                    viewModel.updateFavoriteIcon(recipe.recipeId, recipe.isFavorite)

                    // view 更新
                    recipe.isFavorite = !(recipe.isFavorite)
                    binding.searchResultRV.adapter?.notifyItemChanged(position)
                }
            })
            setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(recipe: SearchRecipeModel) {
                    if (viewModel.isOpened.value!!) return

                    // 戻ってきたときにデータ更新
                    viewModel.setShouldUpdate(true)

                    val showDetailAction =
                        SearchFragmentDirections
                            .showRecipeDetailAction().apply {
                                recipeId = recipe.recipeId
                                beanId   = recipe.beanId
                            }
                    findNavController().navigate(showDetailAction)
                }
            })
        }
    }

    private fun getSortTypeStrings(): Array<String> {
        return requireContext()
            .resources
            .getStringArray(R.array.search_recipe_sort_types)

    }
}