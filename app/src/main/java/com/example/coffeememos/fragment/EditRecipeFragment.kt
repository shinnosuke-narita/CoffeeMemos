package com.example.coffeememos.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentEditBeanBinding
import com.example.coffeememos.databinding.FragmentEditRecipeBinding
import com.example.coffeememos.listener.SimpleTextWatcher
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.viewModel.EditRecipeViewModel
import com.example.coffeememos.viewModel.EditRecipeViewModelFactory


class EditRecipeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentEditRecipeBinding? = null
    private val binding
        get() = _binding!!

    // viewModel 初期化
    private val viewModel: EditRecipeViewModel by viewModels {
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        EditRecipeViewModelFactory(db.recipeDao())
    }

    private val safeArgs: EditRecipeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initialize(
            safeArgs.recipeId,
            RatingManager(
                RatingManager.Star(RatingManager.StarState.LIGHT),
                RatingManager.Star(RatingManager.StarState.DARK),
                RatingManager.Star(RatingManager.StarState.DARK),
                RatingManager.Star(RatingManager.StarState.DARK),
                RatingManager.Star(RatingManager.StarState.DARK)
            )
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // header セッティング
        binding.header.headerTitle.text = getString(R.string.edit_recipe)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }


        // 選択されたレシピの監視処理
        viewModel.selectedRecipe.observe(viewLifecycleOwner) { recipe ->
            binding.grindTextView.text = Constants.grindSizeList[recipe.grindSize]
            binding.roastTextView.text = Constants.roastList[recipe.roast]
            binding.toolEditText.setText(recipe.tool)
            binding.amountBeanEditText.setText(recipe.amountOfBeans.toString())
            binding.temperatureEditText.setText(recipe.temperature.toString())
            binding.preInfusionTimeEditText.setText(recipe.preInfusionTime.toString())
            binding.extractionTimeMinuteEditText.setText(recipe.extractionTimeMinutes.toString())
            binding.extractionTimeSecondsEditText.setText(recipe.extractionTimeSeconds.toString())
            binding.amountExtractionEditText.setText(recipe.amountExtraction.toString())
            binding.commentEditText.setText(recipe.comment)
        }


        // Favorite 関連
        viewModel.currentFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) binding.header.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.header.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        binding.header.favoriteBtn.setOnClickListener {
            if (viewModel.currentFavorite.value!!) viewModel.setFavorite(false)
            else viewModel.setFavorite(true)
        }



        // ★画像のリスト
        val recipeStarViewList: List<ImageView> = listOf(
            binding.beanStarFirst,
            binding.beanStarSecond,
            binding.beanStarThird,
            binding.beanStarFourth,
            binding.beanStarFifth,
        )
        // rating ★Viewの状態監視処理
        viewModel.recipeStarList.observe(viewLifecycleOwner) { starList ->
            for ((index, star) in starList.withIndex()) {
                if (star.state == RatingManager.StarState.LIGHT) recipeStarViewList[index].setImageResource(R.drawable.ic_baseline_star_beige_light_24)
                else recipeStarViewList[index].setImageResource(R.drawable.ic_baseline_star_grey)
            }
        }
        // Ratingの値 監視処理
        viewModel.recipeCurrentRating.observe(viewLifecycleOwner) { currentRating ->
            binding.beanRating.text = getString(R.string.rate_decimal, currentRating.toString())
        }
        // ★画像のクリックリスナーセット
        binding.beanStarFirst.setOnClickListener(this)
        binding.beanStarSecond.setOnClickListener(this)
        binding.beanStarThird.setOnClickListener(this)
        binding.beanStarFourth.setOnClickListener(this)
        binding.beanStarFifth.setOnClickListener(this)


        // TextChangeListener セット
        binding.toolEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setTool(editable.toString())
            }
        })
        binding.amountBeanEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setAmountBeans(editable.toString().toInt())
            }
        })
        binding.temperatureEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setTemperature(editable.toString().toInt())
            }
        })
        binding.preInfusionTimeEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setTemperature(editable.toString().toInt())
            }
        })
        binding.extractionTimeMinuteEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setExtractionTimeMinutes(editable.toString().toInt())
            }
        })
        binding.extractionTimeSecondsEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setExtractionTimeSeconds(editable.toString().toInt())
            }
        })
        binding.amountExtractionEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setAmountExtraction(editable.toString().toInt())
            }
        })
        binding.commentEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setComment(editable.toString())
            }
        })


        // TODO ロースト処理
        binding.selectRoastBtn.setOnClickListener {

        }


        // TODO グラインド処理
        binding.selectGrindBtn.setOnClickListener {

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // ★画像の共通クリックリスナー
    override fun onClick(starView: View) {
        when(starView.id) {
            R.id.beanStarFirst  -> viewModel.updateRatingState(1)
            R.id.beanStarSecond -> viewModel.updateRatingState(2)
            R.id.beanStarThird  -> viewModel.updateRatingState(3)
            R.id.beanStarFourth -> viewModel.updateRatingState(4)
            R.id.beanStarFifth  -> viewModel.updateRatingState(5)
        }
    }


}