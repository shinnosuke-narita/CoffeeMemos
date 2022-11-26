package com.example.coffeememos.utilities

import android.animation.ObjectAnimator
import android.view.View

class AnimUtil {
    companion object {
        fun fadeInAnimation(view: View, duration: Long) {
            ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f).apply {
                this.duration = duration
                start()
            }
        }

        fun fadeOutAnimation(view: View, duration: Long) {
            ObjectAnimator.ofFloat(view, "alpha",  1.0f, 0.0f).apply {
                this.duration = duration
                start()
            }
        }
    }
}