package com.example.coffeememos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.Constants
import com.example.coffeememos.Constants.Companion.isFavoriteTagName
import com.example.coffeememos.Constants.Companion.notFavoriteTagName
import com.example.coffeememos.CustomRecipe
import com.example.coffeememos.R
import com.example.coffeememos.SimpleRecipe
import com.example.coffeememos.utilities.DateUtil
import com.example.coffeememos.utilities.ViewUtil
import java.util.*

class RecipeDetailAdapter(context: Context, data: List<CustomRecipe>) : BaseAdapter<CustomRecipe, RecipeDetailViewHolder>(context, data) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDetailViewHolder {
        return RecipeDetailViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_width_taste, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeDetailViewHolder, position: Int) {
        val recipe: CustomRecipe = data[position]

        holder.tool.text       = recipe.tool
        holder.createdAt.text  = DateUtil.formatEpochTimeMills(recipe.createdAt, DateUtil.pattern)
        holder.sour.text       = recipe.sour.toString()
        holder.bitter.text     = recipe.bitter.toString()
        holder.sweet.text      = recipe.sweet.toString()
        holder.flavor.text     = recipe.flavor.toString()
        holder.rich.text       = recipe.rich.toString()
        holder.rating.text     = context.getString(R.string.rate_decimal, recipe.rating.toString())
        ViewUtil.setRecipeTag(holder.country, recipe.country)
        ViewUtil.setRecipeTag(holder.roast, Constants.roastList[recipe.roast])
        ViewUtil.setRecipeTag(holder.grindSize,  Constants.grindSizeList[recipe.grindSize])

        // お気に入りアイコンのセット
        setFavoriteIcon(holder.favorite, recipe.isFavorite)

        // お気に入りアイコン コールバックセット
        holder.favorite.setOnClickListener { clickedFavoriteIcon ->
            mFavoriteListener.onClick(clickedFavoriteIcon, recipe.recipeId)
        }

        // アイテムタップ時のコールバックセット
        holder.itemView.setOnClickListener { view ->
            mItemClickListener.onClick(view, recipe)
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