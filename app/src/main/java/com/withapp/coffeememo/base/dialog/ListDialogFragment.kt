package com.withapp.coffeememo.base.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.withapp.coffeememo.base.adapter.DialogDataAdapter
import com.withapp.coffeememo.databinding.DialogFragmentListBinding
import com.withapp.coffeememo.base.dialog.model.DialogDataHolder


class ListDialogFragment : DialogFragment() {
    private var _binding: DialogFragmentListBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var dataHolder: DialogDataHolder
    private lateinit var _titleName: String
    private lateinit var _key: String

    companion object {
        fun create(currentIndex: Int, title: String, keyName: String, data: Array<String>): ListDialogFragment {
            return ListDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt("currentIndex", currentIndex)
                    putString("title", title)
                    putString("key", keyName)
                    putStringArray("originData", data)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFragmentListBinding.inflate(requireActivity().layoutInflater)

        val dialog = Dialog(requireContext())
        dialog.setContentView(binding.root)

        // 背景を透過させる(dialogにセットした背景を見えるようにするため)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        arguments?.let { bundle ->
            // processListManager初期化
            bundle.getStringArray("originData")?.let{ data ->
                dataHolder = DialogDataHolder(bundle.getInt("currentIndex"), data)
            }
            _titleName = bundle.getString("title", "")
            _key       = bundle.getString("key", "")
        }


        binding.title.text = _titleName

        // ListView セッティング
        val data: List<DialogDataHolder.DialogData> = dataHolder.dialogData
        binding.listView.adapter = DialogDataAdapter(requireContext(), data)
        binding.listView.setOnItemClickListener { _, _, selectedPosition, _ ->
            dataHolder.updateProcessList(selectedPosition)

            binding.listView.adapter = DialogDataAdapter(requireContext(), dataHolder.dialogData)
        }

        binding.updateBtn.setOnClickListener {
            setFragmentResult(
                _key,
                Bundle().apply {
                    putInt("newIndex", dataHolder.currentIndex)
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