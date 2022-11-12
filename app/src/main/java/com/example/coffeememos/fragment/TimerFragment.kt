package com.example.coffeememos.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentTimerBinding
import com.example.coffeememos.viewModel.TimerViewModel

class TimerFragment : Fragment() {
    private var _binding: FragmentTimerBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: TimerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressView = binding.circleProgressBar

        // header セッティング
        binding.header.headerTitle.text = getString(R.string.timer)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.currentTime.observe(viewLifecycleOwner) { currentTime ->
            binding.timerView.text = currentTime.toString()
        }

        binding.startBtn.setOnClickListener {
            viewModel.start()
            binding.circleProgressBar.setProgressWithAnimation(100F)
        }

        binding.stopBtn.setOnClickListener {
            viewModel.stop()
            binding.circleProgressBar.stopAnimation()
        }

        binding.resetBtn.setOnClickListener {
            viewModel.reset()
        }
    }
}