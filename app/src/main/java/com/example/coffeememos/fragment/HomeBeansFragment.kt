package com.example.coffeememos.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentHomeBeansBinding
import com.example.coffeememos.databinding.FragmentNewBeanBinding


class HomeBeansFragment : Fragment() {
    // viewBinding
    private var _binding: FragmentHomeBeansBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBeansBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.newBeanFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}