package com.withapp.coffeememo.home.recipe.presentation.adapter

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
import com.withapp.coffeememo.home.recipe.presentation.model.HomeRecipeCardData
import com.withapp.coffeememo.utilities.ViewUtil
import com.withapp.coffeememo.utilities.ViewUtil.Companion.setFavoriteIcon
import java.time.format.DateTimeFormatter

class RecipeAdapter(
    private val context: Context,
    private val onFavoriteClick: (recipe: HomeRecipeCardData) -> Unit,
    private val onItemClick: (recipe: HomeRecipeCardData) -> Unit
) : ListAdapter<
        HomeRecipeCardData,
        RecipeAdapter.RecipeViewHolder>(DiffRecipeCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.home_card_item,
                    parent,
                    false)
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)

        // createdAt変換
        val createdAt: String =
            recipe.createdAt.format(DateTimeFormatter.ofPattern(
                    context.getString(R.string.date_pattern)
                )
            )

        holder.tool.text = recipe.tool
        holder.createdAt.text = createdAt
        holder.rating.text =
            context.getString(R.string.rate_decimal, recipe.rating)
        ViewUtil.setCardTag(holder.country, recipe.country)
        ViewUtil.setCardTag(holder.roast, recipe.roast)

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

    class RecipeViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        val tool: TextView = itemView.findViewById(R.id.title)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val country: TextView = itemView.findViewById(R.id.tagFirst)
        val roast: TextView = itemView.findViewById(R.id.tagSecond)
        val rating: TextView = itemView.findViewById(R.id.rating)
        val favorite: ImageView = itemView.findViewById(R.id.favoriteIcon)
    }

    class DiffRecipeCallBack
        : DiffUtil.ItemCallback<HomeRecipeCardData>() {
        override fun areItemsTheSame(
            oldItem: HomeRecipeCardData,
            newItem: HomeRecipeCardData
        ): Boolean {
            return oldItem.recipeId == newItem.recipeId
        }

        override fun areContentsTheSame(
            oldItem: HomeRecipeCardData,
            newItem: HomeRecipeCardData
        ): Boolean {
            return oldItem == newItem
        }
    }
}
