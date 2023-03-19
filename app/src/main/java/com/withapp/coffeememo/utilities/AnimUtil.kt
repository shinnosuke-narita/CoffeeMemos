package com.withapp.coffeememo.utilities

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup

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

        fun translateYAnimation(view: View, duration: Long, startPos: Float, endPos: Float) {
            ObjectAnimator.ofFloat(view, "translationY", startPos, endPos).apply {
                this.duration = duration
                start()
            }
        }

        fun expandMenu(containerView: ViewGroup) {
            // viewの大きさを計測
            containerView.measure(
                View.MeasureSpec.makeMeasureSpec(
                    0,
                    View.MeasureSpec.UNSPECIFIED
                ),
                View.MeasureSpec.makeMeasureSpec(
                    0,
                    View.MeasureSpec.UNSPECIFIED
                )
            )
            val containerHeight: Int = containerView.measuredHeight

            containerView.layoutParams.height = 0
            val anim = ValueAnimator
                .ofInt(0, containerHeight)
                .apply {
                addUpdateListener {
                    val updateValue = it.animatedValue as Int
                    when (updateValue) {
                        0 ->  {
                            containerView.visibility = View.VISIBLE
                        }
                        containerHeight -> {
                            containerView.layoutParams.height =
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        }
                        else -> {
                          containerView.layoutParams.height = updateValue
                        }
                    }

                    containerView.requestLayout()
                }
                duration = 300
            }
            anim.start()
        }

        fun collapseMenu(parentView: ViewGroup, filterElementView: ViewGroup) {
            ValueAnimator.ofInt(parentView.height, 0).apply {
                duration = 300
                addUpdateListener {
                    if (it.animatedValue == 0) {
                        parentView.visibility = View.GONE
                        filterElementView.removeAllViews()
                    }
                    parentView.layoutParams.height = it.animatedValue as Int
                    parentView.requestLayout()
                }
                start()
            }
        }

        fun collapseMenu(parentView: ViewGroup) {
            ValueAnimator.ofInt(parentView.height, 0).apply {
                duration = 300
                addUpdateListener {
                    if (it.animatedValue == 0) {
                        parentView.visibility = View.GONE
                    }
                    parentView.layoutParams.height = it.animatedValue as Int
                    parentView.requestLayout()
                }
                start()
            }
        }
    }
}