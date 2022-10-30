package com.example.coffeememos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.Constants.Companion.isFavoriteTagName
import com.example.coffeememos.Constants.Companion.notFavoriteTagName
import com.example.coffeememos.R
import com.example.coffeememos.SimpleRecipe

class RecipeAdapter(context: Context, data: List<SimpleRecipe>) : BaseAdapter<SimpleRecipe, RecipeViewHolder>(context, data) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_horizontal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.country.text      = data[position].country
        holder.createdAt.text    = data[position].createdAt
        holder.tool.text         = data[position].tool
        holder.roast.text        = data[position].roast
        holder.rating.text       = context.getString(R.string.rate_decimal, data[position].rating)

        // お気に入りアイコンのセット
        setFavoriteIcon(holder.favorite, data[position].isFavorite)

        // お気に入りアイコン コールバックセット
        holder.favorite.setOnClickListener { clickedFavoriteIcon ->
            mFavoriteListener.onClick(clickedFavoriteIcon, data[position].recipeId)
        }

        // アイテムタップ時のコールバックセット
        holder.itemView.setOnClickListener { view ->
            mItemClickListener.onClick(view, data[position])
        }
    }

    private fun setFavoriteIcon(favoriteIcon: ImageView, isFavorite: Boolean) {
        if (isFavorite) {
            favoriteIcon.tag = isFavoriteTagName
            favoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        else {
            favoriteIcon.tag = notFavoriteTagName
            favoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }
}



class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val country  : TextView  = itemView.findViewById(R.id.country)
    val createdAt: TextView  = itemView.findViewById(R.id.createdAt)
    val tool     : TextView  = itemView.findViewById(R.id.tool)
    val roast    : TextView  = itemView.findViewById(R.id.roast)
    val rating   : TextView  = itemView.findViewById(R.id.rating)
    val favorite : ImageView = itemView.findViewById(R.id.favoriteIcon)
}