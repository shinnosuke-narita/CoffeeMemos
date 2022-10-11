package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.R
import com.example.coffeememos.SimpleRecipe
import com.example.coffeememos.adapter.OnItemClickListener
import com.example.coffeememos.adapter.RecipeAdapter
import com.example.coffeememos.databinding.FragmentHomeBinding
import com.example.coffeememos.viewModel.HomeViewModel
import com.example.coffeememos.viewModel.HomeViewModelFactory


class HomeFragment : Fragment() {
    private var mContext: Context? = null

    // viewBinding
    private  var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var viewModel: HomeViewModel

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
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        viewModel = HomeViewModelFactory(db.beanDao()).create(HomeViewModel::class.java)

        // RecyclerView セットアップ
        val rv = view.findViewById<RecyclerView>(R.id.recipeList)
        rv.layoutManager = LinearLayoutManager(mContext).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        // SimpleRecipeList 監視処理
        viewModel.simpleRecipeList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) return@observe

            rv.adapter = RecipeAdapter(list).apply {
                setOnItemClickListener(object : OnItemClickListener<SimpleRecipe> {
                    override fun onClick(view: View, recipe: SimpleRecipe) {
                        Toast.makeText(mContext, recipe.country, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        // レシピ新規作成画面へ遷移
        binding.newRecipeBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.newRecipeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onDetach() {
        super.onDetach()
        mContext = null
    }


}