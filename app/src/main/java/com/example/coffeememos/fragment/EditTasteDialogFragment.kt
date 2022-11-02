package com.example.coffeememos.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
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
    val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFragmentEditTasteBinding.inflate(requireActivity().layoutInflater)
        val dialog = Dialog(requireContext())

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // TODO シークバーの初期化処理処理


        // seekbar リスナーセット
        binding.sourSeekBar.setOnSeekBarChangeListener(this@EditTasteDialogFragment)
        binding.bitterSeekBar.setOnSeekBarChangeListener(this@EditTasteDialogFragment)
        binding.sweetSeekBar.setOnSeekBarChangeListener(this@EditTasteDialogFragment)
        binding.flavorSeekBar.setOnSeekBarChangeListener(this@EditTasteDialogFragment)
        binding.richSeekBar.setOnSeekBarChangeListener(this@EditTasteDialogFragment)

        // TODO キャンセルボタンのリスナー処理
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        // TODO 更新ボタンのリスナー処理
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
        }



        dialog.setContentView(binding.root)
        return dialog
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (!fromUser) return

        when(seekBar?.id) {
            binding.sourSeekBar.id   ->  {
                sour = convertProgressValue(progress)
                binding.sourValues.text = sour.toString()
            }
            binding.bitterSeekBar.id ->  {
                bitter = convertProgressValue(progress)
                binding.bitterValues.text = bitter.toString()
            }
            binding.sweetSeekBar.id  -> {
                sweet = convertProgressValue(progress)
                binding.sweetValues.text = sweet.toString()
            }
            binding.richSeekBar.id   ->  {
                flavor = convertProgressValue(progress)
                binding.flavorValues.text = flavor.toString()
            }
            binding.flavorSeekBar.id ->  {
                rich = convertProgressValue(progress)
                binding.richValues.text = rich.toString()
            }
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {}
    override fun onStopTrackingTouch(p0: SeekBar?) {}

    // 1~5の値にするため
    private fun convertProgressValue(progress: Int): Int = progress + 1

}