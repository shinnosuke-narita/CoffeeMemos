package com.example.coffeememos.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.coffeememos.databinding.DialogFragmentEditTasteBinding

class EditTasteDialogFragment : DialogFragment(),  SeekBar.OnSeekBarChangeListener {
    private var sour  : Int = 0
    private var bitter: Int = 0
    private var sweet : Int = 0
    private var flavor: Int = 0
    private var rich  : Int = 0

    private var _binding: DialogFragmentEditTasteBinding? = null
    private val binding
        get() = _binding!!

    companion object {
        fun create(sour: Int, bitter: Int, sweet: Int, flavor: Int, rich: Int): EditTasteDialogFragment =
             EditTasteDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt("sour", sour)
                    putInt("bitter", bitter)
                    putInt("sweet", sweet)
                    putInt("flavor", flavor)
                    putInt("rich", rich)
                }
            }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFragmentEditTasteBinding.inflate(requireActivity().layoutInflater)
        val dialog = Dialog(requireContext())

        // 背景を透過させる(dialogにセットした背景を見えるようにするため)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // tasteプロパティの初期化処理処理
        arguments?.let { taste ->
            sour = taste.getInt("sour", 3)
            bitter = taste.getInt("bitter", 3)
            sweet = taste.getInt("sweet", 3)
            flavor = taste.getInt("flavor", 3)
            rich = taste.getInt("rich", 3)
        }

        // SeekBar セッティング
        binding.sourSeekBar.apply {
            progress = getProgress(sour)
            setOnSeekBarChangeListener(this@EditTasteDialogFragment)
        }
        binding.bitterSeekBar.apply {
            progress = getProgress(bitter)
            setOnSeekBarChangeListener(this@EditTasteDialogFragment)
        }
        binding.sweetSeekBar.apply {
            progress = getProgress(sweet)
            setOnSeekBarChangeListener(this@EditTasteDialogFragment)
        }
        binding.flavorSeekBar.apply {
            progress = getProgress(flavor)
            setOnSeekBarChangeListener(this@EditTasteDialogFragment)
        }
        binding.richSeekBar.apply {
            progress = getProgress(rich)
            setOnSeekBarChangeListener(this@EditTasteDialogFragment)
        }

        // tasteの数値セッティング
        binding.sourValues.text   = sour.toString()
        binding.bitterValues.text = bitter.toString()
        binding.sweetValues.text  = sweet.toString()
        binding.flavorValues.text = flavor.toString()
        binding.richValues.text   = rich.toString()

        // キャンセルボタンのリスナー処理
        binding.cancelBtn.setOnClickListener {
            // dialog を閉じる処理だがDialogFragmentの内部的にdismiss()が呼ばれるとFragmentを削除するようにリスナーがセットされている
            dismiss()
        }

        // 更新ボタンのリスナー処理
        binding.updateBtn.setOnClickListener {
            setFragmentResult(
                "newTaste",
                Bundle().apply {
                    putInt("sour", sour)
                    putInt("bitter", bitter)
                    putInt("sweet", sweet)
                    putInt("flavor", flavor)
                    putInt("rich", rich)
            })

            dismiss()
        }

        dialog.setContentView(binding.root)
        return dialog
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (!fromUser) return

        when(seekBar?.id) {
            binding.sourSeekBar.id   ->  {
                sour = setRealTasteValue(progress)
                binding.sourValues.text = sour.toString()
            }
            binding.bitterSeekBar.id ->  {
                bitter = setRealTasteValue(progress)
                binding.bitterValues.text = bitter.toString()
            }
            binding.sweetSeekBar.id  -> {
                sweet = setRealTasteValue(progress)
                binding.sweetValues.text = sweet.toString()
            }
            binding.flavorSeekBar.id   ->  {
                flavor = setRealTasteValue(progress)
                binding.flavorValues.text = flavor.toString()
            }
            binding.richSeekBar.id ->  {
                rich = setRealTasteValue(progress)
                binding.richValues.text = rich.toString()
            }
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {}
    override fun onStopTrackingTouch(p0: SeekBar?) {}

    // 1~5の値にするため
    private fun setRealTasteValue(progress: Int): Int = progress + 1

    // seekbarは0~4で値を表す
    private fun getProgress(tasteValue: Int): Int = tasteValue - 1
}