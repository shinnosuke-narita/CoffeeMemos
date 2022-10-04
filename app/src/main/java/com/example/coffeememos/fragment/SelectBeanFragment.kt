package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.R
import com.example.coffeememos.adapter.BeanAdapter
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.viewModel.NewRecipeViewModel
import com.example.coffeememos.viewModel.NewRecipeViewModelFactory
import com.example.coffeememos.viewModel.SelectBeanViewModel
import com.example.coffeememos.viewModel.SelectBeanViewModelFactory

class SelectBeanFragment : Fragment() {
    private var mContext: Context? = null

    private lateinit var viewModel: SelectBeanViewModel

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
        return inflater.inflate(R.layout.fragment_select_bean, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewModelの初期化
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        viewModel = SelectBeanViewModelFactory(db.beanDao()
        ).create(SelectBeanViewModel::class.java)


        viewModel.beanList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) return@observe

            val rv = view.findViewById<RecyclerView>(R.id.rv)
            rv.layoutManager = LinearLayoutManager(mContext).apply {
                orientation = LinearLayoutManager.VERTICAL
            }

            // TODO adapterSet
            rv.adapter = viewModel.beanList.value?.let {
                BeanAdapter(
                    it,
                    object : BeanAdapter.OnItemClickListener {
                        override fun onClick(bean: Bean) {
                            Toast.makeText(mContext, "hello world", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }
}