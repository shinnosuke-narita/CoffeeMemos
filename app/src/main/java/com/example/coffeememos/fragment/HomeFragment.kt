package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.R
import com.example.coffeememos.SimpleRecipe
import com.example.coffeememos.adapter.HomeViewPagerAdapter
import com.example.coffeememos.adapter.OnItemClickListener
import com.example.coffeememos.adapter.RecipeAdapter
import com.example.coffeememos.databinding.FragmentHomeBinding
import com.example.coffeememos.viewModel.HomeViewModel
import com.example.coffeememos.viewModel.HomeViewModelFactory


class HomeFragment : Fragment() {
    private var mContext: Context? = null

    private val fragmentList: List<Fragment> = listOf(HomeRecipeFragment(), HomeBeansFragment())

    // viewBinding
    private  var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        HomeViewModelFactory(db.beanDao())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
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

        binding.viewPager.adapter = HomeViewPagerAdapter(this, fragmentList)



//        // RecyclerView セットアップ
//        mContext?.let {
//            setUpRecyclerView(it, binding.recipeList)
//            setUpRecyclerView(it, binding.favoriteRecipeList)
//            setUpRecyclerView(it, binding.highRatingRecipeList)
//        }
//
//        // SimpleRecipeList 監視処理
//        viewModel.newRecipeList.observe(viewLifecycleOwner) { list ->
//            if (list.isEmpty()) return@observe
//
//            mContext?.let { context ->
//                binding.recipeList.adapter = RecipeAdapter(context, list).apply {
//                    setOnItemClickListener(object : OnItemClickListener<SimpleRecipe> {
//                        override fun onClick(view: View, recipe: SimpleRecipe) {
//                            Toast.makeText(mContext, recipe.country, Toast.LENGTH_SHORT).show()
//                        }
//                    })
//                }
//            }
//
//        }
//
//        viewModel.favoriteRecipeList.observe(viewLifecycleOwner) { favoriteList ->
//            mContext?.let { context ->
//                binding.favoriteRecipeList.adapter = RecipeAdapter(context, favoriteList).apply {
//                    setOnItemClickListener(object : OnItemClickListener<SimpleRecipe> {
//                        override fun onClick(view: View, recipe: SimpleRecipe) {
//                            Toast.makeText(mContext, recipe.country, Toast.LENGTH_SHORT).show()
//                        }
//                    })
//                }
//            }
//        }
//
//        viewModel.highRatingRecipeList.observe(viewLifecycleOwner) { highRatingList ->
//            mContext?.let { context ->
//                binding.highRatingRecipeList.adapter = RecipeAdapter(context, highRatingList).apply {
//                    setOnItemClickListener(object : OnItemClickListener<SimpleRecipe> {
//                        override fun onClick(view: View, recipe: SimpleRecipe) {
//                            Toast.makeText(mContext, recipe.country, Toast.LENGTH_SHORT).show()
//                        }
//                    })
//                }
//            }
//        }

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

    private fun setUpRecyclerView(context: Context, rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
    }

}