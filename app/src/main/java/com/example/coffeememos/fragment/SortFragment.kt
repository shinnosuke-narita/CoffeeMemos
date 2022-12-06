package com.example.coffeememos.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.coffeememos.search.SortType
import com.example.coffeememos.adapter.SortAdapter
import com.example.coffeememos.databinding.FragmentSortBinding
import com.example.coffeememos.viewModel.SearchRecipeViewModel
import com.example.coffeememos.viewModel.SortViewModel

class SortFragment : Fragment() {
    private var _binding: FragmentSortBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: SortViewModel by viewModels()
    // 親のviewModel
    private val searchRecipeViewModel: SearchRecipeViewModel by viewModels({requireParentFragment()})

    private lateinit var _adapter: BaseAdapter

    companion object  {
        fun create(sortName: String): SortFragment {
            return SortFragment().apply {
                arguments = Bundle().apply {
                    putString("sortName", sortName)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sortName = arguments?.getString("sortName", "") ?: ""

        viewModel.setSortItemList(sortName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // アダプター保持
        _adapter = SortAdapter(requireContext(), viewModel.sortItemList)
        binding.lv.adapter = _adapter
        binding.lv.setOnItemClickListener { _, _, position, _ ->
            viewModel.changeData(position)
            _adapter.notifyDataSetChanged()
        }


        // 閉じるボタンクリックリスナ―
        binding.closeIcon.setOnClickListener {
            // shadow消す
            searchRecipeViewModel.changeBottomSheetState()

            // sort 処理
            val sortType: SortType = viewModel.getSelectedSortType()
            searchRecipeViewModel.sortSearchResult(sortType)

            parentFragmentManager.popBackStack()
        }

        // バックキー ハンドリング
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            searchRecipeViewModel.changeBottomSheetState()
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}