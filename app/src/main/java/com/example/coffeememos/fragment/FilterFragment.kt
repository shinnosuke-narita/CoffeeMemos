package com.example.coffeememos.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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

        viewModel.makeMenuInfoList(wrapperList)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        // 隠れたメニューの高さ取得
//        binding.countryContainer.root.post {
//            viewModel.inputTextContainerHeight = binding.countryContainer.root.height.toFloat()
//        }
//        // 隠れたメニューの高さ取得
//        binding.ratingContainer.root.post {
//            viewModel.radioGroupContainerHeight = binding.ratingContainer.root.height.toFloat()
//        }


        viewModel.countryMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe


            if (state == MenuState.OPEN) {
                val containerView = layoutInflater.inflate(R.layout.filter_edit_text_layout, binding.countryContainer)

                containerView.findViewById<EditText>(R.id.countryEditText).setOnClickListener {
                    Log.d("testtest", "Hello World")
                }
            } else {
                binding.countryContainer.removeAllViews()
            }



//            val clickedIndex: Int = wrapperList.indexOf(binding.countryWrapper)
//
//            if (state == MenuState.OPEN) {
//                for ((index, targetView) in wrapperList.withIndex()) {
//                    if (index <= clickedIndex) continue
//
////                    AnimUtil.fadeInAnimation(binding.countryContainer.root, 500L)
////                    AnimUtil.translateYAnimation(
////                        targetView,
////                        300L,
////                        0f,
////                        viewModel.inputTextContainerHeight
////                    )
//                }
//                viewModel.updateMenuInfoList(binding.countryWrapper.id, MenuState.OPEN)
//            } else {
//                for ((index, targetView) in wrapperList.withIndex()) {
//                    if (index <= clickedIndex) continue
//
////                    AnimUtil.fadeOutAnimation(binding.countryContainer.root, 100L)
////                    AnimUtil.translateYAnimation(
////                        targetView,
////                        300L,
////                        viewModel.inputTextContainerHeight,
////                        0f
////                    )
//                }
//                viewModel.updateMenuInfoList(binding.countryWrapper.id, MenuState.CLOSE)
//            }
        }

        viewModel.toolMenuState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            val clickedIndex: Int = wrapperList.indexOf(binding.toolWrapper)

            val openContainerHeight = viewModel.calculateOpenContainerHeight(clickedIndex)
            val toolContainerHeight = viewModel.inputTextContainerHeight

            if (state == MenuState.OPEN) {
                for ((index, targetView) in wrapperList.withIndex()) {
                    if (index <= clickedIndex) continue

                    AnimUtil.fadeInAnimation(binding.toolContainer.root, 500L)
                    AnimUtil.translateYAnimation(binding.toolContainer.root, 300L, 0f, openContainerHeight)
                    AnimUtil.translateYAnimation(
                        targetView,
                        300L,
                        openContainerHeight,
                        openContainerHeight + toolContainerHeight
                    )


                }
            } else {
                for ((index, targetView) in wrapperList.withIndex()) {
                    if (index <= clickedIndex) continue

                    AnimUtil.fadeOutAnimation(binding.toolContainer.root, 100L)
                    AnimUtil.translateYAnimation(
                        targetView,
                        300L,
                        openContainerHeight + toolContainerHeight,
                        openContainerHeight
                    )
                }
            }
        }




        binding.countryWrapper.setOnClickListener {
            val currentMenuState: MenuState = viewModel.countryMenuState.value ?: MenuState.CLOSE

            if (currentMenuState == MenuState.OPEN) {
                viewModel.setCountryState(MenuState.CLOSE)
            } else {
                viewModel.setCountryState(MenuState.OPEN)
            }




        }

        binding.toolWrapper.setOnClickListener {
            val currentMenuState: MenuState = viewModel.toolMenuState.value ?: MenuState.CLOSE

            if (currentMenuState == MenuState.OPEN) {
                viewModel.setToolState(MenuState.CLOSE)
            } else {
                viewModel.setToolState(MenuState.OPEN)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
