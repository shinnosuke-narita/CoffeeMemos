package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.CustomRecipe
import com.example.coffeememos.R
import com.example.coffeememos.adapter.RecipeDetailAdapter
import com.example.coffeememos.databinding.FragmentSearchRecipeBinding
import com.example.coffeememos.listener.OnFavoriteIconClickListener
import com.example.coffeememos.listener.OnItemClickListener
import com.example.coffeememos.viewModel.MainSearchViewModel
import com.example.coffeememos.viewModel.SearchRecipeViewModel


class SearchRecipeFragment : Fragment() {
    // viewBinding
    private var _binding: FragmentSearchRecipeBinding? = null
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
        _binding = FragmentSearchRecipeBinding.inflate(inflater, container, false)
        return binding.root
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
            binding.searchResultRV.adapter = RecipeDetailAdapter(requireContext(), list).apply {
                setFavoriteListener(object : OnFavoriteIconClickListener {
                    override fun onClick(view: View, id: Long) {
                        viewModel.updateFavoriteIcon(view, id)
                    }
                })
                setOnItemClickListener(object: OnItemClickListener<CustomRecipe> {
                    override fun onClick(view: View, selectedItem: CustomRecipe) {

                    }
                })
            }
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

            // TODO SortFragment 初期化時の値を変更する！
            childFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_bottom,R.anim.go_down,R.anim.enter_from_bottom, R.anim.go_down)
                .replace(R.id.bottomSheet, SortFragment.create(0))
                .addToBackStack(null)
                .commit()
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
}