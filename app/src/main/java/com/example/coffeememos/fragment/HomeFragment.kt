package com.example.coffeememos.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.coffeememos.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {
    private var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // レシピ新規作成画面へ遷移
        view.findViewById<FloatingActionButton>(R.id.newRecipeBtn).setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.newRecipeFragment)
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }


}