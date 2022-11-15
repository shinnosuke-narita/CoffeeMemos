package com.example.coffeememos.fragment

import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentTimerBinding
import com.example.coffeememos.state.InputType
import com.example.coffeememos.state.TimerButtonState
import com.example.coffeememos.state.TimerState
import com.example.coffeememos.viewModel.MainViewModel
import com.example.coffeememos.viewModel.TimerViewModel
import java.sql.Time

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

        viewModel.setNewRecipeExistsFlag(safeArgs.existsNewRecipeFragment)
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

        binding.showNewRecipeBtn.setOnClickListener { view ->
            if (viewModel.newRecipeFragmentExists.value!!) {
                // レシピ新規作成画面から開かれた場合
                setFragmentResult("returnFromTimer", Bundle())
                findNavController().popBackStack()
            } else {
                // bottomタブから開かれていた場合
                val showNewRecipeAction = TimerFragmentDirections.showNewRecipeAction().apply {
                    preInfusionTimeInputType = InputType.AUTO
                    extractionTimeInputType  = InputType.AUTO
                }
                Navigation.findNavController(view).navigate(showNewRecipeAction)
            }

        }
    }
}