package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentNewRecipeBinding

class NewRecipeFragment : Fragment() {
    private  var _binding: FragmentNewRecipeBinding? = null
    private val binding
        get() = _binding!!

    private var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 計測画面に遷移
        binding.toTimerFragmentBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.timerFragment)
        }

        // スピナーのセットアップ
        mContext?.let {
            val roastSpinnerAdapter =
                ArrayAdapter(it, android.R.layout.simple_list_item_1, Constants.roastList)
            binding.roastSpinner.adapter = roastSpinnerAdapter

            val grindSizeAdapter =
                ArrayAdapter(it, android.R.layout.simple_list_item_1, Constants.grindSizeList)
            binding.grindSizeSpinner.adapter = grindSizeAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = NewRecipeFragment()
    }
}