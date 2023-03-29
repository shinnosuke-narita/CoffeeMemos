package com.withapp.coffeememo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.withapp.coffeememo.R
import com.withapp.coffeememo.databinding.FragmentTimerBinding
import com.withapp.coffeememo.state.InputType
import com.withapp.coffeememo.state.TimerButtonState
import com.withapp.coffeememo.state.TimerState
import com.withapp.coffeememo.viewModel.MainViewModel
import com.withapp.coffeememo.viewModel.TimerViewModel

class TimerFragment : Fragment() {
    private var _binding: FragmentTimerBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: TimerViewModel by viewModels()

    // 共有viewModel
    private val mainViewModel: MainViewModel by activityViewModels()

    private val safeArgs: TimerFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.setNewRecipeExistsFlag(safeArgs.existsNewRecipeFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // header セッティング
        binding.header.actionBtn.setImageResource(R.drawable.ic_baseline_create_24)
        binding.header.headerTitle.text = getString(R.string.timer)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        // タイマーの経過時間 監視処理
        viewModel.mainTimerTime.observe(viewLifecycleOwner) { currentTime ->
            binding.timerView.text = currentTime
        }

        // 抽出時間 監視処理
        mainViewModel.formattedExtractionTime.observe(viewLifecycleOwner) { extractionTime ->
            binding.extractionTime.text = extractionTime
        }


        //蒸らし時間 監視処理
        mainViewModel.formattedPreInfusionTime.observe(viewLifecycleOwner) { preInFusionTime ->
            binding.preInfusionTime.text = preInFusionTime
        }

        // タイマーの状態 監視処理
        viewModel.timerState.observe(viewLifecycleOwner) { state ->
            when(state) {
                TimerState.RUN -> {
                    binding.circleProgressBar.setProgressWithAnimation(100F)
                }
                TimerState.STOP -> {
                    binding.circleProgressBar.stopAnimation()
                    // 抽出時間の設定
                    mainViewModel.setExtractionTime(viewModel.currentTime.value!!)
                }
                TimerState.CLEAR -> {
                    binding.circleProgressBar.stopAnimation()
                    mainViewModel.resetTime()
                }
                else -> {
                    binding.circleProgressBar.stopAnimation()
                }
            }
        }

        // タイマーボタンの状態監視処理
        viewModel.mainBtnState.observe(viewLifecycleOwner) { btnState ->
            if (btnState == TimerButtonState.START) binding.mainBtnIcon.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            else binding.mainBtnIcon.setImageResource(R.drawable.ic_baseline_stop_24)
        }

        binding.mainBtn.setOnClickListener {
            if (viewModel.timerState.value == TimerState.RUN) viewModel.setTimerState(TimerState.STOP)
            else viewModel.setTimerState(TimerState.RUN)
        }

        binding.resetBtn.setOnClickListener {
            viewModel.setTimerState(TimerState.CLEAR)
        }

        binding.finishPreInfusionBtn.setOnClickListener {
            mainViewModel.setPreInfusionTime(viewModel.currentTime.value!!)
        }

        binding.header.actionBtn.setOnClickListener {
            if (mainViewModel.newRecipeFragmentExists.value!!) {
                // レシピ新規作成画面から開かれた場合
                setFragmentResult("returnFromTimer", Bundle())
                findNavController().popBackStack()
            } else {
                // bottomタブから開かれていた場合
                val showNewRecipeAction =
                    TimerFragmentDirections
                        .showNewRecipeAction().apply {
                    preInfusionTimeInputType = InputType.AUTO
                    extractionTimeInputType  = InputType.AUTO
                }
                findNavController().navigate(showNewRecipeAction)
            }
        }
    }
}