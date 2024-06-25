package com.withapp.coffeememo.presentation.favorite.recipe.adapter

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
import com.withapp.coffeememo.presentation.favorite.recipe.model.FavoriteRecipeModel
import com.withapp.coffeememo.utilities.DateUtil
import com.withapp.coffeememo.utilities.ViewUtil

class FavoriteRecipeAdapter(
    private val context: Context,
    private val onFavoriteClick: (
        recipe: FavoriteRecipeModel,
        favoriteIcon: View
    ) -> Unit,
    private val onItemClick: (recipe: FavoriteRecipeModel) -> Unit
) : ListAdapter<
        FavoriteRecipeModel,
        FavoriteRecipeAdapter.RecipeViewHolder>(DiffRecipeCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.recipe_detail_card,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        val preInfusionTime: String =
            DateUtil.formatPreInfusionTime(context, recipe.preInfusionTime)
        val extractionTime: String =
            DateUtil.formatExtractionTime(context, recipe.extractionTime)
        val createdAt: String =
            DateUtil.formatEpochTimeMills(
                recipe.createdAt,
                context.getString(R.string.date_pattern)
            )

        holder.tool.text = recipe.tool
        holder.roast.text = recipe.roast
        holder.grindSize.text = recipe.grindSize
        holder.amountBean.text = recipe.amountOfBeans
        holder.temperature.text = recipe.temperature
        holder.preInfusionTime.text = preInfusionTime
        holder.extractionTime.text = extractionTime
        holder.amountExtraction.text = recipe.amountExtraction
        holder.createdAt.text = createdAt
        holder.comment.text = recipe.comment
        holder.rating.text = String.format("%d.0",recipe.rating)

        // お気に入りアイコンセットアップ
        ViewUtil.setFavoriteIcon(holder.favorite, recipe.isFavorite)

        // ★画像のセットアップ
        setUpStarView(recipe.rating, holder.startList)

        // お気に入りアイコン コールバックセット
        holder.favorite.setOnClickListener { view ->
            onFavoriteClick(recipe, view)
        }
        // アイテムタップ時のコールバックセット
        holder.itemView.setOnClickListener {
            onItemClick(recipe)
        }
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tool: TextView =
            itemView.findViewById(R.id.toolText)
        val roast: TextView =
            itemView.findViewById(R.id.roastText)
        val grindSize: TextView =
            itemView.findViewById(R.id.grindText)
        val amountBean: TextView =
            itemView.findViewById(R.id.amountBeanText)
        val temperature: TextView =
            itemView.findViewById(R.id.temperatureText)
        val preInfusionTime: TextView =
            itemView.findViewById(R.id.preInfusionTimeText)
        val extractionTime: TextView =
            itemView.findViewById(R.id.extractionTimeText)
        val amountExtraction: TextView =
            itemView.findViewById(R.id.amountExtractionText)
        val createdAt: TextView =
            itemView.findViewById(R.id.createdDateText)
        val comment: TextView =
            itemView.findViewById(R.id.recipeCommentText)
        val rating: TextView =
            itemView.findViewById(R.id.recipeRating)
        val favorite: ImageView =
            itemView.findViewById(R.id.recipeFavoriteIcon)

        val startList: List<ImageView> =
            listOf(
                itemView.findViewById(R.id.recipeStarFirst),
                itemView.findViewById(R.id.recipeStarSecond),
                itemView.findViewById(R.id.recipeStarThird),
                itemView.findViewById(R.id.recipeStarFourth),
                itemView.findViewById(R.id.recipeStarFifth),
            )
    }

    class DiffRecipeCallBack
        : DiffUtil.ItemCallback<FavoriteRecipeModel>() {
        override fun areItemsTheSame(
            oldItem: FavoriteRecipeModel,
            newItem: FavoriteRecipeModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FavoriteRecipeModel,
            newItem: FavoriteRecipeModel
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
