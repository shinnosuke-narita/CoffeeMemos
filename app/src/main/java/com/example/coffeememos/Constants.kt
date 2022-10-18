package com.example.coffeememos

import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.Taste
import com.example.coffeememos.util.DateUtil
import com.example.coffeememos.util.Util

class Constants {
    companion object {
        private val timeStamp = System.currentTimeMillis()

        const val databaseResetFlag = false

        val attributeAuthorUrl = "<a href=\"https://www.flaticon.com/free-icons/coffee\" title=\"coffee icons\">Coffee icons created by srip - Flaticon</a>"

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
            true,
            timeStamp
        )

        val sampleBean2 = Bean(
            0,
            "コロンビア",
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
            true,
            timeStamp + 1000
        )

        val sampleBean3 = Bean(
            0,
            "ケニア",
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
            true,
            timeStamp + 2000
        )

        val sampleBean4 = Bean(
            0,
            "インドネシア",
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
            true,
            timeStamp + 3000
        )

        val sampleBean5 = Bean(
            0,
            "エチオピア",
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
            true,
            timeStamp + 4000
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
            true,
            3,
            System.currentTimeMillis()
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
            false,
            4,
            System.currentTimeMillis()
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