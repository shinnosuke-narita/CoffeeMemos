package com.withapp.coffeememo.search.bean.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.withapp.coffeememo.R
import com.withapp.coffeememo.databinding.FragmentSearchBeanBinding
import com.withapp.coffeememo.databinding.SearchContentsBinding
import com.withapp.coffeememo.search.common.presentation.fragment.SortFragment
import com.withapp.coffeememo.search.bean.domain.model.BeanSortType
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.search.bean.presentation.adapter.BeanAdapter
import com.withapp.coffeememo.search.bean.presentation.adapter.listener.OnFavoriteClickListener
import com.withapp.coffeememo.search.bean.presentation.adapter.listener.OnItemClickListener
import com.withapp.coffeememo.search.common.presentation.view_model.MainSearchViewModel
import com.withapp.coffeememo.search.bean.presentation.view_model.SearchBeanViewModel
import com.withapp.coffeememo.search.common.presentation.fragment.SearchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBeanFragment : Fragment() {
    // viewBinding
    private var _mainBinding: FragmentSearchBeanBinding? = null
    private val mainBinding get() = _mainBinding!!
    // merge tag 使用
    private var _binding: SearchContentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchBeanViewModel by viewModels()
    // 共有viewModel
    private val sharedViewModel: MainSearchViewModel by viewModels({ requireParentFragment() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initSearchResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mainBinding = FragmentSearchBeanBinding.inflate(inflater, container, false)
        _binding = SearchContentsBinding.bind(mainBinding.root)
        return mainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView(requireContext(), binding.searchResultRV)

        // 検索結果 監視処理
        viewModel.sortedSearchResult.observe(viewLifecycleOwner) { list ->
            binding.searchResultRV.adapter = setUpAdapter(list)
        }

        // 現在のソート 監視処理
        viewModel.currentSortType.observe(viewLifecycleOwner) { type ->
            binding.currentSortText.text = type.getSortName()
        }

        sharedViewModel.searchKeyWord.observe(viewLifecycleOwner) { keyWord ->
            viewModel.freeWordSearch(keyWord)
        }

        // レシピ数 監視処理
        viewModel.beanCount.observe(viewLifecycleOwner) { count ->
            binding.itemCount.text = requireContext().getString(R.string.recipeCount, count)
        }

        // bottomMenu状態監視処理
        viewModel.isOpened.observe(viewLifecycleOwner) { isOpened ->
            if (isOpened) binding.wholeShadow.visibility = View.VISIBLE
            else binding.wholeShadow.visibility = View.GONE
        }

        binding.sortBtn.setOnClickListener {
            viewModel.changeBottomSheetState()

            val originData = BeanSortType.getNameList()
            val currentSortTypeName = viewModel.currentSortType.value!!.getSortName()
            val currentIndex = BeanSortType.getIndexByName(currentSortTypeName)

            childFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_bottom,R.anim.go_down,R.anim.enter_from_bottom, R.anim.go_down)
                .replace(R.id.bottomSheet,
                    SortFragment.create(currentIndex, originData.toTypedArray())
                )
                .addToBackStack(null)
                .commit()
        }

        childFragmentManager.setFragmentResultListener("sortResult", viewLifecycleOwner) { _, bundle ->
            viewModel.changeBottomSheetState()

            val selectedIndex: Int = bundle.getInt("selectedIndex", 0)
            val selectedSortType: BeanSortType = BeanSortType.getSortTypeByIndex(selectedIndex)

            viewModel.setCurrentSortType(selectedSortType)
        }

        binding.refineBtn.setOnClickListener {
            viewModel.changeBottomSheetState()

            childFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_bottom,R.anim.go_down,R.anim.enter_from_bottom, R.anim.go_down)
                .replace(R.id.bottomSheet, BeanFilterFragment())
                .addToBackStack(null)
                .commit()
        }

        childFragmentManager.setFragmentResultListener("filterResult", viewLifecycleOwner) { _, _ ->
            viewModel.changeBottomSheetState()
        }

       binding.clearBtn.setOnClickListener {
           viewModel.resetResult()
       }
    }

    private fun setUpRecyclerView(context: Context, rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
    }

    private fun setUpAdapter(list: List<SearchBeanModel>): BeanAdapter {
        return BeanAdapter(requireContext(), list).apply {
            setFavoriteListener(object : OnFavoriteClickListener {
                override fun onFavoriteClick(
                    view: View,
                    position: Int,
                    bean: SearchBeanModel) {
                    // 連打防止
                    viewModel.disableFavoriteIcon(view)
                    // view更新
                    bean.isFavorite = !(bean.isFavorite)
                    binding.searchResultRV.adapter?.notifyItemChanged(position)
                    // db 更新
                    viewModel.updateFavoriteData(bean)
                }
            })
            setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(bean: SearchBeanModel) {
                    if (viewModel.isOpened.value!!) return

                    val showDetailAction =
                        SearchFragmentDirections.showBeanDetailAction().apply {
                            beanId = bean.id
                        }

                    findNavController().navigate(showDetailAction)
                }
            })
        }
    }
}