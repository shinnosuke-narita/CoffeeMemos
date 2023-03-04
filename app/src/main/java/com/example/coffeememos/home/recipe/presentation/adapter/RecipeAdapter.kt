package com.example.coffeememos.home.recipe.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.R
import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeInfo
import com.example.coffeememos.utilities.ViewUtil.Companion.setFavoriteIcon

class RecipeAdapter(
    private val context: Context,
    private val onFavoriteClick: (recipe: HomeRecipeInfo) -> Unit,
    private val onItemClick: (recipe: HomeRecipeInfo) -> Unit
) : ListAdapter<
        HomeRecipeInfo,
        RecipeAdapter.RecipeViewHolder>(DiffRecipeCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.recipe_item_horizontal,
                    parent,
                    false)
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.country.text = recipe.country
        holder.createdAt.text = recipe.createdAt
        holder.tool.text = recipe.tool
        holder.roast.text = recipe.roast
        holder.rating.text =
            context.getString(R.string.rate_decimal, recipe.rating)

        // お気に入りアイコンのセット
        setFavoriteIcon(holder.favorite, recipe.isFavorite)

        // お気に入りアイコン コールバックセット
        holder.favorite.setOnClickListener {
            onFavoriteClick(recipe)
        }

        // アイテムタップ時のコールバックセット
        holder.itemView.setOnClickListener {
            onItemClick(recipe)
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

    class DiffRecipeCallBack
        : DiffUtil.ItemCallback<HomeRecipeInfo>() {
        override fun areItemsTheSame(
            oldItem: HomeRecipeInfo,
            newItem: HomeRecipeInfo
        ): Boolean {
            return oldItem.recipeId == newItem.recipeId
        }

        override fun areContentsTheSame(
            oldItem: HomeRecipeInfo,
            newItem: HomeRecipeInfo
        ): Boolean {
            return oldItem == newItem
        }
    }
}
