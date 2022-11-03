package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentNewBeanBinding
import com.example.coffeememos.viewModel.NewBeanViewModel
import com.example.coffeememos.viewModel.NewBeanViewModelFactory
import com.example.coffeememos.viewModel.NewRecipeViewModel
import com.example.coffeememos.viewModel.NewRecipeViewModelFactory
import com.google.android.material.snackbar.Snackbar


class NewBeanFragment : Fragment() {
    // viewBinding
    private var _binding: FragmentNewBeanBinding? = null
    private val binding get() = _binding!!


    // アクティビティのコンテキストを保持
    private var mContext: Context? = null

    private val viewModel: NewBeanViewModel by viewModels {
        // viewModelの初期化
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        NewBeanViewModelFactory(db.beanDao())
    }

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
        _binding = FragmentNewBeanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // header セッティング
        binding.header.headerTitle.text = getString(R.string.new_bean)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        // お気に入り 監視処理
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) binding.header.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.header.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        binding.header.favoriteBtn.setOnClickListener {
            if (viewModel.isFavorite.value == true) viewModel.setFavoriteFlag(false)
            else viewModel.setFavoriteFlag(true)
        }


        /**
         * rateの監視処理
         */
        viewModel.starFirst.observe(viewLifecycleOwner) { shouldSetColor ->
            if (shouldSetColor) binding.starFirst.setImageResource(R.drawable.ic_baseline_star_yellow)
            else binding.starFirst.setImageResource(R.drawable.ic_baseline_star_grey)
        }
        viewModel.starSecond.observe(viewLifecycleOwner) { shouldSetColor ->
            if (shouldSetColor) binding.starSecond.setImageResource(R.drawable.ic_baseline_star_yellow)
            else binding.starSecond.setImageResource(R.drawable.ic_baseline_star_grey)
        }
        viewModel.starThird.observe(viewLifecycleOwner) { shouldSetColor ->
            if (shouldSetColor) binding.starThird.setImageResource(R.drawable.ic_baseline_star_yellow)
            else binding.starThird.setImageResource(R.drawable.ic_baseline_star_grey)
        }
        viewModel.starFourth.observe(viewLifecycleOwner) { shouldSetColor ->
            if (shouldSetColor) binding.starFourth.setImageResource(R.drawable.ic_baseline_star_yellow)
            else binding.starFourth.setImageResource(R.drawable.ic_baseline_star_grey)
        }
        viewModel.starFifth.observe(viewLifecycleOwner) { shouldSetColor ->
            if (shouldSetColor) binding.starFifth.setImageResource(R.drawable.ic_baseline_star_yellow)
            else binding.starFifth.setImageResource(R.drawable.ic_baseline_star_grey)
        }

        /**
         * resetView 監視処理
         */
        viewModel.shouldResetView.observe(viewLifecycleOwner) { shouldReset ->
            if (!shouldReset)  return@observe

            resetView()
            Snackbar.make(binding.snackBarPlace, "新しいコーヒー豆を保存しました", Snackbar.LENGTH_SHORT).apply {
                mContext?.let {
                    setTextColor(ContextCompat.getColor(it, R.color.snackBar_text))
                    getView().setBackgroundColor(ContextCompat.getColor(it,
                        R.color.white
                    ))
                }
            }.show()

            // リセットフラグの初期化
            viewModel.setResetFlag(false)
        }

        viewModel.selectedProcess.observe(viewLifecycleOwner) {index ->
            binding.processSpinner.setSelection(index)
        }

        /**
         * rateのクリックリスナー
         */
        binding.starFirst.setOnClickListener { viewModel.changeRatingState(1) }
        binding.starSecond.setOnClickListener { viewModel.changeRatingState(2) }
        binding.starThird.setOnClickListener { viewModel.changeRatingState(3) }
        binding.starFourth.setOnClickListener { viewModel.changeRatingState(4) }
        binding.starFifth.setOnClickListener { viewModel.changeRatingState(5) }

        /**
         * スピナーのセットアップ
         */
        mContext?.let {
            binding.processSpinner.adapter =
                ArrayAdapter(it, android.R.layout.simple_list_item_1, Constants.processList)
            binding.processSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(av: AdapterView<*>?, v: View?, index: Int, id: Long) {
                        viewModel.setProcessIndex(index)
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }

        binding.saveBtn.setOnClickListener {
            // レシピ 保存処理
            viewModel.createNewBean(
                binding.country.text.toString(),
                binding.farm.text.toString(),
                binding.district.text.toString(),
                binding.species.text.toString(),
                binding.elevationFrom.text.toString(),
                binding.elevationTo.text.toString(),
                binding.purchaseStore.text.toString(),
                binding.comment.text.toString()
            )

            viewModel.setResetFlag(true)
        }
    }

    private fun resetView() {
        binding.country.setText("")
        binding.farm.setText("")
        binding.district.setText("")
        binding.species.setText("")
        binding.elevationFrom.setText("")
        binding.elevationTo.setText("")
        binding.purchaseStore.setText("")
        binding.comment.setText("")

        viewModel.changeRatingState(1)
        viewModel.setProcessIndex(0)
        viewModel.setResetFlag(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

}