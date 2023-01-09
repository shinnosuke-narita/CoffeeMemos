package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentEditBeanBinding
import com.example.coffeememos.databinding.FragmentEditRecipeBinding
import com.example.coffeememos.dialog.BasicDialogFragment
import com.example.coffeememos.dialog.ListDialogFragment
import com.example.coffeememos.listener.SimpleTextWatcher
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.utilities.DateUtil
import com.example.coffeememos.utilities.ViewUtil
import com.example.coffeememos.validate.ValidationInfo
import com.example.coffeememos.validate.ValidationState
import com.example.coffeememos.viewModel.EditRecipeViewModel
import com.example.coffeememos.viewModel.EditRecipeViewModelFactory
import java.util.*


class EditRecipeFragment : BaseFragment(), View.OnClickListener {
    private var _binding: FragmentEditRecipeBinding? = null
    private val binding
        get() = _binding!!

    // viewModel 初期化
    private val viewModel: EditRecipeViewModel by viewModels {
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        EditRecipeViewModelFactory(db.recipeDao())
    }

    private val safeArgs: EditRecipeFragmentArgs by navArgs()

    // Rating ☆画像リスト
    private lateinit var recipeStarViewList: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initialize(
            safeArgs.recipeId,
            RatingManager()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditRecipeBinding.inflate(inflater, container, false)

        // ★画像リスト 初期化
        recipeStarViewList = listOf(binding.beanStarFirst, binding.beanStarSecond, binding.beanStarThird, binding.beanStarFourth, binding.beanStarFifth)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // header セッティング
        binding.header.headerTitle.text = getString(R.string.edit_recipe)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        /////////////////////
        // observe process //
        /////////////////////
        // 選択されたレシピ
        viewModel.selectedRecipe.observe(viewLifecycleOwner) { recipe ->
            val preInfusionTime = DateUtil.convertSeconds(recipe.preInfusionTime)
            val extractionTimeMinutes = DateUtil.getMinutes(recipe.extractionTime)
            val extractionTimeSeconds = DateUtil.getSeconds(recipe.extractionTime)

            binding.toolEditText.setText(recipe.tool)
            binding.amountBeanEditText.setText(recipe.amountOfBeans.toString())
            binding.temperatureEditText.setText(recipe.temperature.toString())
            binding.amountExtractionEditText.setText(recipe.amountExtraction.toString())
            binding.commentEditText.setText(recipe.comment)
            binding.preInfusionTimeEditText.setText(preInfusionTime.toString())
            binding.extractionTimeMinuteEditText.setText(extractionTimeMinutes.toString())
            binding.extractionTimeSecondsEditText.setText(extractionTimeSeconds.toString())
        }
        // Favorite
        viewModel.currentFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) binding.header.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.header.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
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
        // 編集ダイアログ
        // Roast
        viewModel.currentRoast.observe(viewLifecycleOwner) { roast ->
            binding.roastTextView.text = Constants.roastList[roast]
        }
        // Grind Size
        viewModel.currentGrind.observe(viewLifecycleOwner) { grind ->
            binding.grindTextView.text = Constants.grindSizeList[grind]
        }
        ////////////////////////
        // validation message //
        ////////////////////////
        viewModel.toolValidation.observe(viewLifecycleOwner) { validation ->
            setUpValidationMessage(validation, binding.scrollView, binding.header.root, binding.toolValidateMessage, binding.toolTitle)
        }
        viewModel.temperatureValidation.observe(viewLifecycleOwner) { validation ->
            setUpValidationMessage(validation, binding.scrollView, binding.header.root, binding.temperatureValidateMessage, binding.temperatureTitle)
        }
        viewModel.extractionTimeValidation.observe(viewLifecycleOwner) { validation ->
            setUpValidationMessage(validation, binding.scrollView, binding.header.root, binding.extractionTimeValidateMessage, binding.extractionTimeTitle)
        }

        ///////////////////
        // clickListener //
        //////////////////
        // お気に入りアイコン
        binding.header.favoriteBtn.setOnClickListener {
            if (viewModel.currentFavorite.value!!) viewModel.setFavorite(false)
            else viewModel.setFavorite(true)
        }
        // 焙煎度選択アイコン
        binding.selectRoastBtn.setOnClickListener {
            ListDialogFragment
                .create(viewModel.currentRoast.value!!, getString(R.string.edit_roast), "updateRoast", Constants.roastList.toTypedArray())
                .show(childFragmentManager, ListDialogFragment::class.simpleName)
        }
        // 編集アイコンクリックリスナ―
        binding.selectGrindBtn.setOnClickListener {
            ListDialogFragment
                .create(viewModel.currentGrind.value!!, getString(R.string.edit_grind), "updateGrind", Constants.grindSizeList.toTypedArray())
                .show(childFragmentManager, ListDialogFragment::class.simpleName)
        }
        // ★画像のクリックリスナーセット
        binding.beanStarFirst.setOnClickListener(this)
        binding.beanStarSecond.setOnClickListener(this)
        binding.beanStarThird.setOnClickListener(this)
        binding.beanStarFourth.setOnClickListener(this)
        binding.beanStarFifth.setOnClickListener(this)

        // 保存処理
        binding.saveBtn.setOnClickListener {
            BasicDialogFragment
                .create(
                    getString(R.string.update_recipe_title),
                    getString(R.string.update_message),
                    getString(R.string.update),
                    getString(R.string.cancel),
                    "updateRecipe")
                .show(childFragmentManager, BasicDialogFragment::class.simpleName)
        }
        //更新ダイアログの結果受信
        childFragmentManager.setFragmentResultListener("updateRecipe", viewLifecycleOwner) { _, _ ->
            // validation
            if (viewModel.validateRecipeData(requireActivity())) return@setFragmentResultListener

            // 保存処理
            viewModel.updateRecipe()

            setFragmentResult("recipeUpdate", Bundle().apply { putLong("recipeId", viewModel.selectedRecipe.value!!.id) })
            findNavController().popBackStack()
        }

        ////////////////////////
        // textChangeListener //
        ///////////////////////
        binding.toolEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setTool(editable.toString())
            }
        })
        binding.amountBeanEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setAmountBeans(editable.toString())
            }
        })
        binding.temperatureEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setTemperature(editable.toString())
            }
        })
        binding.preInfusionTimeEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setPreInfusionTime(editable.toString())
            }
        })
        binding.extractionTimeMinuteEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setExtractionTimeMinutes(editable.toString())
            }
        })
        binding.extractionTimeSecondsEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setExtractionTimeSeconds(editable.toString())
            }
        })
        binding.amountExtractionEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setAmountExtraction(editable.toString())
            }
        })
        binding.commentEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setComment(editable.toString())
            }
        })

        ////////////////////////////
        // FragmentResultListener //
        ///////////////////////////
        // RoastDialogからの結果を受信
        childFragmentManager.setFragmentResultListener("updateRoast", viewLifecycleOwner) {_, bundle ->
            viewModel.setRoast(bundle.getInt("newIndex"))
        }
        // GrindDialogからの結果を受信
        childFragmentManager.setFragmentResultListener("updateGrind", viewLifecycleOwner) {_, bundle ->
            viewModel.setGrind(bundle.getInt("newIndex"))
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