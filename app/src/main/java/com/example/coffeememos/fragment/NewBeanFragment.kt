package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentNewBeanBinding
import com.example.coffeememos.dialog.BasicDialogFragment
import com.example.coffeememos.dialog.ListDialogFragment
import com.example.coffeememos.listener.SimpleTextWatcher
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.viewModel.NewBeanViewModel
import com.example.coffeememos.viewModel.NewBeanViewModelFactory
import com.example.coffeememos.viewModel.NewRecipeViewModel
import com.example.coffeememos.viewModel.NewRecipeViewModelFactory
import com.google.android.material.snackbar.Snackbar


class NewBeanFragment : Fragment(), View.OnClickListener {
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
    ): View? {
        _binding = FragmentNewBeanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // header セッティング
        binding.header.headerTitle.text = getString(R.string.new_bean)
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
        //更新ダイアログの結果受信
        childFragmentManager.setFragmentResultListener("createBean", viewLifecycleOwner) { _, _ ->
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
            R.id.beanStarFirst  -> viewModel.updateRatingState(1)
            R.id.beanStarSecond -> viewModel.updateRatingState(2)
            R.id.beanStarThird  -> viewModel.updateRatingState(3)
            R.id.beanStarFourth -> viewModel.updateRatingState(4)
            R.id.beanStarFifth  -> viewModel.updateRatingState(5)
        }
    }

}