package com.withapp.coffeememo.search.bean.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.withapp.coffeememo.R
import com.withapp.coffeememo.base.adapter.BeanViewHolder
import com.withapp.coffeememo.core.ad_mob.locale.LocalizationManager
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.search.bean.presentation.adapter.listener.OnFavoriteClickListener
import com.withapp.coffeememo.search.bean.presentation.adapter.listener.OnItemClickListener
import com.withapp.coffeememo.utilities.DateUtil
import com.withapp.coffeememo.utilities.ViewUtil

class BeanAdapter(
    private val context: Context,
    private val data: List<SearchBeanModel>
    ) : RecyclerView.Adapter<BeanViewHolder>() {
    private lateinit var mItemClickListener: OnItemClickListener
    private lateinit var mFavoriteListener: OnFavoriteClickListener

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mItemClickListener = listener
    }

    fun setFavoriteListener(listener: OnFavoriteClickListener) {
        mFavoriteListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeanViewHolder {
        return BeanViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bean_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BeanViewHolder, position: Int) {
        val processList: List<String> =
            LocalizationManager.getProcessList()

        holder.country.text     = data[position].country
        holder.species.text     = data[position].species
        holder.storeName.text   = data[position].store
        holder.processName.text = processList[data[position].process]
        holder.rate.text        = context.getString(R.string.rate_decimal, data[position].rating.toString())
        holder.createdAt.text   =
            DateUtil.formatEpochTimeMills(
                data[position].createdAt,
                context.getString(R.string.date_pattern))
        ViewUtil.setCardTag(holder.farm, data[position].farm)
        ViewUtil.setCardTag(holder.district, data[position].district)
        ViewUtil.setFavoriteIcon(holder.favoriteIcon, data[position].isFavorite)

        // リストアイテムクリック時のコールバック
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(data[position])
        }

        // favoriteアイコンクリック時のコールバック
        holder.favoriteIcon.setOnClickListener { v ->
            mFavoriteListener.onFavoriteClick(
                v, position, data[position])
        }
    }
}

class BeanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val country     : TextView  = itemView.findViewById(R.id.country)
    val farm        : TextView  = itemView.findViewById(R.id.farm)
    val district    : TextView  = itemView.findViewById(R.id.district)
    val storeName   : TextView  = itemView.findViewById(R.id.storeName)
    val processName : TextView  = itemView.findViewById(R.id.processName)
    val species     : TextView  = itemView.findViewById(R.id.speciesName)
    val rate        : TextView  = itemView.findViewById(R.id.rating)
    val favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon)
    val createdAt   : TextView  = itemView.findViewById(R.id.createdAt)
}