package com.example.coffeememos.fragment

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION.SDK
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.coffeememos.*
import com.example.coffeememos.databinding.FragmentNewRecipeBinding
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.state.NewRecipeMenuState
import com.example.coffeememos.viewModel.NewRecipeViewModel
import com.example.coffeememos.viewModel.NewRecipeViewModelFactory
import com.google.android.material.snackbar.Snackbar

class NewRecipeFragment :
    Fragment(),
    SeekBar.OnSeekBarChangeListener,
    AdapterView.OnItemSelectedListener {

    // viewBinding
    private  var _binding: FragmentNewRecipeBinding? = null
    private val binding
        get() = _binding!!

    // アクティビティのコンテキストを保持
    private var mContext: Context? = null

    private val  viewModel: NewRecipeViewModel by viewModels {
        // viewModelの初期化
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        NewRecipeViewModelFactory(db.recipeDao(), db.beanDao(), db.tasteDao())
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
        // Inflate the layout for this fragment
        _binding = FragmentNewRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // header セッティング
        binding.header.headerTitle.text = getString(R.string.new_recipe)
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
        viewModel.sour.observe(viewLifecycleOwner) {
            binding.sourValues.text = it.toString()
            binding.sourSeekBar.progress = it - 1
        }
        viewModel.bitter.observe(viewLifecycleOwner) {
            binding.bitterValues.text = it.toString()
            binding.bitterSeekBar.progress = it - 1
        }
        viewModel.sweet.observe(viewLifecycleOwner) {
            binding.sweetValues.text = it.toString()
            binding.sweetSeekBar.progress = it - 1
        }
        viewModel.rich.observe(viewLifecycleOwner) {
            binding.richValues.text = it.toString()
            binding.richSeekBar.progress = it - 1
        }

        viewModel.flavor.observe(viewLifecycleOwner) {
            binding.flavorValues.text = it.toString()
            binding.flavorSeekBar.progress = it - 1
        }

        /**
         * fab 監視処理
         */
        // TODO 隠れたメニューのvisibility GONE調整
        viewModel.isMenuOpened.observe(viewLifecycleOwner) { state ->
            when(state) {
                NewRecipeMenuState.MENU_OPENED -> {
                    binding.wholeShadow.visibility = View.VISIBLE
                    binding.menuBtn.setImageResource(R.drawable.ic_baseline_close_24)
                    fadeInAnimation(binding.timeBtn)
                    fadeInAnimation(binding.saveBtn)
                }
                NewRecipeMenuState.MENU_CLOSED -> {
                    binding.wholeShadow.visibility = View.GONE
                    binding.menuBtn.setImageResource(R.drawable.ic_baseline_menu_24)
                    fadeOutAnimation(binding.timeBtn)
                    fadeOutAnimation(binding.saveBtn)
                }
            }
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
         * rateのクリックリスナー
         */
        binding.starFirst.setOnClickListener { viewModel.changeRatingState(1) }
        binding.starSecond.setOnClickListener { viewModel.changeRatingState(2) }
        binding.starThird.setOnClickListener { viewModel.changeRatingState(3) }
        binding.starFourth.setOnClickListener { viewModel.changeRatingState(4) }
        binding.starFifth.setOnClickListener { viewModel.changeRatingState(5) }

        /**
         * resetView 監視処理
         */
        viewModel.shouldResetView.observe(viewLifecycleOwner) { shouldReset ->
            if (!shouldReset)  return@observe

            resetView()
            Snackbar.make(binding.snackBarPlace, "新しいレシピを保存しました", Snackbar.LENGTH_SHORT).apply {
                mContext?.let {
                    setTextColor(ContextCompat.getColor(it, R.color.snackBar_text))
                    getView().setBackgroundColor(ContextCompat.getColor(it, R.color.snackBar_background))
                }
            }.show()

            // リセットフラグの初期化
            viewModel.setResetFlag(false)
        }

        binding.selectBeanBtn.setOnClickListener { v ->

            Navigation.findNavController(v).navigate(R.id.selectBeanFragment)

            setFragmentResultListener("selectedBean") { key, result ->
                viewModel.setBeanId(result.getLong("beanId"))

                binding.beanContainer.rvCountry.text = result.getString("country")
                binding.beanContainer.rvFarm.text = result.getString("farm")
                binding.beanContainer.rvDistrict.text = result.getString("district")
                binding.beanContainer.rvElevationFrom.text = getString(R.string.elevation_width_meters, result.getString("elevationFrom"))
                binding.beanContainer.rvElevationTo.text = getString(R.string.elevation_width_meters, result.getString("elevationTo"))
                binding.beanContainer.rvStore.text = result.getString("store")
                binding.beanContainer.rvProcess.text = result.getString("process")
                binding.beanContainer.rvRate.text = getString(R.string.rate_decimal, result.getString("rating"))
            }
        }




        binding.menuBtn.setOnClickListener { v ->
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
            // レシピ 保存処理
            viewModel.createNewRecipeAndTaste(
                binding.toolInputText.text.toString(),
                binding.extractionTime.text.toString(),
                binding.preInfusionTime.text.toString(),
                binding.temperature.text.toString(),
                binding.amountBeans.text.toString(),
                binding.amountExtraction.text.toString(),
                binding.comment.text.toString()
            )

            viewModel.setResetFlag(true)
        }

        /**
         * スピナーのセットアップ
         */
        mContext?.let {
            binding.roastSpinner.adapter = ArrayAdapter(it, android.R.layout.simple_list_item_1, Constants.roastList)
            binding.roastSpinner.onItemSelectedListener = this

            binding.grindSizeSpinner.adapter = ArrayAdapter(it, android.R.layout.simple_list_item_1, Constants.grindSizeList)
            binding.grindSizeSpinner.onItemSelectedListener = this
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
        if (!userFlag) return

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
     * spinner リスナー
     */
    override fun onItemSelected(av: AdapterView<*>?, view: View?, index: Int, id: Long) {
        when (view?.id) {
            binding.roastSpinner.id     -> viewModel.setRoastIndex(index)
            binding.grindSizeSpinner.id -> viewModel.setGrindSizeIndex(index)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}


    /**
     * アニメーション関連
     */
    private fun fadeInAnimation(view: View) {
        ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f).apply {
            duration = 500
        }.start()
    }

    private fun fadeOutAnimation(view: View) {
        ObjectAnimator.ofFloat(view, "alpha",  0.0f).apply {
            duration = 500
        }.start()
    }

    /**
     * viewのリセット
     */
    private fun resetView() {
        // お気に入り の初期化
        viewModel.setFavoriteFlag(false)

        // seekbar の初期化
        viewModel.setSourValue(3)
        viewModel.setBitterValue(3)
        viewModel.setSweetValue(3)
        viewModel.setRichValue(3)
        viewModel.setFlavorValue(3)

        // spinner の初期化
        binding.roastSpinner.setSelection(0)
        binding.grindSizeSpinner.setSelection(0)

        // textField の初期化
        binding.toolInputText.setText("")
        binding.amountBeans.setText("")
        binding.temperature.setText("")
        binding.preInfusionTime.setText("")
        binding.extractionTime.setText("")
        binding.amountExtraction.setText("")
        binding.comment.setText("")

        // rating の初期化
        viewModel.changeRatingState(1)

        // メニューを閉じる
        viewModel.setMenuOpenedFlag(NewRecipeMenuState.MENU_CLOSED)
    }
}