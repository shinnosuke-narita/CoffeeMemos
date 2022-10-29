package com.example.coffeememos.manager

class RatingManager(
    starFirst    : Star,
    starSecond   : Star,
    starThird    : Star,
    starFourth   : Star,
    starFifth    : Star) {
    // rating 初期値: 1
    var currentRating: Int = 1

    val starList: List<Star> = listOf(
        starFirst, starSecond, starThird, starFourth, starFifth
    )

    fun changeRatingState(selectedRate: Int) {
        currentRating = selectedRate

        for ((index, star) in starList.withIndex()) {
            if (index < selectedRate) {
                star.state = StarState.LIGHT
            } else {
                star.state = StarState.DARK
            }
        }
    }

    enum class StarState { LIGHT, DARK }

    data class Star(var state: StarState)
}