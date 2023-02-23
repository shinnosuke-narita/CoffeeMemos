package com.example.coffeememos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.Constants
import com.example.coffeememos.search.domain.model.SearchRecipeModel
import com.example.coffeememos.R
import com.example.coffeememos.utilities.DateUtil
import com.example.coffeememos.utilities.ViewUtil
import com.example.coffeememos.utilities.ViewUtil.Companion.setFavoriteIcon

class RecipeDetailAdapter(context: Context, data: List<SearchRecipeModel>) : BaseAdapter<SearchRecipeModel, RecipeDetailViewHolder>(context, data) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDetailViewHolder {
        return RecipeDetailViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_width_taste, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeDetailViewHolder, position: Int) {
        val recipe: SearchRecipeModel = data[position]

        holder.tool.text       = recipe.tool
        holder.createdAt.text  = DateUtil.formatEpochTimeMills(recipe.createdAt, DateUtil.pattern)
        holder.sour.text       = recipe.sour.toString()
        holder.bitter.text     = recipe.bitter.toString()
        holder.sweet.text      = recipe.sweet.toString()
        holder.flavor.text     = recipe.flavor.toString()
        holder.rich.text       = recipe.rich.toString()
        holder.rating.text     = context.getString(R.string.rate_decimal, recipe.rating.toString())
        ViewUtil.setCardTag(holder.country, recipe.country)
        ViewUtil.setCardTag(holder.roast, Constants.roastList[recipe.roast])
        ViewUtil.setCardTag(holder.grindSize,  Constants.grindSizeList[recipe.grindSize])

        // お気に入りアイコンのセット
        setFavoriteIcon(holder.favorite, recipe.isFavorite)

        // お気に入りアイコン コールバックセット
        holder.favorite.setOnClickListener { clickedFavoriteIcon ->
            mFavoriteListener.onFavoriteClick(clickedFavoriteIcon, recipe)
        }

        // アイテムタップ時のコールバックセット
        holder.itemView.setOnClickListener { view ->
            mItemClickListener.onClick(view, recipe)
        }
    }
}


class RecipeDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val country  : TextView   = itemView.findViewById(R.id.country)
    val tool     : TextView   = itemView.findViewById(R.id.tool)
    val roast    : TextView   = itemView.findViewById(R.id.roast)
    val grindSize: TextView   = itemView.findViewById(R.id.grindSize)
    val createdAt: TextView   = itemView.findViewById(R.id.createdAt)
    val sour     : TextView   = itemView.findViewById(R.id.sourValue)
    val bitter   : TextView   = itemView.findViewById(R.id.bitterValue)
    val sweet    : TextView   = itemView.findViewById(R.id.sweetValue)
    val flavor   : TextView   = itemView.findViewById(R.id.flavorValue)
    val rich     : TextView   = itemView.findViewById(R.id.richValue)
    val rating   : TextView   = itemView.findViewById(R.id.rating)
    val favorite : ImageView  = itemView.findViewById(R.id.favoriteIcon)
}