package com.withapp.coffeememo.select_bean.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.withapp.coffeememo.CoffeeMemosApplication
import com.withapp.coffeememo.R
import com.withapp.coffeememo.base.adapter.BeanAdapter
import com.withapp.coffeememo.databinding.FragmentSelectBeanBinding
import com.withapp.coffeememo.base.adapter.listener.OnItemClickListener
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.MainViewModel

class SelectBeanFragment : Fragment() {
    private var mContext: Context? = null

    private val viewModel: SelectBeanViewModel by viewModels {
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        SelectBeanViewModelFactory(db.beanDao())
    }

    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentSelectBeanBinding? = null
    private val binding
        get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectBeanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // header セッティング
        binding.header.headerTitle.text = getString(R.string.select_bean)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }


        // RecyclerView セットアップ
        val rv = view.findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(mContext).apply {
            orientation = LinearLayoutManager.VERTICAL
        }


        // コーヒー豆リスト監視処理
        viewModel.beanList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) return@observe

            mContext?.let { context ->
                rv.adapter = BeanAdapter(context, list).apply {
                    setOnItemClickListener (object :
                        OnItemClickListener<SearchBeanModel> {
                        override fun onClick(view: View, bean: SearchBeanModel) {
                            // MainViewModel にセット
                            mainViewModel.setBean(bean)

                            findNavController().popBackStack()
                        }
                    })
                }
            }
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