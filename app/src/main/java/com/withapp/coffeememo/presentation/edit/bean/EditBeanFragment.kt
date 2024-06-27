package com.withapp.coffeememo.presentation.edit.bean

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.withapp.coffeememo.R
import com.withapp.coffeememo.core.ad_mob.locale.LocalizationManager
import com.withapp.coffeememo.databinding.FragmentEditBeanBinding
import com.withapp.coffeememo.presentation.base.dialog.BasicDialogFragment
import com.withapp.coffeememo.presentation.base.dialog.ListDialogFragment
import com.withapp.coffeememo.presentation.base.fragment.BaseFragment
import com.withapp.coffeememo.presentation.base.text_watcher.SimpleTextWatcher
import com.withapp.coffeememo.entity.Rating
import com.withapp.coffeememo.presentation.state.ProcessState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditBeanFragment : BaseFragment(), View.OnClickListener {
    private var _binding: FragmentEditBeanBinding? = null
    private val binding
        get() = _binding!!

    // viewModel 初期化
    private val viewModel: EditBeanViewModel by viewModels()

    private val safeArgs: EditBeanFragmentArgs by navArgs()

    // ★画像のリスト
    private lateinit var beanStarViewList: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initialize(
            safeArgs.beanId,
            Rating()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBeanBinding.inflate(inflater, container, false)

        // ★画像のリスト初期化
        beanStarViewList = listOf( binding.beanStarFirst, binding.beanStarSecond, binding.beanStarThird, binding.beanStarFourth, binding.beanStarFifth)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // header セッティング
        binding.header.headerTitle.text =
            getString(R.string.edit_bean)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        /////////////////////
        // observe process //
        /////////////////////
        // 選択された豆の監視処理
        viewModel.selectedBean.observe(viewLifecycleOwner) { bean ->
            binding.countryEditText.setText(bean.country)
            binding.farmEditText.setText(bean.farm)
            binding.districtEditText.setText(bean.district)
            binding.speciesEditText.setText(bean.species)
            binding.elevationFromEditText.setText(bean.elevationFrom.toString())
            binding.elevationToEditText.setText(bean.elevationTo.toString())
            binding.storeEditText.setText(bean.store)
            binding.commentEditText.setText(bean.comment)
        }
        // country validation
        viewModel.countryValidation.observe(viewLifecycleOwner) { validationInfo ->
            setUpValidationMessage(
                validationInfo,
                binding.scrollView,
                binding.header.root,
                binding.countryValidateMessage,
                binding.countryTitle
            )
        }
        // rating ★Viewの状態監視処理
        viewModel.beanStarList.observe(viewLifecycleOwner) { starList ->
            for ((index, star) in starList.withIndex()) {
                if (star.state == Rating.StarState.LIGHT) beanStarViewList[index].setImageResource(
                    R.drawable.ic_baseline_star_beige_light_24
                )
                else beanStarViewList[index].setImageResource(R.drawable.ic_baseline_star_grey)
            }
        }
        // Ratingの値 監視処理
        viewModel.beanCurrentRating.observe(viewLifecycleOwner) { currentRating ->
            binding.beanRating.text = getString(
                R.string.rate_decimal,
                currentRating.toString()
            )
        }
        // Favorite
        viewModel.currentFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) binding.header.actionBtn.setImageResource(
                R.drawable.ic_baseline_favorite_24
            )
            else binding.header.actionBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        // Process
        viewModel.process.observe(viewLifecycleOwner) { process ->
            val processList: List<String> =
                LocalizationManager.getProcessList()

            binding.processEditText.text =
                processList[process]
        }
        // progressBar
        viewModel.processState.observe(viewLifecycleOwner) { state ->
            when (state) {
                ProcessState.BEFORE_PROCESSING -> {
                    binding.progressBar.visibility = View.GONE
                }
                ProcessState.PROCESSING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                ProcessState.FINISH_PROCESSING -> {
                    binding.progressBar.visibility = View.GONE

                    setFragmentResult(
                        "beanUpdate",
                        Bundle().apply {
                            putLong(
                                "beanId",
                                viewModel.selectedBean.value!!.id
                            )
                        }
                    )
                    findNavController().popBackStack()
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

            ////////////////////
            // click listener //
            ////////////////////
            // ★画像のクリックリスナーセット
            binding.beanStarFirst.setOnClickListener(this)
            binding.beanStarSecond.setOnClickListener(this)
            binding.beanStarThird.setOnClickListener(this)
            binding.beanStarFourth.setOnClickListener(this)
            binding.beanStarFifth.setOnClickListener(this)
            // favorite
            binding.header.actionBtn.setOnClickListener {
                if (viewModel.currentFavorite.value!!) viewModel.setFavorite(
                    false
                )
                else viewModel.setFavorite(true)
            }
            // processDialog 表示
            binding.selectProcessBtn.setOnClickListener {
                ListDialogFragment
                    .create(
                        viewModel.process.value!!,
                        getString(R.string.edit_bean),
                        "updateProcess",
                        LocalizationManager.getProcessList().toTypedArray()
                    )
                    .show(
                        childFragmentManager,
                        ListDialogFragment::class.simpleName
                    )
            }
            // 更新ダイアログ表示
            binding.saveBtn.setOnClickListener {
                BasicDialogFragment
                    .create(
                        getString(R.string.update_bean_title),
                        getString(R.string.update_message),
                        getString(R.string.update),
                        getString(R.string.cancel),
                        "updateBean"
                    )
                    .show(
                        childFragmentManager,
                        BasicDialogFragment::class.simpleName
                    )
            }

            ////////////////////////
            // TextChangeListener //
            ////////////////////////
            binding.countryEditText.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(editable: Editable?) {
                    viewModel.setCountry(editable.toString())
                }
            })
            binding.farmEditText.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(editable: Editable?) {
                    viewModel.setFarm(editable.toString())
                }
            })
            binding.districtEditText.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(editable: Editable?) {
                    viewModel.setDistrict(editable.toString())
                }
            })
            binding.speciesEditText.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(editable: Editable?) {
                    viewModel.setSpecies(editable.toString())
                }
            })
            binding.elevationFromEditText.addTextChangedListener(
                object : SimpleTextWatcher() {
                    override fun afterTextChanged(editable: Editable?) {
                        viewModel.setElevationFrom(editable.toString())
                    }
                })
            binding.elevationToEditText.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(editable: Editable?) {
                    viewModel.setElevationTo(editable.toString())
                }
            })
            binding.storeEditText.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(editable: Editable?) {
                    viewModel.setStore(editable.toString())
                }
            })
            binding.commentEditText.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(editable: Editable?) {
                    viewModel.setComment(editable.toString())
                }
            })

            ////////////////////////////
            // FragmentResultListener //
            ////////////////////////////
            // processDialogからの結果を受信
            childFragmentManager.setFragmentResultListener(
                "updateProcess",
                viewLifecycleOwner
            ) { _, bundle ->
                viewModel.setProcess(bundle.getInt("newIndex"))
            }
            //更新ダイアログの結果受信
            childFragmentManager.setFragmentResultListener(
                "updateBean",
                viewLifecycleOwner
            ) { _, _ ->
                // validation
                if (viewModel.validateBeanData(requireActivity())) return@setFragmentResultListener

                // 保存処理
                viewModel.updateBean()
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