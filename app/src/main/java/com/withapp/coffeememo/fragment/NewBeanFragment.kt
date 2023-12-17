package com.withapp.coffeememo.fragment

import android.app.ProgressDialog.show
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.withapp.coffeememo.CoffeeMemosApplication
import com.withapp.coffeememo.R
import com.withapp.coffeememo.core.ad_mob.locale.LocalizationManager
import com.withapp.coffeememo.databinding.FragmentNewBeanBinding
import com.withapp.coffeememo.dialog.BasicDialogFragment
import com.withapp.coffeememo.dialog.ListDialogFragment
import com.withapp.coffeememo.listener.SimpleTextWatcher
import com.withapp.coffeememo.manager.RatingManager
import com.withapp.coffeememo.viewModel.NewBeanViewModel
import com.withapp.coffeememo.viewModel.NewBeanViewModelFactory


class NewBeanFragment : BaseFragment(), View.OnClickListener {
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

    // Ratingの☆画像リスト
    lateinit var beanStarViewList: List<ImageView>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initialize(RatingManager())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewBeanBinding.inflate(inflater, container, false)

        // startViewList 初期化
        beanStarViewList = listOf(binding.starFirst, binding.starSecond, binding.starThird, binding.starFourth, binding.starFifth)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // header セッティング
        binding.header.headerTitle.text = getString(R.string.new_bean)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        /////////////////////
        // observe process //
        /////////////////////
        // お気に入り 監視処理
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) binding.header.actionBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.header.actionBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        // rating ★Viewの状態監視処理
        viewModel.beanStarList.observe(viewLifecycleOwner) { starList ->
            for ((index, star) in starList.withIndex()) {
                if (star.state == RatingManager.StarState.LIGHT) beanStarViewList[index].setImageResource(R.drawable.ic_baseline_star_beige_light_24)
                else beanStarViewList[index].setImageResource(R.drawable.ic_baseline_star_grey)
            }
        }
        // Ratingの値 監視処理
        viewModel.beanCurrentRating.observe(viewLifecycleOwner) { currentRating ->
            binding.beanRating.text = getString(R.string.rate_decimal, currentRating.toString())
        }
        // Process 関連処理
        viewModel.process.observe(viewLifecycleOwner) { process ->
            val processList: List<String> =
                LocalizationManager.getProcessList()

            binding.processEditText.text = processList[process]
        }
        // country validation
        viewModel.countryValidation.observe(viewLifecycleOwner) { validationInfo ->
            setUpValidationMessage(validationInfo, binding.scrollView, binding.header.root, binding.countryValidateMessage, binding.countryTitle)
        }

        ///////////////////
        // clickListener //
        ///////////////////
        // favorite
        binding.header.actionBtn.setOnClickListener {
            if (viewModel.isFavorite.value == true) viewModel.setFavoriteFlag(false)
            else viewModel.setFavoriteFlag(true)
        }
        // processDialog 表示
        binding.selectProcessBtn.setOnClickListener {
            val processList: List<String> =
                LocalizationManager.getProcessList()

            ListDialogFragment
                .create(
                    viewModel.process.value!!,
                    getString(R.string.edit_bean),
                    "updateProcess",
                    processList.toTypedArray())
                .show(
                    childFragmentManager,
                    ListDialogFragment::class.simpleName
                )
        }
        // ★画像のクリックリスナーセット
        binding.starFirst.setOnClickListener(this)
        binding.starSecond.setOnClickListener(this)
        binding.starThird.setOnClickListener(this)
        binding.starFourth.setOnClickListener(this)
        binding.starFifth.setOnClickListener(this)

        // 保存処理
        binding.saveBtn.setOnClickListener {
            BasicDialogFragment
                .create(
                    getString(R.string.create_bean_title),
                    getString(R.string.create_bean_message),
                    getString(R.string.save),
                    getString(R.string.cancel),
                    "createBean")
                .show(childFragmentManager, BasicDialogFragment::class.simpleName)
        }

        ////////////////////////
        // TextChangeListener //
        ////////////////////////
        binding.countryEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setCountry(editable.toString())
            }
        })
        binding.farmEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setFarm(editable.toString())
            }
        })
        binding.districtEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setDistrict(editable.toString())
            }
        })
        binding.speciesEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setSpecies(editable.toString())
            }
        })
        binding.elevationFromEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setElevationFrom(editable.toString())
            }
        })
        binding.elevationToEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setElevationTo(editable.toString())
            }
        })
        binding.storeEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setStore(editable.toString())
            }
        })
        binding.commentEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setComment(editable.toString())
            }
        })

        ////////////////////////////
        // FragmentResultListener //
        ////////////////////////////
        // processDialogからの結果を受信
        childFragmentManager.setFragmentResultListener("updateProcess", viewLifecycleOwner) {_, bundle ->
            viewModel.setProcess(bundle.getInt("newIndex"))
        }
        //更新ダイアログの結果受信
        childFragmentManager.setFragmentResultListener("createBean", viewLifecycleOwner) { _, _ ->
            // validation
            if (viewModel.validateBeanData(requireActivity())) return@setFragmentResultListener

            viewModel.createNewBean()

            setFragmentResult("createBean", Bundle())
            findNavController().popBackStack()
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
}