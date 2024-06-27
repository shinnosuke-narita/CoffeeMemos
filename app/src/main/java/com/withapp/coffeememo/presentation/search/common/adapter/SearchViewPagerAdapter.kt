package com.withapp.coffeememo.presentation.search.common.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchViewPagerAdapter(parent: Fragment, private val fragmentList: List<Fragment>) : FragmentStateAdapter(parent){
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}