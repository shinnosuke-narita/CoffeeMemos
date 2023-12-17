package com.withapp.coffeememo.search.recipe.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.withapp.coffeememo.R
import com.withapp.coffeememo.core.ad_mob.locale.LocalizationManager
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel
import com.withapp.coffeememo.search.recipe.presentation.adapter.`interface`.OnFavoriteClickListener
import com.withapp.coffeememo.search.recipe.presentation.adapter.`interface`.OnItemClickListener
import com.withapp.coffeememo.utilities.DateUtil
import com.withapp.coffeememo.utilities.ViewUtil
import com.withapp.coffeememo.utilities.ViewUtil.Companion.setFavoriteIcon

class RecipeDetailAdapter(val context: Context, val data: List<SearchRecipeModel>)
    : RecyclerView.Adapter<RecipeDetailViewHolder>() {
    private lateinit var mItemClickListener: OnItemClickListener
    private lateinit var mFavoriteListener: OnFavoriteClickListener


    fun setOnItemClickListener(listener: OnItemClickListener) {
        mItemClickListener = listener
    }

    fun setFavoriteListener(listener: OnFavoriteClickListener) {
        mFavoriteListener = listener
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDetailViewHolder {
        return RecipeDetailViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recipe_item_width_taste, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeDetailViewHolder, position: Int) {
        val recipe: SearchRecipeModel = data[position]
        val roastList: List<String> =
            LocalizationManager.getRoastList()
        val grindSizeList: List<String> =
            LocalizationManager.getGrindSizeList()

        holder.tool.text       = recipe.tool
        holder.sour.text       = recipe.sour.toString()
        holder.bitter.text     = recipe.bitter.toString()
        holder.sweet.text      = recipe.sweet.toString()
        holder.flavor.text     = recipe.flavor.toString()
        holder.rich.text       = recipe.rich.toString()
        holder.rating.text     = context.getString(R.string.rate_decimal, recipe.rating.toString())
        holder.createdAt.text  =
            DateUtil.formatEpochTimeMills(
                recipe.createdAt,
                context.getString(R.string.date_pattern))
        ViewUtil.setCardTag(holder.country, recipe.country)
        ViewUtil.setCardTag(holder.roast, roastList[recipe.roast])
        ViewUtil.setCardTag(holder.grindSize,  grindSizeList[recipe.grindSize])

        // お気に入りアイコンのセット
        setFavoriteIcon(holder.favorite, recipe.isFavorite)

        // お気に入りアイコン コールバックセット
        holder.favorite.setOnClickListener {
            mFavoriteListener.onFavoriteClick(holder.favorite, position, recipe)
        }

        // アイテムタップ時のコールバックセット
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(recipe)
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