package com.example.coffeememos.fragment

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.navigation.Navigation
import com.example.coffeememos.*
import com.example.coffeememos.databinding.FragmentNewRecipeBinding
import com.example.coffeememos.state.NewRecipeMenuState
import com.example.coffeememos.viewModel.NewRecipeViewModel
import com.example.coffeememos.viewModel.NewRecipeViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewRecipeFragment : Fragment(), SeekBar.OnSeekBarChangeListener {
    // viewBinding
    private  var _binding: FragmentNewRecipeBinding? = null
    private val binding
        get() = _binding!!

    // アクティビティのコンテキストを保持
    private var mContext: Context? = null

    private lateinit var viewModel: NewRecipeViewModel

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

        // viewModelの初期化
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        viewModel = NewRecipeViewModelFactory(
                db.recipeDao(), db.beanDao(), db.tasteDao()
            ).create(NewRecipeViewModel::class.java)

        /**
         * seekBar リスナーセット
         */
        binding.sourSeekBar.setOnSeekBarChangeListener(this)
        binding.bitterSeekBar.setOnSeekBarChangeListener(this)
        binding.sweetSeekBar.setOnSeekBarChangeListener(this)
        binding.richSeekBar.setOnSeekBarChangeListener(this)
        binding.flavorSeekBar.setOnSeekBarChangeListener(this)

        /**
         * seekbar 監視処理
         */
        viewModel.sour.observe(viewLifecycleOwner) { binding.sourValues.text = it.toString() }
        viewModel.bitter.observe(viewLifecycleOwner) { binding.bitterValues.text = it.toString() }
        viewModel.sweet.observe(viewLifecycleOwner) { binding.sweetValues.text = it.toString() }
        viewModel.rich.observe(viewLifecycleOwner) { binding.richValues.text = it.toString() }
        viewModel.flavor.observe(viewLifecycleOwner) { binding.flavorValues.text = it.toString() }

        /**
         * fab 監視処理
         */
        viewModel.isMenuOpened.observe(viewLifecycleOwner) { state ->
            when(state) {
                NewRecipeMenuState.MENU_OPENED -> {
                    binding.wholeShadow.visibility = View.VISIBLE
                    binding.toTimerFragmentBtn.setImageResource(R.drawable.ic_baseline_close_24)
                    fadeInAnimation(binding.timeBtn)
                    fadeInAnimation(binding.saveBtn)
                }
                NewRecipeMenuState.MENU_CLOSED -> {
                    binding.wholeShadow.visibility = View.GONE
                    binding.toTimerFragmentBtn.setImageResource(R.drawable.ic_baseline_menu_24)
                    fadeOutAnimation(binding.timeBtn)
                    fadeOutAnimation(binding.saveBtn)
                }
            }
        }


        binding.toTimerFragmentBtn.setOnClickListener { v ->
            when(viewModel.isMenuOpened.value) {
                NewRecipeMenuState.MENU_OPENED -> viewModel.setMenuOpenedFlag(NewRecipeMenuState.MENU_CLOSED)
                NewRecipeMenuState.MENU_CLOSED -> viewModel.setMenuOpenedFlag(NewRecipeMenuState.MENU_OPENED)
                else -> viewModel.setMenuOpenedFlag(NewRecipeMenuState.MENU_CLOSED)
            }
        }

        // 計測画面に遷移
        binding.timeBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.timerFragment)
        }

        binding.saveBtn.setOnClickListener {
            // TODO: 保存処理
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

    /**
     * seekBarリスナー
     */
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, userFlag: Boolean) {
        when(seekBar?.id) {
            binding.sourSeekBar.id -> viewModel.changeTasteValue(TasteKind.SOUR, progress)
            binding.bitterSeekBar.id -> viewModel.changeTasteValue(TasteKind.BITTER, progress)
            binding.sweetSeekBar.id -> viewModel.changeTasteValue(TasteKind.SWEET, progress)
            binding.richSeekBar.id -> viewModel.changeTasteValue(TasteKind.RICH, progress)
            binding.flavorSeekBar.id -> viewModel.changeTasteValue(TasteKind.FLAVOR, progress)
        }
    }
    override fun onStartTrackingTouch(p0: SeekBar?) {}
    override fun onStopTrackingTouch(p0: SeekBar?) {}


    /**
     * アニメーション関連
     */
    fun fadeInAnimation(view: View) {
        ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f).apply {
            duration = 500
        }.start()
    }

    fun fadeOutAnimation(view: View) {
        ObjectAnimator.ofFloat(view, "alpha",  0.0f).apply {
            duration = 500
        }.start()
    }
}