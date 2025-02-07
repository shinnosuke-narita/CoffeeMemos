package com.withapp.coffeememo.presentation.detail.recipe.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.withapp.coffeememo.R
import com.withapp.coffeememo.infra.ad_mob.locale.LocalizationManager
import com.withapp.coffeememo.databinding.FragmentRecipeDetailBinding
import com.withapp.coffeememo.presentation.base.dialog.BasicDialogFragment
import com.withapp.coffeememo.presentation.base.dialog.EditTasteDialogFragment
import com.withapp.coffeememo.presentation.detail.recipe.chart.ChartManager
import com.withapp.coffeememo.entity.Rating
import com.withapp.coffeememo.entity.Rating.StarState
import com.withapp.coffeememo.presentation.utilities.DateUtil
import com.withapp.coffeememo.presentation.utilities.SnackBarUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {
    private var mContext: Context? = null

    // viewBinding
    private  var _binding: FragmentRecipeDetailBinding? = null
    private val binding
        get() = _binding!!

    // viewModel 初期化
    private val viewModel: RecipeDetailViewModel by viewModels()

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
            Rating(),
            Rating()
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
            binding.recipeCardView.recipeStarFirst,
            binding.recipeCardView.recipeStarSecond,
            binding.recipeCardView.recipeStarThird,
            binding.recipeCardView.recipeStarFourth,
            binding.recipeCardView.recipeStarFifth,
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
        chartManager.createRadarChart(
            requireContext(),
            binding.radarChart
        )

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
            val roastList: List<String> =
                LocalizationManager.getRoastList()
            val grindSizeList: List<String> =
                LocalizationManager.getGrindSizeList()

            binding.recipeCardView.toolText.text = recipe.tool
            binding.recipeCardView.recipeCommentText.text = recipe.comment
            binding.recipeCardView.amountBeanText.text       = recipe.amountOfBeans.toString()
            binding.recipeCardView.temperatureText.text      = recipe.temperature.toString()
            binding.recipeCardView.amountExtractionText.text = recipe.amountExtraction.toString()
            binding.recipeCardView.roastText.text            = roastList[recipe.roast]
            binding.recipeCardView.grindText.text            = grindSizeList[recipe.grindSize]
            binding.recipeCardView.preInfusionTimeText.text  = DateUtil.formatPreInfusionTime(requireContext(), recipe.preInfusionTime)
            binding.recipeCardView.extractionTimeText.text   = DateUtil.formatExtractionTime(requireContext(), recipe.extractionTime)
            binding.recipeCardView.createdDateText.text =
                DateUtil.formatEpochTimeMills(
                    recipe.createdAt,
                    requireContext().getString(R.string.date_pattern))


            if (recipe.isFavorite) binding.recipeCardView.recipeFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.recipeCardView.recipeFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        viewModel.selectedBean.observe(viewLifecycleOwner) { bean ->
            val processList: List<String> =
                LocalizationManager.getProcessList()

            binding.beanCardView.countryText.text     = bean.country
            binding.beanCardView.farmText.text        = bean.farm
            binding.beanCardView.districtText.text    = bean.district
            binding.beanCardView.speciesText.text     = bean.species
            binding.beanCardView.elevationText.text   = getString(R.string.elevation_from_to, bean.elevationFrom.toString(), bean.elevationTo.toString())
            binding.beanCardView.storeText.text       = bean.store
            binding.beanCardView.processText.text     = processList[bean.process]
            binding.beanCardView.beanCommentText.text = bean.comment
            binding.beanCardView.createdAtText.text =
                DateUtil.formatEpochTimeMills(
                    bean.createdAt,
                    getString(R.string.date_pattern)
                )

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
            binding.recipeCardView.recipeRating.text = getString(R.string.rate_decimal, currentRating.toString())
        }
        viewModel.beanCurrentRating.observe(viewLifecycleOwner) { currentRating ->
            binding.beanCardView.beanRating.text = getString(R.string.rate_decimal, currentRating.toString())
        }


        // 削除処理
        binding.deleteBtn.setOnClickListener {
            BasicDialogFragment
                .create(
                    getString(R.string.delete_recipe_title),
                    getString(R.string.delete_recipe_message),
                    getString(R.string.delete),
                    getString(R.string.cancel),
                "deleteRecipe")
                .show(childFragmentManager, BasicDialogFragment::class.simpleName)
        }
        //削除ダイアログの結果受信
        childFragmentManager.setFragmentResultListener("deleteRecipe", viewLifecycleOwner) { _, _ ->
            viewModel.deleteRecipe()

            setFragmentResult("deleteRecipe", Bundle())
            findNavController().popBackStack()
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


        // コーヒー豆編集画面へ遷移
        binding.beanEditIcon.setOnClickListener { v ->
            val showEditBeanAction =
                RecipeDetailFragmentDirections.showEditBeanAction()
                    .apply {
                        beanId = viewModel.selectedBean.value!!.id
                }
            Navigation.findNavController(v).navigate(showEditBeanAction)
        }
        // コーヒー豆編集結果受信
        setFragmentResultListener("beanUpdate") { _, bundle ->
            viewModel.updateBean(bundle.getLong("beanId"))

            SnackBarUtil.showSimpleSnackBar(
                requireContext(),
                binding.snackBarPlace,
                getString(R.string.bean_finish_update_message)
            )
        }


        // レシピ編集画面へ遷移
        binding.recipeEditIcon.setOnClickListener { v ->
            val showEditRecipeAction = RecipeDetailFragmentDirections.showEditRecipeAction()
                .apply {
                    recipeId = viewModel.selectedRecipe.value!!.id
                }

            Navigation.findNavController(v).navigate(showEditRecipeAction)
        }
        // レシピ編集結果受信
        setFragmentResultListener("recipeUpdate") { _, bundle ->
            viewModel.updateRecipe(bundle.getLong("recipeId"))

            SnackBarUtil.showSimpleSnackBar(
                requireContext(),
                binding.snackBarPlace,
                getString(R.string.recipe_finish_update_message)
            )
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