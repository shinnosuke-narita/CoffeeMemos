package com.withapp.coffeememo.presentation.home.bean.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.withapp.coffeememo.R
import com.withapp.coffeememo.presentation.base.adapter.HomeBeanCardAdapter
import com.withapp.coffeememo.databinding.FragmentHomeBeansBinding
import com.withapp.coffeememo.presentation.home.bean.view_model.HomeBeanViewModel
import com.withapp.coffeememo.presentation.utilities.SnackBarUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeBeansFragment : Fragment() {
    // viewBinding
    private var _binding: FragmentHomeBeansBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeBeanViewModel by viewModels()

    // RecyclerView adapter
    private lateinit var _newBeanAdapter: HomeBeanCardAdapter
    private lateinit var _highRatingBeanAdapter: HomeBeanCardAdapter
    private lateinit var _favoriteBeansAdapter: HomeBeanCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBeansBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // UIセットアップ
        setUpView()

        // 総レシピ数 監視処理
        viewModel.totalBeanCount.observe(
            viewLifecycleOwner) { count ->
            binding.beanTotalNum.text = count
        }

        // 今日作成したレシピ数 監視処理
        viewModel.todayBeanCount.observe(
            viewLifecycleOwner) { count ->
            binding.todayBeanNum.text = count
        }

        // お気に入りコーヒー豆数
        viewModel.favoriteBeanCount.observe(
            viewLifecycleOwner) { count ->
            binding.favoriteBeanNum.text = count
        }

        // 新しい順レシピ 監視処理
        viewModel.newBeans.observe(viewLifecycleOwner) { list ->
            _newBeanAdapter.submitList(list)
        }

        // お気に入りレシピ 監視処理
        viewModel.favoriteBeans.observe(viewLifecycleOwner) { list ->
            _favoriteBeansAdapter.submitList(list)
        }

        // レイティング順レシピ 監視処理
        viewModel.highRatingBeans.observe(viewLifecycleOwner) { list ->
            _highRatingBeanAdapter.submitList(list)
        }

        // コーヒー豆の登録数 監視処理
        viewModel.beanExists.observe(viewLifecycleOwner) { beanExists ->
            if (beanExists) {
                binding.newBeanHeader.visibility = View.VISIBLE
                binding.highRatingBeanHeader.visibility = View.VISIBLE
                binding.favoriteBeanHeader.visibility = View.VISIBLE
            } else {
                binding.newBeanHeader.visibility = View.GONE
                binding.highRatingBeanHeader.visibility = View.GONE
                binding.favoriteBeanHeader.visibility = View.GONE
            }
        }

        // 豆の新規作成画面に遷移
        binding.createBeanBtn.setOnClickListener {
            findNavController().navigate(R.id.newBeanFragment)
        }

        // レシピ画面に戻る
        binding.goToRecipeBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        // 削除処理のリスナー
        setFragmentResultListener("deleteBean") { _, _ ->
            SnackBarUtil.showFinishDeleteSnackBar(
                requireContext(),
                binding.snackBarPlace,
                getString(R.string.bean_finish_delete_message)
            )
        }

        // 新規保存のリスナー
        setFragmentResultListener("createBean") { _, _ ->
            SnackBarUtil.showSimpleSnackBar(
                requireContext(),
                binding.snackBarPlace,
                getString(R.string.bean_finish_create_message)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHomeBeanData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {
        _newBeanAdapter = getHomeBeanAdapter()
        _favoriteBeansAdapter = getHomeBeanAdapter()
        _highRatingBeanAdapter = getHomeBeanAdapter()

        binding.newBeanList.apply {
            adapter = _newBeanAdapter
            layoutManager = getLinerLayoutManger()
        }
        binding.favoriteBeanList.apply {
            adapter = _favoriteBeansAdapter
            layoutManager = getLinerLayoutManger()
        }
        binding.highRatingBeanList.apply {
            adapter = _highRatingBeanAdapter
            layoutManager = getLinerLayoutManger()
        }
    }

    private fun getHomeBeanAdapter(): HomeBeanCardAdapter {
        return HomeBeanCardAdapter(
            context = requireContext(),
            onFavoriteClick = { bean ->
                viewModel.updateHomeBeanData(
                    bean.id,
                    bean.isFavorite)
            },
            onItemClick = { bean ->
                val showDetailAction =
                    HomeBeansFragmentDirections.showBeanDetailAction()
                        .apply {
                            beanId  = bean.id
                        }
                findNavController().navigate(showDetailAction)
            }
        )
    }

    private fun getLinerLayoutManger(): LinearLayoutManager {
        return LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
    }

    private fun setUpView() {
        // header セッティング
        binding.header.headerTitle.text = getString(R.string.home_bean)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        // RecyclerView セットアップ
        setUpRecyclerView()
    }
}