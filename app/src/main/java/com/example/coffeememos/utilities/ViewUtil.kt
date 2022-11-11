package com.example.coffeememos.utilities

import android.widget.ImageView
import android.widget.TextView
import com.example.coffeememos.R

class ViewUtil {
    companion object {
        fun setRecipeTag(view: TextView, value: String) {
            if (value.isEmpty()) return

            view.text = value
            view.setBackgroundResource(R.drawable.recipe_tag_background)
        }

        fun setFavoriteIcon(imageView: ImageView, isFavorite: Boolean) {
            if (isFavorite) imageView.setImageResource(R.drawable.ic_baseline_favorite_24)
            else imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }
}