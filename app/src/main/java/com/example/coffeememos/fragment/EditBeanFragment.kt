package com.example.coffeememos.fragment

import android.app.ProgressDialog.show
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentEditBeanBinding
import com.example.coffeememos.dialog.BeanProcessDialogFragment
import com.example.coffeememos.dialog.EditTasteDialogFragment
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.util.Util
import com.example.coffeememos.viewModel.EditBeanViewModel
import com.example.coffeememos.viewModel.EditBeanViewModelFactory
import com.example.coffeememos.viewModel.RecipeDetailViewModel
import com.example.coffeememos.viewModel.RecipeDetailViewModelFactory


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
        // Inflate the layout for this fragment
        _binding = FragmentEditBeanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val beanStarViewList: List<ImageView> = listOf(
            binding.beanStarFirst,
            binding.beanStarSecond,
            binding.beanStarThird,
            binding.beanStarFourth,
            binding.beanStarFifth,
        )

        // header セッティング
        binding.header.headerTitle.text = getString(R.string.edit_bean)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.selectedBean.observe(viewLifecycleOwner) { bean ->
            binding.countryEditText.setText(bean.country)
            binding.farmEditText.setText(bean.farm)
            binding.districtEditText.setText(bean.district)
            binding.speciesEditText.setText(bean.species)
            binding.elevationFromEditText.setText(bean.elevationFrom.toString())
            binding.elevationToEditText.setText(bean.elevationTo.toString())
            binding.storeEditText.setText(bean.store)
            binding.commentEditText.setText(bean.comment)
            binding.processEditText.text = Constants.processList[bean.process]

            if (bean.isFavorite) binding.header.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.header.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
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

        // ★画像のクリックリスナーセット
        binding.beanStarFirst.setOnClickListener(this)
        binding.beanStarSecond.setOnClickListener(this)
        binding.beanStarThird.setOnClickListener(this)
        binding.beanStarFourth.setOnClickListener(this)
        binding.beanStarFifth.setOnClickListener(this)

        // プロセス編集アイコンクリックリスナ―処理
        binding.selectProcessBtn.setOnClickListener {
            BeanProcessDialogFragment
                .create(viewModel.selectedBean.value!!.process)
                .show(childFragmentManager, BeanProcessDialogFragment::class.simpleName)
        }

        // processDialogからのリザルトを受け取る
        childFragmentManager.setFragmentResultListener("selectProcess", viewLifecycleOwner) {_, bundle ->
            binding.processEditText.text = Constants.processList[bundle.getInt("process")]
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