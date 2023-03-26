package com.withapp.coffeememo.favorite.bean.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.withapp.coffeememo.R
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel
import com.withapp.coffeememo.utilities.ViewUtil

class FavoriteBeanAdapter(
    private val onFavoriteClick: (
        bean: FavoriteBeanModel,
        favoriteIcon: View
    ) -> Unit,
    private val onItemClick: (bean: FavoriteBeanModel) -> Unit
) : ListAdapter<
        FavoriteBeanModel,
        FavoriteBeanAdapter.BeanViewHolder>(DiffBeanCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeanViewHolder {
        return BeanViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.bean_detail_info,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: BeanViewHolder, position: Int) {
        val bean = getItem(position)

        holder.country.text = bean.country
        holder.farm.text = bean.farm
        holder.district.text = bean.district
        holder.species.text = bean.species
        holder.process.text = bean.process
        holder.elevation.text = bean.elevation
        holder.store.text = bean.store
        holder.createdAt.text = bean.createdAt
        holder.comment.text = bean.comment
        holder.rating.text = String.format("%d.0",bean.rating)

        // お気に入りアイコンセットアップ
        ViewUtil.setFavoriteIcon(holder.favorite, bean.isFavorite)

        // ★画像のセットアップ
        setUpStarView(bean.rating, holder.startList)

        // お気に入りアイコン コールバックセット
        holder.favorite.setOnClickListener { view ->
            onFavoriteClick(bean, view)
        }
        // アイテムタップ時のコールバックセット
        holder.itemView.setOnClickListener {
            onItemClick(bean)
        }
    }

    class BeanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val country: TextView =
            itemView.findViewById(R.id.countryText)
        val farm: TextView =
            itemView.findViewById(R.id.farmText)
        val district: TextView =
            itemView.findViewById(R.id.districtText)
        val species: TextView =
            itemView.findViewById(R.id.speciesText)
        val process: TextView =
            itemView.findViewById(R.id.processText)
        val elevation: TextView =
            itemView.findViewById(R.id.elevationText)
        val store: TextView =
            itemView.findViewById(R.id.storeText)
        val comment: TextView =
            itemView.findViewById(R.id.beanCommentText)
        val rating: TextView =
            itemView.findViewById(R.id.beanRating)
        val createdAt: TextView =
            itemView.findViewById(R.id.createdAtText)
        val favorite: ImageView =
            itemView.findViewById(R.id.beanFavoriteIcon)

        val startList: List<ImageView> =
            listOf(
                itemView.findViewById(R.id.beanStarFirst),
                itemView.findViewById(R.id.beanStarSecond),
                itemView.findViewById(R.id.beanStarThird),
                itemView.findViewById(R.id.beanStarFourth),
                itemView.findViewById(R.id.beanStarFifth),
            )
    }

    class DiffBeanCallBack
        : DiffUtil.ItemCallback<FavoriteBeanModel>() {
        override fun areItemsTheSame(
            oldItem: FavoriteBeanModel,
            newItem: FavoriteBeanModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FavoriteBeanModel,
            newItem: FavoriteBeanModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    private fun setUpStarView(
        rating: Int,
        starViewList: List<ImageView>
    ) {
        for ((index, starView) in starViewList.withIndex()) {
            if (index < rating) {
                starView.setImageResource(
                    R.drawable.ic_baseline_star_beige_light_24
                )
                continue
            }

            starView.setImageResource(
                R.drawable.ic_baseline_star_grey
            )
        }
    }
}
