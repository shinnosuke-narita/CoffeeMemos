package com.withapp.coffeememo.presentation.copyright.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.withapp.coffeememo.R
import com.withapp.coffeememo.presentation.copyright.adapter.CopyRightAdapter
import com.withapp.coffeememo.databinding.FragmentCopyRightBinding

class CopyRightFragment : Fragment() {
    // viewBinding
    private  var _binding: FragmentCopyRightBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CopyRightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentCopyRightBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setUpHeader()
        setUpAdapter(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpHeader() {
        // header セッティング
        binding.header.headerTitle.text =
            getString(R.string.copy_right_header)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpAdapter(context: Context) {
        binding.copyRightList.layoutManager =
            LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }

        binding.copyRightList.adapter =
            CopyRightAdapter(
                viewModel.copyRightInfoList
            ) { url ->
                try {
                    val uri = Uri.parse(url)

                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }
}