package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.R
import com.example.coffeememos.SimpleBeanInfo
import com.example.coffeememos.adapter.OnItemClickListener
import com.example.coffeememos.adapter.SimpleBeanInfoAdapter
import com.example.coffeememos.databinding.FragmentHomeBeansBinding
import com.example.coffeememos.viewModel.HomeBeanViewModel
import com.example.coffeememos.viewModel.HomeBeanViewModelFactory


class HomeBeansFragment : Fragment(), OnItemClickListener<SimpleBeanInfo> {
    private var mContext: Context? = null

    // viewBinding
    private var _binding: FragmentHomeBeansBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeBeanViewModel by viewModels {
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        HomeBeanViewModelFactory(db.beanDao())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
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

        // RecyclerView セットアップ
        mContext?.let {
            setUpRecyclerView(it, binding.newBeanList)
            setUpRecyclerView(it, binding.favoriteBeanList)
            setUpRecyclerView(it, binding.highRatingBeanList)
        }

        viewModel.simpleBeanInfoList.observe(viewLifecycleOwner) { list ->
            binding.beanTotalNum.text = list.size.toString()
        }

        viewModel.todayBeanList.observe(viewLifecycleOwner) { todayRecipeNum ->
            binding.todayBeanNum.text = todayRecipeNum.size.toString()
        }

        // SimpleRecipeList 監視処理
        viewModel.newBeanList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) return@observe

            mContext?.let { context ->
                binding.newBeanList.adapter = SimpleBeanInfoAdapter(context, list).apply {
                    setOnItemClickListener(this@HomeBeansFragment)
                }
            }
        }

        viewModel.favoriteBeanList.observe(viewLifecycleOwner) { favoriteList ->
            binding.favoriteBeanNum.text = favoriteList.size.toString()

            mContext?.let { context ->
                binding.favoriteBeanList.adapter = SimpleBeanInfoAdapter(context, favoriteList).apply {
                    setOnItemClickListener(this@HomeBeansFragment)
                }
            }
        }

        viewModel.highRatingBeanList.observe(viewLifecycleOwner) { highRatingList ->
            mContext?.let { context ->
                binding.highRatingBeanList.adapter = SimpleBeanInfoAdapter(context, highRatingList).apply {
                    setOnItemClickListener(this@HomeBeansFragment)
                }
            }
        }

        // 豆の新規作成画面に遷移
        binding.createBeanBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.newBeanFragment)
        }

        // レシピ画面に戻る
        binding.goToRecipeBtn.setOnClickListener { v ->
            findNavController().popBackStack()
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

    // BeanItem クリックリスナー
    override fun onClick(view: View, bean: SimpleBeanInfo) {
        val showDetailAction = HomeBeansFragmentDirections.showBeanDetailAction().apply {
            beanId = bean.id
        }

        Navigation.findNavController(view).navigate(showDetailAction)
    }

    private fun setUpRecyclerView(context: Context, rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
    }
}