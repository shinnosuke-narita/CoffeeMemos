package com.example.coffeememos.fragment

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentFilterBinding
import com.example.coffeememos.state.MenuState
import com.example.coffeememos.utilities.AnimUtil
import com.example.coffeememos.viewModel.FilterViewModel

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: FilterViewModel by viewModels()

    private lateinit var wrapperList: List<ViewGroup>

    private lateinit var containerView: View
    var flag = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)

        // viewの順番に合わせる
        wrapperList = listOf(
            binding.countryWrapper,
            binding.toolWrapper,
            binding.ratingWrapper,
            binding.sourWrapper,
            binding.bitterWrapper,
            binding.sweetWrapper,
            binding.sweetWrapper,
            binding.flavorWrapper,
            binding.richWrapper
        )

        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.countryMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) expandMenu(R.layout.filter_edit_text_layout, binding.countryContainer)
            else collapseMenu(binding.countryContainer)
        }

        viewModel.toolMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) expandMenu(R.layout.filter_edit_text_layout, binding.toolContainer)
            else collapseMenu(binding.toolContainer)
        }

        viewModel.ratingMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.ratingContainer)
            else collapseMenu(binding.ratingContainer)
        }

        viewModel.sourMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.sourContainer)
            else collapseMenu(binding.sourContainer)
        }

        viewModel.bitterMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.bitterContainer)
            else collapseMenu(binding.bitterContainer)
        }

        viewModel.sweetMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.sweetContainer)
            else collapseMenu(binding.sweetContainer)
        }

        viewModel.flavorMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.flavorContainer)
            else collapseMenu(binding.flavorContainer)
        }

        viewModel.richMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.richContainer)
            else collapseMenu(binding.richContainer)
        }




        binding.countryWrapper.setOnClickListener {
            val currentState = viewModel.countryMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setCountryState(MenuState.CLOSE)
            else viewModel.setCountryState(MenuState.OPEN)

            viewModel.updateMenuState(binding.countryContainer, requireActivity())
        }

        binding.toolWrapper.setOnClickListener {
            val currentState = viewModel.toolMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setToolState(MenuState.CLOSE)
            else viewModel.setToolState(MenuState.OPEN)

            viewModel.updateMenuState(binding.toolContainer, requireActivity())
        }

        binding.ratingWrapper.setOnClickListener {
            val currentState = viewModel.ratingMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setRatingState(MenuState.CLOSE)
            else viewModel.setRatingState(MenuState.OPEN)

            viewModel.updateMenuState(binding.ratingContainer, requireActivity())
        }

        binding.sourWrapper.setOnClickListener {
            val currentState = viewModel.sourMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setSourState(MenuState.CLOSE)
            else viewModel.setSourState(MenuState.OPEN)

            viewModel.updateMenuState(binding.sourContainer, requireActivity())
        }

        binding.bitterWrapper.setOnClickListener {
            val currentState = viewModel.bitterMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setBitterState(MenuState.CLOSE)
            else viewModel.setBitterState(MenuState.OPEN)

            viewModel.updateMenuState(binding.bitterContainer, requireActivity())
        }

        binding.sweetWrapper.setOnClickListener {
            val currentState = viewModel.sweetMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setSweetState(MenuState.CLOSE)
            else viewModel.setSweetState(MenuState.OPEN)

            viewModel.updateMenuState(binding.sweetContainer, requireActivity())
        }

        binding.flavorWrapper.setOnClickListener {
            val currentState = viewModel.flavorMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setFlavorState(MenuState.CLOSE)
            else viewModel.setFlavorState(MenuState.OPEN)

            viewModel.updateMenuState(binding.flavorContainer, requireActivity())
        }

        binding.richWrapper.setOnClickListener {
            val currentState = viewModel.richMenuState.value ?: MenuState.CLOSE

            if (currentState == MenuState.OPEN) viewModel.setRichState(MenuState.CLOSE)
            else viewModel.setRichState(MenuState.OPEN)

            viewModel.updateMenuState(binding.richContainer, requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun expandMenu(resId: Int, parentView: ViewGroup) {
        val containerView = layoutInflater.inflate(resId, null)

        // viewの大きさを計測
        containerView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        val containerHeight: Int = containerView.measuredHeight

        parentView.addView(containerView)
        containerView.layoutParams.height = 1

        ValueAnimator.ofInt(0, containerHeight).apply {
            duration = 300
            addUpdateListener {
                parentView.getChildAt(0).layoutParams.height = it.animatedValue as Int
                parentView.getChildAt(0).requestLayout()
            }
            start()
        }
    }

    private fun collapseMenu(parentView: ViewGroup) {
        ValueAnimator.ofInt(parentView.height, 0).apply {
            duration = 300
            addUpdateListener {
                parentView.getChildAt(0).layoutParams.height = it.animatedValue as Int
                parentView.getChildAt(0).requestLayout()
            }
            addListener {
                doOnEnd {
                    parentView.removeAllViews()
                }
            }
            start()
        }
    }
}
