package com.example.coffeememos.fragment

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentEditBeanBinding
import com.example.coffeememos.dialog.BasicDialogFragment
import com.example.coffeememos.dialog.ListDialogFragment
import com.example.coffeememos.listener.SimpleTextWatcher
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.viewModel.EditBeanViewModel
import com.example.coffeememos.viewModel.EditBeanViewModelFactory


class EditBeanFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentEditBeanBinding? = null
    private val binding
        get() = _binding!!

    // viewModel 初期化
    private val viewModel: EditBeanViewModel by viewModels {
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        EditBeanViewModelFactory(db.beanDao())
    }

    private val safeArgs: EditBeanFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initialize(
            safeArgs.beanId,
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
        _binding = FragmentEditBeanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // header セッティング
        binding.header.headerTitle.text = getString(R.string.edit_bean)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }


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


        // ★画像のリスト
        val beanStarViewList: List<ImageView> = listOf(
            binding.beanStarFirst,
            binding.beanStarSecond,
            binding.beanStarThird,
            binding.beanStarFourth,
            binding.beanStarFifth,
        )
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
        // ★画像のクリックリスナーセット
        binding.beanStarFirst.setOnClickListener(this)
        binding.beanStarSecond.setOnClickListener(this)
        binding.beanStarThird.setOnClickListener(this)
        binding.beanStarFourth.setOnClickListener(this)
        binding.beanStarFifth.setOnClickListener(this)


        // TextChangeListener セット
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



        // Favorite 関連処理
        viewModel.currentFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) binding.header.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.header.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        binding.header.favoriteBtn.setOnClickListener {
            if (viewModel.currentFavorite.value!!) viewModel.setFavorite(false)
            else viewModel.setFavorite(true)
        }


        // Process 関連処理
        viewModel.process.observe(viewLifecycleOwner) { process ->
            binding.processEditText.text = Constants.processList[process]
        }
        // processDialog 表示
        binding.selectProcessBtn.setOnClickListener {
            ListDialogFragment
                .create(viewModel.process.value!!, getString(R.string.edit_bean), "updateProcess", Constants.processList.toTypedArray())
                .show(childFragmentManager, ListDialogFragment::class.simpleName)
        }
        // processDialogからの結果を受信
        childFragmentManager.setFragmentResultListener("updateProcess", viewLifecycleOwner) {_, bundle ->
            viewModel.setProcess(bundle.getInt("newIndex"))
        }


        // 更新ダイアログ表示
        binding.saveBtn.setOnClickListener {
            BasicDialogFragment
                .create(
                    getString(R.string.update_message),
                    getString(R.string.update),
                    getString(R.string.cancel),
                "updateBean")
                .show(childFragmentManager, BasicDialogFragment::class.simpleName)
        }
        //更新ダイアログの結果受信
        childFragmentManager.setFragmentResultListener("updateBean", viewLifecycleOwner) { _, _ ->
            viewModel.updateBean()

            setFragmentResult("beanUpdate", Bundle().apply { putLong("beanId", viewModel.selectedBean.value!!.id) })
            findNavController().popBackStack()
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