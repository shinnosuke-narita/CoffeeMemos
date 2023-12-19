package com.withapp.coffeememo.entity

class Rating(private var _currentRating: Int = 1) {
    private val maxRate = 5

    val currentRating: Int
        get() = _currentRating

    private val _starList: MutableList<Star> = mutableListOf()
    val starList: List<Star>
        get() = _starList

    init {
        initializeStarList(_currentRating)
    }

    private fun initializeStarList(rating: Int) {
        for (index in 1..maxRate) {
            val star: Star = if (index <= rating) Star(StarState.LIGHT) else Star(StarState.DARK)
            _starList.add(star)
        }
    }

    fun changeRatingState(selectedRate: Int) {
        _currentRating = selectedRate

        for ((index, star) in _starList.withIndex()) {
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