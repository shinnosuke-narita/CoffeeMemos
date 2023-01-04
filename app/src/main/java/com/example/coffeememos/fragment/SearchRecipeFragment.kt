package com.example.coffeememos.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.CustomRecipe
import com.example.coffeememos.R
import com.example.coffeememos.adapter.RecipeDetailAdapter
import com.example.coffeememos.databinding.FragmentSearchRecipeBinding
import com.example.coffeememos.databinding.SearchContentsBinding
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.listener.OnFavoriteIconClickListener
import com.example.coffeememos.listener.OnItemClickListener
import com.example.coffeememos.search.RecipeSortType
import com.example.coffeememos.viewModel.MainSearchViewModel
import com.example.coffeememos.viewModel.SearchRecipeViewModel


class SearchRecipeFragment : Fragment() {
    // viewBinding
    private var _mainBinding: FragmentSearchRecipeBinding? = null
    private val mainBinding get() = _mainBinding!!
    // merge tag 使用
    private var _binding: SearchContentsBinding? = null
    private val binding get() = _binding!!


    private val viewModel: SearchRecipeViewModel by viewModels {
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        SearchRecipeViewModel.SearchRecipeViewModelFactory(db.beanDao(), db.recipeDao(), db.tasteDao())
    }

    // 共有viewModel
    private val sharedViewModel: MainSearchViewModel by viewModels({ requireParentFragment() })

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

        viewModel.customRecipeList.observe(viewLifecycleOwner) { list ->
            viewModel.setSearchResult(list)
        }

        // 検索結果 監視処理
        viewModel.searchResult.observe(viewLifecycleOwner) { list ->
            binding.searchResultRV.adapter = setUpAdapter(list)
        }

        // 絞り込み結果 監視処理
        viewModel.filteringResult.observe(viewLifecycleOwner) { list ->
            if (list == null) return@observe

            binding.searchResultRV.adapter = setUpAdapter(list)
        }

        // レシピ数 監視処理
        viewModel.recipeCount.observe(viewLifecycleOwner) { count ->
            binding.itemCount.text = requireContext().getString(R.string.recipeCount, count)
        }

        // 現在のソート 監視処理
        viewModel.currentSortType.observe(viewLifecycleOwner) { sortType ->
            binding.currentSortText.text = sortType.getSortName()
        }

        sharedViewModel.searchKeyWord.observe(viewLifecycleOwner) { keyWord ->
            viewModel.freeWordSearch(keyWord)
        }


        viewModel.isOpened.observe(viewLifecycleOwner) { isOpened ->
            if (isOpened) binding.wholeShadow.visibility = View.VISIBLE
            else binding.wholeShadow.visibility = View.GONE
        }


        // 並び替えボタン クリックリスナ―
        binding.sortBtn.setOnClickListener { view ->
            viewModel.changeBottomSheetState()

            val originData = RecipeSortType.getNameList()
            val currentSortTypeName = viewModel.currentSortType.value!!.getSortName()
            val currentIndex = RecipeSortType.getIndexByName(currentSortTypeName)

            childFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_bottom,R.anim.go_down,R.anim.enter_from_bottom, R.anim.go_down)
                .replace(R.id.bottomSheet, SortFragment.create(currentIndex, originData.toTypedArray()))
                .addToBackStack(null)
                .commit()
        }

        childFragmentManager.setFragmentResultListener("sortResult", viewLifecycleOwner) { _, bundle ->
            viewModel.changeBottomSheetState()

            val selectedIndex: Int = bundle.getInt("selectedIndex", 0)
            val selectedSortType: RecipeSortType = RecipeSortType.getSortTypeByIndex(selectedIndex)

            viewModel.sortSearchResult(selectedSortType)
        }
        // 絞り込みボタン クリックリスナ―
        binding.refineBtn.setOnClickListener { view ->
            viewModel.changeBottomSheetState()

            childFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_bottom,R.anim.go_down,R.anim.enter_from_bottom, R.anim.go_down)
                .replace(R.id.bottomSheet, FilterFragment())
                .addToBackStack(null)
                .commit()
        }
        // 絞り込み画面 リスナー
        childFragmentManager.setFragmentResultListener("filterResult", viewLifecycleOwner) { _, bundle ->
            viewModel.changeBottomSheetState()

            viewModel.filterSearchResult()
        }

        binding.clearBtn.setOnClickListener {
            viewModel.resetResult()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView(context: Context, rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
    }

    private fun setUpAdapter(list: List<CustomRecipe>): RecipeDetailAdapter {
        return RecipeDetailAdapter(requireContext(), list).apply {
            setFavoriteListener(object : OnFavoriteIconClickListener {
                override fun onClick(view: View, id: Long) {
                    viewModel.updateFavoriteIcon(view, id)
                }
            })
            setOnItemClickListener(object : OnItemClickListener<CustomRecipe> {
                override fun onClick(view: View, selectedItem: CustomRecipe) {
                    val showDetailAction = SearchFragmentDirections.showRecipeDetailAction().apply {
                        recipeId = selectedItem.recipeId
                        beanId   = selectedItem.beanId
                        tasteId  = selectedItem.tasteId
                    }

                    Navigation.findNavController(view).navigate(showDetailAction)
                }
            })
        }
    }
}