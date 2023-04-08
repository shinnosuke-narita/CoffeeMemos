package com.withapp.coffeememo

object Constants {
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