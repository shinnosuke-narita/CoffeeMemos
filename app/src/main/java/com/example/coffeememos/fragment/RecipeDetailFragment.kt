package com.example.coffeememos.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentRecipeDetailBinding
import com.example.coffeememos.dialog.EditTasteDialogFragment
import com.example.coffeememos.manager.ChartManager
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.manager.RatingManager.Star
import com.example.coffeememos.manager.RatingManager.StarState
import com.example.coffeememos.util.DateUtil
import com.example.coffeememos.viewModel.RecipeDetailViewModel
import com.example.coffeememos.viewModel.RecipeDetailViewModelFactory
import com.google.android.material.snackbar.Snackbar

class RecipeDetailFragment : Fragment() {
    private var mContext: Context? = null

    // viewBinding
    private  var _binding: FragmentRecipeDetailBinding? = null
    private val binding
        get() = _binding!!

    // viewModel 初期化
    private val viewModel: RecipeDetailViewModel by viewModels {
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        RecipeDetailViewModelFactory(db.beanDao(), db.recipeDao(), db.tasteDao())
    }

    private val safeArgs: RecipeDetailFragmentArgs by navArgs()

    private lateinit var chartManager: ChartManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewModel初期化処理
        viewModel.initialize(
            safeArgs.recipeId,
            safeArgs.beanId,
            safeArgs.tasteId,
            RatingManager(
                Star(StarState.LIGHT),
                Star(StarState.DARK),
                Star(StarState.DARK),
                Star(StarState.DARK),
                Star(StarState.DARK)
            ),
            RatingManager(
                Star(StarState.LIGHT),
                Star(StarState.DARK),
                Star(StarState.DARK),
                Star(StarState.DARK),
                Star(StarState.DARK)
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeStarViewList: List<ImageView> = listOf(
            binding.recipeStarFirst,
            binding.recipeStarSecond,
            binding.recipeStarThird,
            binding.recipeStarFourth,
            binding.recipeStarFifth,
        )

        val beanStarViewList: List<ImageView> = listOf(
            binding.beanCardView.beanStarFirst,
            binding.beanCardView.beanStarSecond,
            binding.beanCardView.beanStarThird,
            binding.beanCardView.beanStarFourth,
            binding.beanCardView.beanStarFifth,
        )

        // ChartManager 初期化
        chartManager = ChartManager()

        // レーダーチャート作成(データのセットはしない)
        chartManager.createRadarChart(binding.radarChart)

        // header セッティング
        binding.header.headerTitle.text = getString(R.string.recipe_detail)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.selectedTaste.observe(viewLifecycleOwner) { taste ->
            // レーダーチャートデータセット
            mContext?.let { context ->
                chartManager.setData(
                    context,
                    binding.radarChart,
                    taste.sour.toFloat(),
                    taste.bitter.toFloat(),
                    taste.sweet.toFloat(),
                    taste.flavor.toFloat(),
                    taste.rich.toFloat()
                )
            }
        }

        viewModel.selectedRecipe.observe(viewLifecycleOwner) { recipe ->
            binding.toolText.text             = recipe.tool
            binding.roastText.text            = Constants.roastList[recipe.roast]
            binding.grindText.text            = Constants.grindSizeList[recipe.grindSize]
            binding.amountBeanText.text       = recipe.amountOfBeans.toString()
            binding.temperatureText.text      = recipe.temperature.toString()
            binding.preInfusionTimeText.text  = recipe.preInfusionTime.toString()
            binding.extractionTimeText.text   = recipe.extractionTimeMinutes.toString()
            binding.amountExtractionText.text = recipe.amountExtraction.toString()
            binding.createdDateText.text      = DateUtil.formatEpochTimeMills(recipe.createdAt, DateUtil.pattern)
            binding.recipeCommentText.text    = recipe.comment

            if (recipe.isFavorite) binding.recipeFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.recipeFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        viewModel.selectedBean.observe(viewLifecycleOwner) { bean ->
            binding.beanCardView.countryText.text     = bean.country
            binding.beanCardView.farmText.text        = bean.farm
            binding.beanCardView.districtText.text    = bean.district
            binding.beanCardView.speciesText.text     = bean.species
            binding.beanCardView.elevationText.text   = getString(R.string.elevation_from_to, bean.elevationFrom.toString(), bean.elevationTo.toString())
            binding.beanCardView.storeText.text       = bean.store
            binding.beanCardView.processText.text     = Constants.processList[bean.process]
            binding.beanCardView.beanCommentText.text = bean.comment

            if (bean.isFavorite) binding.beanCardView.beanFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.beanCardView.beanFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }


        // Ratingの★画像状態 監視処理
        viewModel.recipeStarList.observe(viewLifecycleOwner) { starList ->
            for ((index, star) in starList.withIndex()) {
                if (star.state == StarState.LIGHT) recipeStarViewList[index].setImageResource(R.drawable.ic_baseline_star_beige_light_24)
                else recipeStarViewList[index].setImageResource(R.drawable.ic_baseline_star_grey)
            }
        }
        viewModel.beanStarList.observe(viewLifecycleOwner) { starList ->
            for ((index, star) in starList.withIndex()) {
                if (star.state == StarState.LIGHT) beanStarViewList[index].setImageResource(R.drawable.ic_baseline_star_beige_light_24)
                else beanStarViewList[index].setImageResource(R.drawable.ic_baseline_star_grey)
            }
        }

        // Ratingの値 監視処理
        viewModel.recipeCurrentRating.observe(viewLifecycleOwner) { currentRating ->
            binding.recipeRating.text = getString(R.string.rate_decimal, currentRating.toString())
        }
        viewModel.beanCurrentRating.observe(viewLifecycleOwner) { currentRating ->
            binding.beanCardView.beanRating.text = getString(R.string.rate_decimal, currentRating.toString())
        }


        // レシピ編集画面へ遷移
        binding.recipeEditIcon.setOnClickListener { v ->
            val showEditRecipeAction = RecipeDetailFragmentDirections.showEditRecipeAction().apply {
                recipeId = viewModel.selectedRecipe.value!!.id
            }
            Navigation.findNavController(v).navigate(showEditRecipeAction)
        }
        // コーヒー豆編集画面へ遷移
        binding.beanCardView.beanEditIcon.setOnClickListener { v ->
            val showEditBeanAction = RecipeDetailFragmentDirections.showEditBeanAction().apply {
                beanId = viewModel.selectedBean.value!!.id
            }
            Navigation.findNavController(v).navigate(showEditBeanAction)
        }
        // テイスト編集ダイアログ表示
        binding.tasteEditIcon.setOnClickListener {
            val taste = viewModel.selectedTaste.value!!
            EditTasteDialogFragment
                .create(taste.sour, taste.bitter, taste.sweet, taste.flavor, taste.rich)
                .show(childFragmentManager, EditTasteDialogFragment::class.simpleName)
        }
        // テイスト編集ダイアログ結果受信
        childFragmentManager.setFragmentResultListener("newTaste", viewLifecycleOwner) { _, newTaste ->
            viewModel.updateTaste(
                newTaste.getInt("sour"),
                newTaste.getInt("bitter"),
                newTaste.getInt("sweet"),
                newTaste.getInt("flavor"),
                newTaste.getInt("rich"))
        }

        setFragmentResultListener("beanUpdate") { _, bundle ->
            viewModel.updateBean(bundle.getLong("beanId"))

            Snackbar.make(binding.snackBarPlace, "コーヒー豆を更新しました", Snackbar.LENGTH_SHORT).apply {
                mContext?.let {
                    setTextColor(ContextCompat.getColor(it, R.color.snackBar_text))
                    getView().setBackgroundColor(
                        ContextCompat.getColor(it,
                        R.color.white
                    ))
                }
            }.show()
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
}