package com.withapp.coffeememo.favorite.bean.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.withapp.coffeememo.databinding.FavoriteContentsBinding
import com.withapp.coffeememo.databinding.FragmentFavoriteBeanBinding
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel
import com.withapp.coffeememo.favorite.bean.presentation.adapter.FavoriteBeanAdapter
import com.withapp.coffeememo.favorite.common.presentation.view.BaseFavoriteFragmentDirections
import com.withapp.coffeememo.favorite.common.presentation.view.DeleteFavoriteSnackBar
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize()
    }

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

        viewModel.favoriteBeans.observe(viewLifecycleOwner) { beans ->
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
            binding.currentSort.text = sortType.getSortName()
        }
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
}