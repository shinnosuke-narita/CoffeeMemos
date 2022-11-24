package com.example.coffeememos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.R
import com.example.coffeememos.SimpleRecipe
import com.example.coffeememos.utilities.ViewUtil.Companion.setTagAndFavoriteIcon

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
        setTagAndFavoriteIcon(holder.favorite, data[position].isFavorite)

        // お気に入りアイコン コールバックセット
        holder.favorite.setOnClickListener { clickedFavoriteIcon ->
            mFavoriteListener.onClick(clickedFavoriteIcon, data[position].recipeId)
        }

        // アイテムタップ時のコールバックセット
        holder.itemView.setOnClickListener { view ->
            mItemClickListener.onClick(view, data[position])
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