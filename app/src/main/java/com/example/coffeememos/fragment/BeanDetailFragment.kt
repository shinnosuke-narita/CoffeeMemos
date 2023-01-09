package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentBeanDetailBinding
import com.example.coffeememos.dialog.BasicDialogFragment
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.manager.RatingManager.*
import com.example.coffeememos.viewModel.BeanDetailViewModel
import com.example.coffeememos.viewModel.BeanDetailViewModelFactory
import com.google.android.material.snackbar.Snackbar

class BeanDetailFragment : Fragment() {
    private var mContext: Context? = null

    // viewBinding
    private  var _binding: FragmentBeanDetailBinding? = null
    private val binding
        get() = _binding!!

    // viewModel 初期化
    private val viewModel: BeanDetailViewModel by viewModels {
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        BeanDetailViewModelFactory(db.beanDao())
    }

    private val safeArgs: BeanDetailFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initialize(
            safeArgs.beanId,
            RatingManager()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBeanDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // header セッティング
        binding.header.headerTitle.text = getString(R.string.bean_detail)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        val beanStarViewList: List<ImageView> = listOf(
            binding.beanCardView.beanStarFirst,
            binding.beanCardView.beanStarSecond,
            binding.beanCardView.beanStarThird,
            binding.beanCardView.beanStarFourth,
            binding.beanCardView.beanStarFifth,
        )

        // Ratingの★画像状態 監視処理
        viewModel.beanStarList.observe(viewLifecycleOwner) { starList ->
            for ((index, star) in starList.withIndex()) {
                if (star.state == StarState.LIGHT) beanStarViewList[index].setImageResource(R.drawable.ic_baseline_star_beige_light_24)
                else beanStarViewList[index].setImageResource(R.drawable.ic_baseline_star_grey)
            }
        }

        // Ratingの値 監視処理
        viewModel.beanCurrentRating.observe(viewLifecycleOwner) { currentRating ->
            binding.beanCardView.beanRating.text = getString(R.string.rate_decimal, currentRating.toString())
        }

        viewModel.selectedBean.observe(viewLifecycleOwner) { bean ->
            binding.beanCardView.countryText.text     = bean.country
            binding.beanCardView.farmText.text        = bean.farm
            binding.beanCardView.districtText.text    = bean.district
            binding.beanCardView.speciesText.text     = bean.species
            binding.beanCardView.processText.text     = Constants.processList[bean.process]
            binding.beanCardView.elevationText.text   = getString(R.string.elevation_from_to, bean.elevationFrom.toString(), bean.elevationTo.toString())
            binding.beanCardView.storeText.text       = bean.store
            binding.beanCardView.beanCommentText.text = bean.comment

            if (bean.isFavorite) binding.beanCardView.beanFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.beanCardView.beanFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }


        // コーヒー豆編集画面 遷移
        binding.beanCardView.beanEditIcon.setOnClickListener { v ->
            val showEditBeanAction = BeanDetailFragmentDirections.showEditBeanAction().apply {
                beanId = viewModel.selectedBean.value!!.id
            }
            Navigation.findNavController(v).navigate(showEditBeanAction)
        }
        // コーヒー豆編集結果受信
        setFragmentResultListener("beanUpdate") { _, bundle ->
            viewModel.updateBean(bundle.getLong("beanId"))

            Snackbar.make(binding.snackBarPlace, getString(R.string.bean_finish_update_message), Snackbar.LENGTH_SHORT).apply {
                mContext?.let {
                    setTextColor(ContextCompat.getColor(it, R.color.snackBar_text))
                    getView().setBackgroundColor(
                        ContextCompat.getColor(it,
                            R.color.white
                        ))
                }
            }.show()
        }


        // 削除処理
        binding.deleteBtn.setOnClickListener { view ->
            BasicDialogFragment
                .create(
                    getString(R.string.delete_bean_title),
                    getString(R.string.delete_bean_message),
                    getString(R.string.delete),
                    getString(R.string.cancel),
                    "deleteBean")
                .show(childFragmentManager, BasicDialogFragment::class.simpleName)
        }
        //削除ダイアログの結果受信
        childFragmentManager.setFragmentResultListener("deleteBean", viewLifecycleOwner) { _, _ ->
            viewModel.deleteRecipe()

            setFragmentResult("deleteBean", Bundle())
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
}