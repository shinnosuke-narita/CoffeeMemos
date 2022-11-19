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
import java.util.*

class RecipeDetailAdapter(context: Context, data: List<CustomRecipe>) : BaseAdapter<CustomRecipe, RecipeDetailViewHolder>(context, data) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDetailViewHolder {
        return RecipeDetailViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_detail, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeDetailViewHolder, position: Int) {
        val recipe: CustomRecipe = data[position]
        val preInfusionTime      : Long = DateUtil.convertSeconds(recipe.preInfusionTime)
        val extractionTimeMinutes: Long = DateUtil.getMinutes(recipe.extractionTime)
        val extractionTimeSeconds: Long = DateUtil.getSeconds(recipe.extractionTime)

        holder.tool.text             = recipe.tool
        holder.country.text          = recipe.country
        holder.roast.text            = Constants.roastList[recipe.roast]
        holder.grindSize.text        = Constants.grindSizeList[recipe.grindSize]
        holder.createdAt.text        = DateUtil.formatEpochTimeMills(recipe.createdAt, DateUtil.pattern)
        holder.amountBean.text       = context.getString(R.string.amountBeans_gram, recipe.amountBean)
        holder.amountExtraction.text = context.getString(R.string.amountExtraction_cc, recipe.amountExtraction)
        holder.temperature.text      = context.getString(R.string.temperature_degree, recipe.temperature)
        holder.preInfusionTime.text  = context.getString(R.string.preInfusionTime_seconds, preInfusionTime)
        holder.extractionTime.text   = context.getString(R.string.extractionTime_unit, extractionTimeMinutes, extractionTimeSeconds)
        holder.rating.text           = context.getString(R.string.rate_decimal, recipe.rating.toString())

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
    val country  : TextView         = itemView.findViewById(R.id.country)
    val tool     : TextView         = itemView.findViewById(R.id.tool)
    val roast    : TextView         = itemView.findViewById(R.id.roast)
    val grindSize: TextView         = itemView.findViewById(R.id.grindSize)
    val createdAt: TextView         = itemView.findViewById(R.id.createdAt)
    val amountBean: TextView        = itemView.findViewById(R.id.amountBean)
    val amountExtraction: TextView  = itemView.findViewById(R.id.amountExtraction)
    val temperature: TextView       = itemView.findViewById(R.id.temperature)
    val preInfusionTime: TextView   = itemView.findViewById(R.id.preInfusionTime)
    val extractionTime: TextView    = itemView.findViewById(R.id.extractionTime)
    val rating   : TextView         = itemView.findViewById(R.id.rating)
    val favorite : ImageView        = itemView.findViewById(R.id.favoriteIcon)
}