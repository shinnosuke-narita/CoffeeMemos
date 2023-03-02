package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.R
import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeInfo
import com.example.coffeememos.listener.OnItemClickListener
import com.example.coffeememos.adapter.RecipeAdapter
import com.example.coffeememos.databinding.FragmentHomeRecipeBinding
import com.example.coffeememos.listener.OnFavoriteIconClickListener
import com.example.coffeememos.viewModel.HomeRecipeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeRecipeFragment : Fragment(),
    OnItemClickListener<HomeRecipeInfo>,
    OnFavoriteIconClickListener<HomeRecipeInfo> {
    private var mContext: Context? = null

    // viewBinding
    private  var _binding: FragmentHomeRecipeBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: HomeRecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewModel 初期化
       // viewModel.initialize()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView セットアップ
        mContext?.let {
            setUpRecyclerView(it, binding.newRecipeList)
            setUpRecyclerView(it, binding.favoriteRecipeList)
            setUpRecyclerView(it, binding.highRatingRecipeList)
        }

        // 今日作成レシピ数
        viewModel.todayRecipeCount.observe(viewLifecycleOwner) { todayRecipeNum ->
            binding.todayRecipeNum.text = todayRecipeNum.toString()
        }

        // お気に入りレシピ数
        viewModel.favoriteRecipeCount.observe(viewLifecycleOwner) { favoriteRecipeNum ->
            binding.favoriteRecipeNum.text = favoriteRecipeNum.toString()
        }

        // SimpleRecipeList 監視処理
        viewModel.newRecipes.observe(viewLifecycleOwner) { list ->
            mContext?.let { context ->
                binding.newRecipeList.adapter = RecipeAdapter(context, list).apply {
                    setOnItemClickListener(this@HomeRecipeFragment)
                    setFavoriteListener(this@HomeRecipeFragment)
                }
            }
        }

        viewModel.favoriteRecipes.observe(viewLifecycleOwner) { favoriteList ->
            mContext?.let { context ->
                binding.favoriteRecipeList.adapter =
                    RecipeAdapter(context, favoriteList).apply {
                        setOnItemClickListener(this@HomeRecipeFragment)
                        setFavoriteListener(this@HomeRecipeFragment)
                }
            }
        }

        viewModel.highRatingRecipes.observe(viewLifecycleOwner) { highRatingList ->
            mContext?.let { context ->
                binding.highRatingRecipeList.adapter = RecipeAdapter(context, highRatingList).apply {
                    setOnItemClickListener(this@HomeRecipeFragment)
                    setFavoriteListener(this@HomeRecipeFragment)
                }
            }
        }

        // レシピの登録数 監視処理
        viewModel.totalRecipeCount.observe(viewLifecycleOwner) { recipeNum ->
            binding.recipeTotalNum.text = recipeNum.toString()

            if (recipeNum == 0) {
                binding.newRecipeHeader.visibility = View.GONE
                binding.highRatingRecipeHeader.visibility = View.GONE
                binding.favoriteRecipeHeader.visibility = View.GONE
            } else {
                binding.newRecipeHeader.visibility = View.VISIBLE
                binding.highRatingRecipeHeader.visibility = View.VISIBLE
                binding.favoriteRecipeHeader.visibility = View.VISIBLE
            }
        }

        // レシピ新規作成画面へ遷移
        binding.createRecipeBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.newRecipeFragment)
        }

        // 豆ホーム画面へ遷移
        binding.goToBeanBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.homeBeansFragment)
        }

        setFragmentResultListener("deleteRecipe") { _, _ ->
            Snackbar.make(binding.snackBarPlace, getString(R.string.recipe_finish_delete_message), Snackbar.LENGTH_SHORT).apply {
                mContext?.let {
                    setTextColor(ContextCompat.getColor(it, R.color.delete_color))
                    getView().setBackgroundColor(
                        ContextCompat.getColor(it,
                            R.color.white
                        ))
                }
            }.show()
        }

        setFragmentResultListener("createRecipe") {_, _ ->
            Snackbar.make(binding.snackBarPlace, getString(R.string.recipe_finish_save_message), Snackbar.LENGTH_SHORT).apply {
                mContext?.let {
                    setTextColor(ContextCompat.getColor(it, R.color.snackBar_text))
                    getView().setBackgroundColor(
                        ContextCompat.getColor(it,
                            R.color.white
                        ))
                }
            }.show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initialize()
        // 更新処理
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    // RecipeItem クリックリスナ―
    override fun onClick(view: View, selectedItem: HomeRecipeInfo) {
        // todo ここでtasteIdを渡す必要はない recipeIdだけ渡して、RecipeDetail側でtasteIDを取得すればよい
        val showDetailAction = HomeRecipeFragmentDirections.showRecipeDetailAction().apply {
            recipeId = selectedItem.recipeId
            beanId   = selectedItem.beanId
        }

        Navigation.findNavController(view).navigate(showDetailAction)
    }

    // お気に入りアイコン クリックリスナ―
    override fun onFavoriteClick(view: View, data: HomeRecipeInfo) {
        viewModel.updateFavoriteIcon(data)
    }


    private fun setUpRecyclerView(context: Context, rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
    }
}