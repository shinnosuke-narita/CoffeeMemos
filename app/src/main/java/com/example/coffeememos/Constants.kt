package com.example.coffeememos

import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.Taste

class Constants {
    companion object {
        val sampleBean = Bean(
            0,
            "ブラジル",
            "",
            "",
            "",
            "1500",
            "1800",
            "washed",
            "kaldi",
            "おいしい",
            3,
            3,
            "2022/09/08"
        )


        val sampleRecipe1 = Recipe(
            0,
            0,
            "hario",
            6,
            90,
            30,
            90,
            8,
            40,
            "おいしい！",
            "2022/09/09"
        )

        val sampleRecipe2 = Recipe(
            0,
            0,
            "karita",
            6,
            90,
            30,
            90,
            8,
            40,
            "おいしい！おいしい！",
            "2022/09/09"
        )

        val sampleTaste = Taste(
            0,
            0,
            3,
            3,
            3,
            3,
            3
        )
    }
}