package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.adapter.BeanAdapter
import com.example.coffeememos.databinding.FragmentSelectBeanBinding
import com.example.coffeememos.listener.OnItemClickListener
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.viewModel.SelectBeanViewModel
import com.example.coffeememos.viewModel.SelectBeanViewModelFactory

class SelectBeanFragment : Fragment() {
    private var mContext: Context? = null

    private lateinit var viewModel: SelectBeanViewModel

    private var _binding: FragmentSelectBeanBinding? = null
    private val binding
        get() = _binding!!

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
    ): View {
        // Inflate the layout for this fragment
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


        // viewModelの初期化
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        viewModel = SelectBeanViewModelFactory(db.beanDao()
        ).create(SelectBeanViewModel::class.java)


        // RecyclerView セットアップ
        val rv = view.findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(mContext).apply {
            orientation = LinearLayoutManager.VERTICAL
        }


        viewModel.beanList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) return@observe

            mContext?.let { context ->
                rv.adapter = BeanAdapter(context, list).apply {
                    setOnItemClickListener (object : OnItemClickListener<Bean> {
                        override fun onClick(view: View, bean: Bean) {
                            val bundle = Bundle().apply {
                                putLong("beanId", bean.id)
                                putString("country", bean.country)
                                putString("district", bean.district)
                                putString("farm", bean.farm)
                                putString("elevationFrom", bean.elevationFrom.toString())
                                putString("elevationTo", bean.elevationTo.toString())
                                putString("store", bean.store)
                                putString("process", Constants.processList[bean.process])
                                putString("rating", bean.rating.toString())
                            }
                            setFragmentResult("selectedBean", bundle)

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