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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.R
import com.example.coffeememos.SimpleBeanInfo
import com.example.coffeememos.listener.OnItemClickListener
import com.example.coffeememos.adapter.SimpleBeanInfoAdapter
import com.example.coffeememos.databinding.FragmentHomeBeansBinding
import com.example.coffeememos.listener.OnFavoriteIconClickListener
import com.example.coffeememos.viewModel.HomeBeanViewModel
import com.google.android.material.snackbar.Snackbar


class HomeBeansFragment : Fragment(),
    OnItemClickListener<SimpleBeanInfo>,
    OnFavoriteIconClickListener<SimpleBeanInfo> {

    private var mContext: Context? = null

    // viewBinding
    private var _binding: FragmentHomeBeansBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeBeanViewModel by viewModels {
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        HomeBeanViewModel.HomeBeanViewModelFactory(db.beanDao())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBeansBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // header セッティング
        binding.header.headerTitle.text = getString(R.string.home_bean)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        // RecyclerView セットアップ
        mContext?.let {
            setUpRecyclerView(it, binding.newBeanList)
            setUpRecyclerView(it, binding.favoriteBeanList)
            setUpRecyclerView(it, binding.highRatingBeanList)
        }

        // 総レシピ数 監視処理
        viewModel.simpleBeanInfoList.observe(viewLifecycleOwner) { list ->
            binding.beanTotalNum.text = list.size.toString()
        }

        // 今日作成したレシピ数 監視処理
        viewModel.todayBeanList.observe(viewLifecycleOwner) { todayRecipeNum ->
            binding.todayBeanNum.text = todayRecipeNum.size.toString()
        }

        // 新しい順レシピ 監視処理
        viewModel.newBeanList.observe(viewLifecycleOwner) { list ->
            mContext?.let { context ->
                binding.newBeanList.adapter = SimpleBeanInfoAdapter(context, list).apply {
                    setOnItemClickListener(this@HomeBeansFragment)
                    setFavoriteListener(this@HomeBeansFragment)
                }
            }
        }

        // お気に入りレシピ 監視処理
        viewModel.favoriteBeanList.observe(viewLifecycleOwner) { favoriteList ->
            binding.favoriteBeanNum.text = favoriteList.size.toString()

            mContext?.let { context ->
                binding.favoriteBeanList.adapter = SimpleBeanInfoAdapter(context, favoriteList).apply {
                    setOnItemClickListener(this@HomeBeansFragment)
                    setFavoriteListener(this@HomeBeansFragment)
                }
            }
        }

        // レイティング順レシピ 監視処理
        viewModel.highRatingBeanList.observe(viewLifecycleOwner) { highRatingList ->
            mContext?.let { context ->
                binding.highRatingBeanList.adapter = SimpleBeanInfoAdapter(context, highRatingList).apply {
                    setOnItemClickListener(this@HomeBeansFragment)
                    setFavoriteListener(this@HomeBeansFragment)
                }
            }
        }

        // コーヒー豆の登録数 監視処理
        viewModel.beanCountIsZero.observe(viewLifecycleOwner) { countIsZero ->
            if (countIsZero) {
                binding.newBeanHeader.visibility = View.GONE
                binding.highRatingBeanHeader.visibility = View.GONE
                binding.favoriteBeanHeader.visibility = View.GONE
            } else {
                binding.newBeanHeader.visibility = View.VISIBLE
                binding.highRatingBeanHeader.visibility = View.VISIBLE
                binding.favoriteBeanHeader.visibility = View.VISIBLE
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


        // 削除処理のリスナー
        setFragmentResultListener("deleteBean") { _, _ ->
            Snackbar.make(binding.snackBarPlace, getString(R.string.bean_finish_delete_message), Snackbar.LENGTH_SHORT).apply {
                mContext?.let {
                    setTextColor(ContextCompat.getColor(it, R.color.delete_color))
                    getView().setBackgroundColor(
                        ContextCompat.getColor(it,
                            R.color.white
                        ))
                }
            }.show()
        }

        // 新規保存のリスナー
        setFragmentResultListener("createBean") { _, _ ->
            Snackbar.make(binding.snackBarPlace, getString(R.string.bean_finish_create_message), Snackbar.LENGTH_SHORT).apply {
                mContext?.let {
                    setTextColor(ContextCompat.getColor(it, R.color.pink_dark))
                    getView().setBackgroundColor(
                        ContextCompat.getColor(it,
                            R.color.white
                        ))
                }
            }.show()
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

    // お気に入りアイコン クリックリスナ―
    override fun onFavoriteClick(view: View, data: SimpleBeanInfo) {
        viewModel.updateFavoriteIcon(data)
    }

    // BeanItem クリックリスナー
    override fun onClick(view: View, selectedItem: SimpleBeanInfo) {
        val showDetailAction = HomeBeansFragmentDirections.showBeanDetailAction().apply {
            beanId = selectedItem.id
        }

        Navigation.findNavController(view).navigate(showDetailAction)
    }

    private fun setUpRecyclerView(context: Context, rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
    }
}