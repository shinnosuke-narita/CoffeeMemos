package com.example.coffeememos.utilities

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import com.example.coffeememos.R

class ViewUtil {
    companion object {
        fun setCardTag(view: TextView, value: String) {
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



        // window上viewの絶対Y座標
        fun getViewYCoordinateInWindow(view: View): Int {
            val location = IntArray(2)
            view.getLocationInWindow(location)
            return location[1]
        }

        // statusBar 高さ取得
        fun getStatusBarHeight(activity: Activity): Int {
            val rect = Rect()
            activity.window.decorView.getWindowVisibleDisplayFrame(rect)
            return rect.top
        }

        fun scrollToTargetView(activity: Activity, scrollView: ScrollView, header: View, targetView: View) {
            val titleViewYCoordinate = getViewYCoordinateInWindow(targetView)
            val statusBarHeight = getStatusBarHeight(activity)
            val scrollY = titleViewYCoordinate - header.height - statusBarHeight
            scrollView.smoothScrollBy(0, scrollY)
        }
    }
}