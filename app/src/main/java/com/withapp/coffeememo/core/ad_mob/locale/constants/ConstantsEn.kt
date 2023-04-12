package com.withapp.coffeememo.core.ad_mob.locale.constants

class ConstantsEn : BaseConstantsHolder {
    override fun getRoastList(): List<String> {
        return listOf(
            "Cinnamon roast",
            "Light roast",
            "Medium roast",
            "High roast",
            "City roast",
            "Full city roast",
            "French roast",
            "Italian roast",
        )
    }

    override fun getProcessList(): List<String> {
        return listOf(
            "Natural process",
            "Washed process",
            "Pulped natural process",
            "Honey process",
            "Anaerobic fermentation",
        )
    }

    override fun getGrindSizeList(): List<String> {
        return listOf(
            "Very fine",
            "Fine",
            "Medium",
            "Coarse",
            "Very Coarse",
        )
    }
}