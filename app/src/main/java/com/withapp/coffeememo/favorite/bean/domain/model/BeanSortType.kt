package com.withapp.coffeememo.favorite.bean.domain.model

enum class BeanSortType {
    NEW    { override fun getSortName(): String = "新しい順" },
    OLD    { override fun getSortName(): String = "古い順" },
    RATING { override fun getSortName(): String = "評価順" };

    abstract fun getSortName(): String

    companion object {
        fun getNameList(): List<String> {
            val res = mutableListOf<String>()
            for (sortType in values()) {
                res.add(sortType.getSortName())
            }
            return res
        }

        fun getIndexByName(sortName: String): Int {
            for ((i, name) in getNameList().withIndex()) {
                if (name == sortName) return i
            }

            return 0
        }

        fun getSortTypeByIndex(index: Int): BeanSortType {
            for ((i, sortType) in values().withIndex()) {
                if (i == index) {
                    return sortType
                }
            }

            return NEW
        }
    }
}
