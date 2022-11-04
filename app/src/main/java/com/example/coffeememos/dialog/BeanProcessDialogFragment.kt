package com.example.coffeememos.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.coffeememos.adapter.BeanProcessAdapter
import com.example.coffeememos.databinding.DialogFragmentBeanProcessBinding
import com.example.coffeememos.manager.ProcessListManager


class BeanProcessDialogFragment : DialogFragment() {
    private var _binding: DialogFragmentBeanProcessBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var processListManager: ProcessListManager

    companion object {
        fun create(currentProcess: Int): BeanProcessDialogFragment {
            return BeanProcessDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt("currentProcess", currentProcess)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFragmentBeanProcessBinding.inflate(requireActivity().layoutInflater)

        val dialog = Dialog(requireContext())
        dialog.setContentView(binding.root)

        // 背景を透過させる(dialogにセットした背景を見えるようにするため)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        arguments?.let { bundle ->
            // processListManager初期化
            processListManager = ProcessListManager(bundle.getInt("currentProcess"))
        }


        val processList: List<ProcessListManager.Process> = processListManager.createProcessList()

        // ListView セッティング
        binding.processListView.adapter = BeanProcessAdapter(requireContext(), processList)
        binding.processListView.setOnItemClickListener { _, _, selectedPosition, _ ->
            processListManager.setCurrentProcess(selectedPosition)
            val list = processListManager.createProcessList()

            binding.processListView.adapter = BeanProcessAdapter(requireContext(), list)
        }

        binding.updateBtn.setOnClickListener {
            setFragmentResult(
                "selectProcess",
                Bundle().apply {
                    putInt("process", processListManager.currentProcess)
                })

            dismiss()
        }

        // キャンセルボタンのリスナー処理
        binding.cancelBtn.setOnClickListener {
            // dialog を閉じる処理だがDialogFragmentの内部的にdismiss()が呼ばれるとFragmentを削除するようにリスナーがセットされている
            dismiss()
        }

        return dialog
    }
}