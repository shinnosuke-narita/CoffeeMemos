package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.R
import com.example.coffeememos.adapter.BeanAdapter
import com.example.coffeememos.databinding.FragmentSearchBeanBinding
import com.example.coffeememos.databinding.SearchContentsBinding
import com.example.coffeememos.entity.CustomBean
import com.example.coffeememos.listener.OnFavoriteIconClickListener
import com.example.coffeememos.listener.OnItemClickListener
import com.example.coffeememos.search.BeanSortType
import com.example.coffeememos.viewModel.MainSearchViewModel
import com.example.coffeememos.viewModel.SearchBeanViewModel

class SearchBeanFragment : Fragment() {
    // viewBinding
    private var _mainBinding: FragmentSearchBeanBinding? = null
    private val mainBinding get() = _mainBinding!!
    // merge tag 使用
    private var _binding: SearchContentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchBeanViewModel by viewModels {
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        SearchBeanViewModel.SearchBeanViewModelFactory(db.beanDao())
    }

    // 共有viewModel
    private val sharedViewModel: MainSearchViewModel by viewModels({ requireParentFragment() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mainBinding = FragmentSearchBeanBinding.inflate(inflater, container, false)
        _binding = SearchContentsBinding.bind(mainBinding.root)
        return mainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView(requireContext(), binding.searchResultRV)

        // 検索結果 監視処理
        viewModel.searchResult.observe(viewLifecycleOwner) { list ->
            if (list == null) return@observe

            binding.searchResultRV.adapter = setUpAdapter(list)
        }

        // 絞り込み結果 監視処理
        viewModel.filteringResult.observe(viewLifecycleOwner) { list ->
            if (list == null) return@observe

            binding.searchResultRV.adapter = setUpAdapter(list)
        }

        viewModel.allCustomBean.observe(viewLifecycleOwner) { list ->
            viewModel.setSearchResult(list)
        }

        // 現在のソート 監視処理
        viewModel.currentSortType.observe(viewLifecycleOwner) { type ->
            binding.currentSortText.text = type.getSortName()
        }

        sharedViewModel.searchKeyWord.observe(viewLifecycleOwner) { keyWord ->
            viewModel.freeWordSearch(keyWord)
        }

        // レシピ数 監視処理
        viewModel.beanCount.observe(viewLifecycleOwner) { count ->
            binding.itemCount.text = requireContext().getString(R.string.recipeCount, count)
        }

        viewModel.isOpened.observe(viewLifecycleOwner) { isOpened ->
            if (isOpened) binding.wholeShadow.visibility = View.VISIBLE
            else binding.wholeShadow.visibility = View.GONE
        }

        binding.sortBtn.setOnClickListener { view ->
            viewModel.changeBottomSheetState()

            val originData = BeanSortType.getNameList()
            val currentSortTypeName = viewModel.currentSortType.value!!.getSortName()
            val currentIndex = BeanSortType.getIndexByName(currentSortTypeName)

            childFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_bottom,R.anim.go_down,R.anim.enter_from_bottom, R.anim.go_down)
                .replace(R.id.bottomSheet, SortFragment.create(currentIndex, originData.toTypedArray()))
                .addToBackStack(null)
                .commit()
        }

        childFragmentManager.setFragmentResultListener("sortResult", viewLifecycleOwner) { _, bundle ->
            viewModel.changeBottomSheetState()

            val selectedIndex: Int = bundle.getInt("selectedIndex", 0)
            val selectedSortType: BeanSortType = BeanSortType.getSortTypeByIndex(selectedIndex)

            viewModel.sortSearchResult(selectedSortType)
        }

        binding.refineBtn.setOnClickListener { view ->
            viewModel.changeBottomSheetState()

            childFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_bottom,R.anim.go_down,R.anim.enter_from_bottom, R.anim.go_down)
                .replace(R.id.bottomSheet, BeanFilterFragment())
                .addToBackStack(null)
                .commit()
        }

        childFragmentManager.setFragmentResultListener("filterResult", viewLifecycleOwner) { _, bundle ->
            viewModel.changeBottomSheetState()

            viewModel.filterSearchResult()
        }

       binding.clearBtn.setOnClickListener {
           viewModel.resetResult()
       }
    }

    private fun setUpRecyclerView(context: Context, rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
    }

    private fun setUpAdapter(list: List<CustomBean>): BeanAdapter {
        return BeanAdapter(requireContext(), list).apply {
            setFavoriteListener(object : OnFavoriteIconClickListener {
                override fun onClick(view: View, id: Long) {
                    viewModel.updateFavoriteIcon(view, id)
                }
            })
            setOnItemClickListener(object : OnItemClickListener<CustomBean> {
                override fun onClick(view: View, bean: CustomBean) {
                    if (viewModel.isOpened.value!!) return

                    val showDetailAction = SearchFragmentDirections.showBeanDetailAction().apply {
                        beanId = bean.id
                    }

                    Navigation.findNavController(view).navigate(showDetailAction)
                }
            })
        }
    }
}