package com.withapp.coffeememo.presentation.favorite.bean.view

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
import com.withapp.coffeememo.databinding.FragmentFavoriteBeanBinding
import com.withapp.coffeememo.base.dialog.ListDialogFragment
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel
import com.withapp.coffeememo.presentation.favorite.bean.adapter.FavoriteBeanAdapter
import com.withapp.coffeememo.presentation.favorite.common.view.BaseFavoriteFragmentDirections
import com.withapp.coffeememo.presentation.favorite.common.view.DeleteFavoriteSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteBeanFragment : Fragment() {
    // viewBinding
    private var _mainBinding: FragmentFavoriteBeanBinding? = null
    private val mainBinding get() = _mainBinding!!
    // merge tag 使用
    private var _binding: FavoriteContentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteBeanViewModel by viewModels()

    private lateinit var favoriteBeanAdapter: FavoriteBeanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mainBinding = FragmentFavoriteBeanBinding
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

        viewModel.sortedFavoriteBeans.observe(viewLifecycleOwner) { beans ->
            favoriteBeanAdapter.submitList(beans)
        }

        // お気に入りコーヒー豆数
        viewModel.favoriteBeanCount.observe(
            viewLifecycleOwner) { countStr ->
            binding.itemCount.text = countStr
        }

        // 現在のソート
        viewModel.currentSort.observe(
            viewLifecycleOwner) { sortType ->
            binding.currentSort.text = getSortTypeStrings()[sortType.index]
        }

        // ソートボタン
        binding.sortBtnWrapper.setOnClickListener {
            ListDialogFragment
                .create(
                    viewModel.currentSort.value!!.index,
                    getString(R.string.favorite_sort_dialog_title),
                    "changeSort",
                    getSortTypeStrings()
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
            viewModel.updateCurrentSort(
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
        favoriteBeanAdapter = getFavoriteBeanAdapter()

        binding.favoriteRV.adapter = favoriteBeanAdapter
        binding.favoriteRV.layoutManager =
            LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
    }

    private fun getFavoriteBeanAdapter(): FavoriteBeanAdapter {
        return FavoriteBeanAdapter(
            requireContext(),
            onFavoriteClick = { bean, view ->
                // 連打防止
                viewModel.disableFavoriteBtn(view)
                // snackbar 表示
                DeleteFavoriteSnackBar<FavoriteBeanModel>()
                    .show(
                        requireContext(),
                        binding.snackBarPlace,
                        bean
                    ) { model ->
                        viewModel.deleteFavoriteBean(model)
                    }
            },
            onItemClick = { bean ->
                val showDetailAction =
                    BaseFavoriteFragmentDirections
                        .actionBaseFavoriteFragmentToBeanDetailFragment()
                        .apply {
                            beanId = bean.id
                        }
                findNavController().navigate(showDetailAction)
            }
        )
    }

    private fun getSortTypeStrings(): Array<String> {
        return requireContext()
                .resources
                .getStringArray(R.array.bean_sort_types)
    }
}