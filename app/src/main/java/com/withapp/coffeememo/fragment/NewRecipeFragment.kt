package com.withapp.coffeememo.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.withapp.coffeememo.R
import com.withapp.coffeememo.core.ad_mob.locale.LocalizationManager
import com.withapp.coffeememo.databinding.FragmentNewRecipeBinding
import com.withapp.coffeememo.dialog.BasicDialogFragment
import com.withapp.coffeememo.dialog.ListDialogFragment
import com.withapp.coffeememo.listener.SimpleTextWatcher
import com.withapp.coffeememo.manager.RatingManager
import com.withapp.coffeememo.state.InputType
import com.withapp.coffeememo.state.MenuState
import com.withapp.coffeememo.state.ProcessState
import com.withapp.coffeememo.state.SelectBeanBtnAction
import com.withapp.coffeememo.utilities.AnimUtil
import com.withapp.coffeememo.utilities.DateUtil
import com.withapp.coffeememo.utilities.ViewUtil
import com.withapp.coffeememo.viewModel.MainViewModel
import com.withapp.coffeememo.viewModel.NewRecipeViewModel
import com.withapp.coffeememo.utilities.SnackBarUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewRecipeFragment :
    BaseFragment(),
    View.OnClickListener {
    // viewBinding
    private  var _binding: FragmentNewRecipeBinding? = null
    private val binding
        get() = _binding!!

    private val  viewModel: NewRecipeViewModel by viewModels()

    // 共有viewModel
    private val mainViewModel: MainViewModel by activityViewModels()

    private val safeArgs: NewRecipeFragmentArgs by navArgs()

    // Ratingの☆画像リスト
    private lateinit var recipeStarViewList: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initialize(
            RatingManager(),
            safeArgs.preInfusionTimeInputType,
            safeArgs.extractionTimeInputType)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewRecipeBinding.inflate(inflater, container, false)
        // startViewList 初期化
        recipeStarViewList = listOf(binding.starFirst, binding.starSecond, binding.starThird, binding.starFourth, binding.starFifth)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // header セッティング
        setUpHeader()

        // お気に入り
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) binding.header.actionBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.header.actionBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        // 蒸らし時間
        viewModel.preInfusionTimeInputType.observe(viewLifecycleOwner) { inputType ->
            changeVisibility(inputType, binding.preInfusionTimeEditText, binding.preInfusionTimeTextView)
        }
        // タイマーで計測した蒸らし時間
        mainViewModel.preInfusionTime
            .observe(viewLifecycleOwner) { preInfusionTime ->
                val preInfusionTimeStr: String =
                     DateUtil.formatPreInfusionTime(
                         requireContext(),
                         preInfusionTime
                     )

                binding.preInfusionTimeTextView.text = preInfusionTimeStr
        }
        // 抽出時間
        viewModel.extractionTimeInputType
            .observe(viewLifecycleOwner) { inputType ->
            changeVisibility(inputType, binding.extractionTimeWrapper, binding.extractionTimeTextView)
        }
        // タイマーで計測した抽出時間
        mainViewModel.extractionTime.observe(viewLifecycleOwner) { extractionTime ->
            val extractionTimeStr: String =
                DateUtil.formatExtractionTime(
                    requireContext(),
                    extractionTime
                )

            binding.extractionTimeTextView.text = extractionTimeStr
        }
        // selectBeanBtnAction
        viewModel.selectBeanBtnAction.observe(viewLifecycleOwner) { action ->
            when(action) {
                SelectBeanBtnAction.SHOW_DIALOG -> {
                    BasicDialogFragment
                        .create(
                            getString(R.string.bean_not_registered_title),
                            getString(R.string.bean_not_registered_message),
                            getString(R.string.OK),
                            getString(R.string.cancel),
                            "beanNotRegistered")
                        .show(childFragmentManager, BasicDialogFragment::class.simpleName)
                }
                SelectBeanBtnAction.SHOW_NEW_BEAN_FRAGMENT -> {
                    findNavController().navigate(R.id.newBeanFragment)
                    viewModel.setSelectBeanBtnAction(SelectBeanBtnAction.NOTHING)
                }
                SelectBeanBtnAction.SHOW_SELECT_BEAN_FRAGMENT -> {
                    findNavController().navigate(R.id.selectBeanFragment)
                    viewModel.setSelectBeanBtnAction(SelectBeanBtnAction.NOTHING)
                }
                SelectBeanBtnAction.NOTHING -> {
                    // do nothing(デフォルト)
                }
                else -> {
                    // do nothing(null対策)
                }
            }
        }
        // Roast
        viewModel.currentRoast.observe(viewLifecycleOwner) { roast ->
            val roastList: List<String> =
                LocalizationManager.getRoastList()
            binding.roastTextView.text = roastList[roast]
        }
        // Grind Size
        viewModel.currentGrind.observe(viewLifecycleOwner) { grind ->
            val grindSizeList: List<String> =
                LocalizationManager.getGrindSizeList()
            binding.grindTextView.text = grindSizeList[grind]
        }
        // 選択されたコーヒー豆
        mainViewModel.selectedBean.observe(viewLifecycleOwner) { bean ->
            if (bean == null) return@observe

            val processList: List<String> =
                LocalizationManager.getProcessList()

            val beanContainer = binding.beanContainer
            beanContainer.country.text     = bean.country
            beanContainer.district.text    = bean.district
            beanContainer.storeName.text   = bean.store
            beanContainer.processName.text = processList[bean.process]
            beanContainer.speciesName.text = bean.species
            beanContainer.ratingWrapper.rating.text      = getString(R.string.rate_decimal, bean.rating.toString())
            beanContainer.ratingWrapper.root.visibility = View.VISIBLE
            beanContainer.createdAt.text   =
                DateUtil.formatEpochTimeMills(
                    bean.createdAt,
                    requireContext().getString(R.string.date_pattern)
                )

            ViewUtil.setCardTag(beanContainer.farm, bean.farm)
            ViewUtil.setCardTag(beanContainer.district, bean.district)
            ViewUtil.setFavoriteIcon(beanContainer.favoriteIcon, bean.isFavorite)
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
        // progressBar 監視処理
        viewModel.processState.observe(viewLifecycleOwner) { state ->
            when(state) {
                ProcessState.BEFORE_PROCESSING -> {
                    binding.progressBar.visibility = View.GONE
                }
                ProcessState.PROCESSING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                ProcessState.FINISH_PROCESSING -> {
                    // mainViewModel データをリセット
                    mainViewModel.resetBean()
                    mainViewModel.resetTime()

                    binding.progressBar.visibility = View.GONE
                    setFragmentResult("createRecipe", Bundle())
                    // ホームレシピ画面までもどる(タイマー画面はバックスタックから消す)
                    findNavController()
                        .navigate(
                            R.id.action_back_to_homeRecipeFragment)
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        //fab 監視処理
        viewModel.isMenuOpened.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            when(state) {
                MenuState.OPEN -> {
                    binding.wholeShadow.visibility = View.VISIBLE
                    binding.menuBtn.setImageResource(R.drawable.ic_baseline_close_24)
                    binding.saveBtn.isEnabled = true
                    binding.timeBtn.isEnabled = true
                    changeViewEnable(false)
                    AnimUtil.fadeInAnimation(binding.timeBtn, 500L)
                    AnimUtil.fadeInAnimation(binding.saveBtn, 500L)
                }
                MenuState.CLOSE -> {
                    binding.wholeShadow.visibility = View.GONE
                    binding.menuBtn.setImageResource(R.drawable.ic_baseline_menu_24)
                    binding.saveBtn.isEnabled = false
                    binding.timeBtn.isEnabled = false
                    changeViewEnable(true)
                    AnimUtil.fadeOutAnimation(binding.timeBtn, 500L)
                    AnimUtil.fadeOutAnimation(binding.saveBtn, 500L)

                    viewModel.resetMenuState()
                }
            }
        }

        // validate
        viewModel.tasteValidation.observe(viewLifecycleOwner) { validation ->
            setUpValidationMessage(validation, binding.scrollView, binding.header.root, binding.tasteValidateMessage, binding.tasteTitle)
        }
        viewModel.toolValidation.observe(viewLifecycleOwner) { validation ->
            setUpValidationMessage(validation, binding.scrollView, binding.header.root, binding.toolValidateMessage, binding.toolTitle)
        }
        viewModel.temperatureValidation.observe(viewLifecycleOwner) { validation ->
            setUpValidationMessage(validation, binding.scrollView, binding.header.root, binding.temperatureValidateMessage, binding.temperatureTitle)
        }
        viewModel.extractionTimeValidation.observe(viewLifecycleOwner) { validation ->
            setUpValidationMessage(validation, binding.scrollView, binding.header.root, binding.extractionTimeValidateMessage, binding.extractionTimeTitle)
        }
        viewModel.beanValidation.observe(viewLifecycleOwner) { validation ->
            setUpValidationMessage(validation, binding.scrollView, binding.header.root, binding.beanValidateMessage, binding.beanTitle)
        }

        // お気に入りアイコン
        binding.header.actionBtn.setOnClickListener {
            if (viewModel.isFavorite.value == true) viewModel.setFavoriteFlag(false)
            else viewModel.setFavoriteFlag(true)
        }
        // 蒸らし時間入力タイプ変更アイコン
        binding.changePreInfusionInputTypeIcon.setOnClickListener {
            if (viewModel.preInfusionTimeInputType.value == InputType.AUTO)
                viewModel.setPreInfusionTimeInputType(InputType.MANUAL)
            else
                viewModel.setPreInfusionTimeInputType(InputType.AUTO)
        }
        // 抽出時間入力タイプ変更アイコン
        binding.changeExtractionInputTypeIcon.setOnClickListener {
            if (viewModel.extractionTimeInputType.value == InputType.AUTO)
                viewModel.setExtractionTimeInputType(InputType.MANUAL)
            else
                viewModel.setExtractionTimeInputType(InputType.AUTO)
        }
        // ロースト選択ボタン
        binding.selectRoastBtn.setOnClickListener {
            val roastList: List<String> =
                LocalizationManager.getRoastList()
            ListDialogFragment
                .create(
                    viewModel.currentRoast.value!!,
                    getString(R.string.edit_roast),
                    "updateRoast",
                    roastList.toTypedArray())
                .show(childFragmentManager, ListDialogFragment::class.simpleName)
        }
        // グラインドサイズ選択ボタン
        binding.selectGrindBtn.setOnClickListener {
            val grindSizeList: List<String> =
                LocalizationManager.getGrindSizeList()

            ListDialogFragment
                .create(
                    viewModel.currentGrind.value!!,
                    getString(R.string.edit_grind),
                    "updateGrind",
                    grindSizeList.toTypedArray())
                .show(
                    childFragmentManager,
                    ListDialogFragment::class.simpleName
                )
        }
        // コーヒー豆選択ボタン クリックリスナ―
        binding.selectBeanBtn.setOnClickListener {
            viewModel.decideSelectBeanBtnAction()
        }
        // ★画像のクリックリスナーセット
        binding.starFirst.setOnClickListener(this)
        binding.starSecond.setOnClickListener(this)
        binding.starThird.setOnClickListener(this)
        binding.starFourth.setOnClickListener(this)
        binding.starFifth.setOnClickListener(this)
        // menuボタン
        binding.menuBtn.setOnClickListener {
            when(viewModel.isMenuOpened.value) {
                MenuState.OPEN -> viewModel.setMenuOpenedFlag(MenuState.CLOSE)
                MenuState.CLOSE -> viewModel.setMenuOpenedFlag(MenuState.OPEN)
                else -> viewModel.setMenuOpenedFlag(MenuState.OPEN)
            }
        }
        // タイマーボタン
        binding.timeBtn.setOnClickListener {
            viewModel.setMenuOpenedFlag(MenuState.CLOSE)

            val showTimerAction =
                NewRecipeFragmentDirections
                    .showTimerAction().apply {
                        existsNewRecipeFragment = true
                }
            findNavController().navigate(showTimerAction)
        }
        // 保存ボタン
        binding.saveBtn.setOnClickListener {
            BasicDialogFragment
                .create(
                    getString(R.string.create_recipe_title),
                    getString(R.string.create_recipe_message),
                    getString(R.string.save),
                    getString(R.string.cancel),
                    "createRecipe")
                .show(
                    childFragmentManager,
                    BasicDialogFragment::class.simpleName
                )
        }

        // TextChangeListener
        binding.sourValue.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setSour(editable.toString())
            }
        })
        binding.bitterValue.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setBitter(editable.toString())
            }
        })
        binding.sweetValue.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setSweet(editable.toString())
            }
        })
        binding.flavorValue.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setFlavor(editable.toString())
            }
        })
        binding.richValue.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setRich(editable.toString())
            }
        })
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

        // RoastDialogからの結果を受信
        childFragmentManager.setFragmentResultListener("updateRoast", viewLifecycleOwner) {_, bundle ->
            viewModel.setRoast(bundle.getInt("newIndex"))
        }
        // GrindDialogからの結果を受信
        childFragmentManager.setFragmentResultListener("updateGrind", viewLifecycleOwner) {_, bundle ->
            viewModel.setGrind(bundle.getInt("newIndex"))
        }
        // コーヒー豆未選択ダイアログからの結果を受信
        childFragmentManager.setFragmentResultListener("beanNotRegistered", viewLifecycleOwner) { _, _ ->
            viewModel.setSelectBeanBtnAction(SelectBeanBtnAction.SHOW_NEW_BEAN_FRAGMENT)
        }
        // 計測画面からの結果を受信
        setFragmentResultListener("returnFromTimer") { _, _ ->
            viewModel.setPreInfusionTimeInputType(InputType.AUTO)
            viewModel.setExtractionTimeInputType(InputType.AUTO)
        }
        // レシピ保存ダイアログからの結果を受信
        childFragmentManager.setFragmentResultListener("createRecipe", viewLifecycleOwner) { _, _ ->
            // validation
            if (viewModel.validateRecipeData(requireActivity(), mainViewModel.selectedBean.value)) {
                viewModel.setMenuOpenedFlag(MenuState.CLOSE)
                return@setFragmentResultListener
            }

            // 保存処理
            viewModel.createNewRecipeAndTaste(
                mainViewModel.selectedBean.value!!,
                mainViewModel.preInfusionTime.value!!,
                mainViewModel.extractionTime.value!!
            )
        }
        // コーヒー豆新規保存結果を受信
        setFragmentResultListener("createBean") { _, _ ->
            SnackBarUtil.showSimpleSnackBar(
                requireContext(),
                binding.snackBarPlace,
                getString(R.string.bean_finish_create_message)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // ★画像の共通クリックリスナー
    override fun onClick(starView: View) {
        when(starView.id) {
            R.id.starFirst  -> viewModel.updateRatingState(1)
            R.id.starSecond -> viewModel.updateRatingState(2)
            R.id.starThird  -> viewModel.updateRatingState(3)
            R.id.starFourth -> viewModel.updateRatingState(4)
            R.id.starFifth  -> viewModel.updateRatingState(5)
        }
    }

    private fun changeVisibility(type: InputType, manualView: View, autoView: View) {
        if (type == InputType.AUTO) {
            manualView.visibility = View.INVISIBLE
            autoView.visibility = View.VISIBLE
        } else {
            manualView.visibility = View.VISIBLE
            autoView.visibility = View.GONE
        }
    }

    private fun setUpHeader() {
        binding.header.headerTitle.text = getString(R.string.new_recipe)
        binding.header.backBtn.setOnClickListener {
            mainViewModel.resetBean()
            findNavController().popBackStack()
        }
    }

    // view の無効化
    private fun changeViewEnable(flag: Boolean) {
        // scroll有効無効
        ViewUtil.setScrollable(binding.scrollView, flag)

        binding.header.actionBtn.isEnabled = flag

        for (view in binding.scrollViewContainer.children) {
            val tag: String = (view.tag ?: "") as String
            if (tag.isEmpty()) continue

            if (tag == getString(R.string.change_enabled_tag)) {
                view.isEnabled = flag
            }
        }
        for (view in binding.extractionTimeWrapper.children) {
            view.isEnabled = flag
        }
    }
}