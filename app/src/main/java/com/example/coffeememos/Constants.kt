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
            1500,
            1800,
            0,
            "kaldi",
            "おいしい",
            3,
            3,
            20220908
        )


        val sampleRecipe1 = Recipe(
            0,
            0,
            "hario",
            6,
            90,
            30,
            0,
            90,
            8,
            40,
            "おいしい！",
            20220909
        )

        val sampleRecipe2 = Recipe(
            0,
            0,
            "karita",
            6,
            90,
            30,
            0,
            90,
            8,
            40,
            "おいしい！おいしい！",
            20220909
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


        // 焙煎度リスト
        val roastList: List<String> = listOf(
            "ライトロースト",
            "シナモンロースト",
            "ミディアムロースト",
            "ハイロースト",
            "シティロースト",
            "フルシティロースト",
            "フレンチロースト",
            "イタリアンロースト"
        )

        // 豆の粗さリスト
        val grindSizeList: List<String> = listOf(
            "極細引き",
            "細引き",
            "中細引き",
            "中挽き",
            "粗挽き"
        )

        // コーヒー豆の精製法リスト
        val processList: List<String> = listOf(
            "ナチュラル",
            "ウォッシュト",
            "スマトラ",
            "パルプトナチュラルプロセス",
            "ハニープロセス",
            "アナエロビックファーメンテーション",

        )
    }
}