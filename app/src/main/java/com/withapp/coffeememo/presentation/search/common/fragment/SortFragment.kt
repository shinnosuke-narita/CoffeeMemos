package com.withapp.coffeememo.presentation.search.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.withapp.coffeememo.base.adapter.SortAdapter
import com.withapp.coffeememo.databinding.FragmentSortBinding
import com.withapp.coffeememo.base.dialog.model.DialogDataHolder

class SortFragment : Fragment() {
    private var _binding: FragmentSortBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var _adapter: BaseAdapter

    private lateinit var dataHolder: DialogDataHolder

    companion object  {
        fun create(index: Int, originData: Array<String>): SortFragment {
            return SortFragment().apply {
                arguments = Bundle().apply {
                    putInt("index", index)
                    putStringArray("originData", originData)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->

            dataHolder = DialogDataHolder(
                bundle.getInt("index"),
                bundle.getStringArray("originData") ?: arrayOf()
            )
        }
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
        _adapter = SortAdapter(requireContext(), dataHolder.dialogData)
        binding.lv.adapter = _adapter
        binding.lv.setOnItemClickListener { _, _, position, _ ->
            dataHolder.updateProcessList(position)
            _adapter.notifyDataSetChanged()
        }

        // 閉じるボタンクリックリスナ―
        binding.closeIcon.setOnClickListener {
            setFragmentResult("sortResult", Bundle().apply {
                putInt("selectedIndex", dataHolder.currentIndex)
            })

            parentFragmentManager.popBackStack()
        }

        // バックキー ハンドリング
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}