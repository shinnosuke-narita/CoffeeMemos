package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.coffeememos.manager.ChartManager
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentBeanDetailBinding
import com.example.coffeememos.databinding.FragmentRecipeDetailBinding
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.manager.RatingManager.*
import com.example.coffeememos.util.DateUtil
import com.example.coffeememos.viewModel.BeanDetailViewModel
import com.example.coffeememos.viewModel.BeanDetailViewModelFactory
import com.example.coffeememos.viewModel.RecipeDetailViewModel
import com.example.coffeememos.viewModel.RecipeDetailViewModelFactory

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
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBeanDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            binding.beanCardView.countryText.text = bean.country
            binding.beanCardView.farmText.text = bean.farm
            binding.beanCardView.districtText.text = bean.district
            binding.beanCardView.speciesText.text = bean.species
            binding.beanCardView.processText.text = Constants.processList[bean.process]
            binding.beanCardView.elevationText.text = getString(R.string.elevation_from_to, bean.elevationFrom.toString(), bean.elevationTo.toString())
            binding.beanCardView.storeText.text = bean.store
            binding.beanCardView.beanCommentText.text = bean.comment

            if (bean.isFavorite) binding.beanCardView.beanFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.beanCardView.beanFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
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