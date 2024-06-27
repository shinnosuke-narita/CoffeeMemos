package com.withapp.coffeememo.infra.ad_mob.locale.constants

class ConstantsJa : BaseConstantsHolder {
    override fun getRoastList(): List<String> {
        return listOf(
            "ライトロースト",
            "シナモンロースト",
            "ミディアムロースト",
            "ハイロースト",
            "シティロースト",
            "フルシティロースト",
            "フレンチロースト",
            "イタリアンロースト"
        )
    }

    override fun getProcessList(): List<String> {
        return listOf(
            "ナチュラル",
            "ウォッシュト",
            "スマトラ",
            "パルプトナチュラルプロセス",
            "ハニープロセス",
            "アナエロビックファーメンテーション",
        )
    }

    override fun getGrindSizeList(): List<String> {
        return listOf(
            "極細引き",
            "細引き",
            "中細引き",
            "中挽き",
            "粗挽き"
        )
    }
}