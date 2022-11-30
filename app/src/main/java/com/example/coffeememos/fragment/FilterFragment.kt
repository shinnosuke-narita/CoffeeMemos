package com.example.coffeememos.fragment

import android.animation.ValueAnimator
import android.app.ActionBar
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentFilterBinding
import com.example.coffeememos.manager.SearchFilterManager
import com.example.coffeememos.state.MenuState
import com.example.coffeememos.utilities.AnimUtil
import com.example.coffeememos.viewModel.FilterViewModel
import com.example.coffeememos.viewModel.SearchRecipeViewModel

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: FilterViewModel by viewModels()

    private val parentViewModel: SearchRecipeViewModel by viewModels({ requireParentFragment() })

    private lateinit var filterManager: SearchFilterManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // フィルタリング管理マネージャー
        filterManager = parentViewModel.filterManager

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.countryMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) {
                setUpEditTextContainer()
                expandMenu(binding.countryContainer)
            }
            else {
                // キーボード非表示
                val inputMService = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMService.hideSoftInputFromWindow(binding.root.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                collapseMenu(binding.countryContainer, binding.filterElementContainer)
            }
        }
        viewModel.toolMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            if (state == MenuState.OPEN) {

            } else {

            }
        }
//        viewModel.ratingMenuState.observe(viewLifecycleOwner) { state ->
//            if (state == null) return@observe
//
//            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.ratingContainer)
//            else collapseMenu(binding.ratingContainer)
//        }
//        viewModel.sourMenuState.observe(viewLifecycleOwner) { state ->
//            if (state == null) return@observe
//
//            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.sourContainer)
//            else collapseMenu(binding.sourContainer)
//        }
//        viewModel.bitterMenuState.observe(viewLifecycleOwner) { state ->
//            if (state == null) return@observe
//
//            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.bitterContainer)
//            else collapseMenu(binding.bitterContainer)
//        }
//        viewModel.sweetMenuState.observe(viewLifecycleOwner) { state ->
//            if (state == null) return@observe
//
//            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.sweetContainer)
//            else collapseMenu(binding.sweetContainer)
//        }
//        viewModel.flavorMenuState.observe(viewLifecycleOwner) { state ->
//            if (state == null) return@observe
//
//            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.flavorContainer)
//            else collapseMenu(binding.flavorContainer)
//        }
//        viewModel.richMenuState.observe(viewLifecycleOwner) { state ->
//            if (state == null) return@observe
//
//            if (state == MenuState.OPEN) expandMenu(R.layout.filter_radio_button_layout, binding.richContainer)
//            else collapseMenu(binding.richContainer)
//        }


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

        binding.finishIcon.setOnClickListener {
            val inputText = binding.inputText.text.toString()

            // 絞り込み要素をinflate
            val itemView = layoutInflater.inflate(R.layout.filtered_element_text, null)
            itemView.findViewById<TextView>(R.id.valueText).text = inputText
            itemView.findViewById<ImageView>(R.id.deleteBtn).setOnClickListener {
                binding.filterElementContainer.removeView(itemView)
                filterManager.removeCountryValue(inputText)
            }

            binding.filterElementContainer.addView(itemView, 0, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))

            // filterManagerのデータ更新
            filterManager.addCountryValue(inputText)
        }

        // 閉じる処理
        binding.closeIcon.setOnClickListener {
            //parentViewModel.filterSearchResult()
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpEditTextContainer() {
        if (filterManager.countryValues.isNotEmpty()) {
            for (text in filterManager.countryValues) {
                val itemView = layoutInflater.inflate(R.layout.filtered_element_text, null)
                itemView.findViewById<TextView>(R.id.valueText).text = text
                itemView.findViewById<ImageView>(R.id.deleteBtn).setOnClickListener {
                    binding.filterElementContainer.removeView(itemView)
                    filterManager.removeCountryValue(text)
                }

                binding.filterElementContainer.addView(itemView, 0, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            }
        }
    }

    private fun expandMenu(containerView: View) {

        // viewの大きさを計測
        containerView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        val containerHeight: Int = containerView.measuredHeight

        containerView.layoutParams.height = 0
        val anim = ValueAnimator.ofInt(0, containerHeight).apply {
            addUpdateListener {
                val updateValue = it.animatedValue as Int

                if (updateValue == 0) {
                    containerView.visibility = View.VISIBLE
                }

                if (updateValue == containerHeight) {
                    containerView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    containerView.layoutParams.height = it.animatedValue as Int
                }

                containerView.requestLayout()
            }
            duration = 300
        }
        anim.start()
    }

    private fun collapseMenu(parentView: ViewGroup, filterElementView: ViewGroup) {
        ValueAnimator.ofInt(parentView.height, 0).apply {
            duration = 300
            addUpdateListener {

                if (it.animatedValue == 0) {
                    parentView.visibility = View.GONE
                    filterElementView.removeAllViews()
                }
                parentView.layoutParams.height = it.animatedValue as Int
                parentView.requestLayout()
            }
            start()
        }
    }
}
