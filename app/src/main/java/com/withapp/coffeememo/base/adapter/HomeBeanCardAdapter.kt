package com.withapp.coffeememo.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.withapp.coffeememo.R
import com.withapp.coffeememo.domain.model.bean.HomeBeanModel
import com.withapp.coffeememo.utilities.ViewUtil
import com.withapp.coffeememo.utilities.ViewUtil.Companion.setFavoriteIcon
import java.time.format.DateTimeFormatter

class HomeBeanCardAdapter(
    private val context: Context,
    private val onFavoriteClick: (bean: HomeBeanModel) -> Unit,
    private val onItemClick: (bean: HomeBeanModel) -> Unit
) : ListAdapter<
        HomeBeanModel,
        HomeBeanCardAdapter.HomeBeanCardViewHolder
        >(DiffBeanCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBeanCardViewHolder {
        return HomeBeanCardViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.home_card_item,
                    parent,
                    false)
        )
    }

    override fun onBindViewHolder(holder: HomeBeanCardViewHolder, position: Int) {
        // データ取得
        val bean = getItem(position)

        holder.country.text = bean.country
        holder.createdAt.text =
            bean.createdAt?.format(
                DateTimeFormatter.ofPattern(
                    context.getString(R.string.date_pattern)
                )
            )
        holder.rating.text =
            context.getString(R.string.rate_decimal, bean.rating.toString())

        // cardタグ セット
        ViewUtil.setCardTag(holder.farm, bean.farm)
        ViewUtil.setCardTag(holder.district, bean.district)

        // お気に入りアイコンのセット
        setFavoriteIcon(holder.favorite, bean.isFavorite)

        // お気に入りアイコンクリックリスナ―
        holder.favorite.setOnClickListener {
            onFavoriteClick(bean)
        }

        // アイテムクリックリスナ―
        holder.itemView.setOnClickListener {
            onItemClick(bean)
        }
    }

    class HomeBeanCardViewHolder(card: View)
        : RecyclerView.ViewHolder(card) {
        val country: TextView = card.findViewById(R.id.title)
        val createdAt: TextView = card.findViewById(R.id.createdAt)
        val farm: TextView = card.findViewById(R.id.tagFirst)
        val district: TextView = card.findViewById(R.id.tagSecond)
        val rating: TextView  = card.findViewById(R.id.rating)
        val favorite: ImageView = card.findViewById(R.id.favoriteIcon)
    }

    class DiffBeanCallBack
        : DiffUtil.ItemCallback<HomeBeanModel>() {
        override fun areItemsTheSame(
            oldItem: HomeBeanModel,
            newItem: HomeBeanModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HomeBeanModel,
            newItem: HomeBeanModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}

