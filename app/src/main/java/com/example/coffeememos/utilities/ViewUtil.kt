package com.example.coffeememos.utilities

import android.widget.ImageView
import android.widget.TextView
import com.example.coffeememos.Constants
import com.example.coffeememos.R

class ViewUtil {
    companion object {
        fun setRecipeTag(view: TextView, value: String) {
            if (value.isEmpty()) return

            view.text = value
            view.setBackgroundResource(R.drawable.recipe_tag_background)
        }


        // お気に入りアイコン用タグ名
        const val IS_FAVORITE_TAG_NAME = "isFavoriteIcon"
        const val NOT_FAVORITE_TAG_NAME = "notFavoriteIcon"
        fun setTagAndFavoriteIcon(favoriteIcon: ImageView, isFavorite: Boolean) {
            if (isFavorite) {
                favoriteIcon.tag = IS_FAVORITE_TAG_NAME
                favoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
            else {
                favoriteIcon.tag = NOT_FAVORITE_TAG_NAME
                favoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        fun setFavoriteIcon(imageView: ImageView, isFavorite: Boolean) {
            if (isFavorite) imageView.setImageResource(R.drawable.ic_baseline_favorite_24)
            else imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }
}