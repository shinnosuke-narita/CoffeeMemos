package com.example.coffeememos.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.coffeememos.databinding.DialogFragmentBasicBinding

class BasicDialogFragment : DialogFragment() {
    private var _binding: DialogFragmentBasicBinding? = null
    private val binding
        get() = _binding!!

    private var _message: String = ""
    private var _positiveButtonText: String = ""
    private var _negativeButtonText: String = ""


    companion object {
        fun create(message: String, positiveButtonText: String, negativeButtonText: String): BasicDialogFragment =
            BasicDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("message", message)
                    putString("positiveButtonText", positiveButtonText)
                    putString("negativeButtonText", negativeButtonText)
                }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFragmentBasicBinding.inflate(requireActivity().layoutInflater)
        val dialog = Dialog(requireContext())

        // 背景を透過させる(dialogにセットした背景を見えるようにするため)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(binding.root)

        // プロパティの初期化
        arguments?.let { bundle ->
            _message            = bundle.getString("message", "")
            _positiveButtonText = bundle.getString("positiveButtonText", "")
            _negativeButtonText = bundle.getString("negativeButtonText", "")
        }

        // view のセッティング
        binding.message.text     = _message
        binding.positiveBtn.text = _positiveButtonText
        binding.negativeBtn.text = _negativeButtonText

        // ボタンのクリック処理
        binding.positiveBtn.setOnClickListener { view ->
            setFragmentResult(
                "isUpdate",
                Bundle().apply { putBoolean("isUpdate", true) }
            )

            dismiss()
        }

        binding.negativeBtn.setOnClickListener {
            dismiss()
        }



        return dialog
    }
}