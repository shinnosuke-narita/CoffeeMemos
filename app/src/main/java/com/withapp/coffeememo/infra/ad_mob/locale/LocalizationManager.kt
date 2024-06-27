package com.withapp.coffeememo.infra.ad_mob.locale

import com.withapp.coffeememo.infra.ad_mob.locale.constants.BaseConstantsHolder
import com.withapp.coffeememo.infra.ad_mob.locale.constants.ConstantsEn
import com.withapp.coffeememo.infra.ad_mob.locale.constants.ConstantsJa
import java.util.*

object LocalizationManager {
    // ローカライズされたコンスタンツ
    private var constantsHolder: BaseConstantsHolder = ConstantsEn()

    val roastListSize: Int
        get() = getRoastList().size
    val processListSize: Int
        get() = getProcessList().size
    val grindListSize: Int
        get() = getGrindSizeList().size

    init {
        updateLocale()
    }

    fun updateLocale() {
        val locale: Locale = Locale.getDefault()

        constantsHolder =
            if (locale == Locale.JAPAN) {
                ConstantsJa()
            } else {
                ConstantsEn()
            }
    }

    fun getRoastList(): List<String> {
        return constantsHolder.getRoastList()
    }

    fun getProcessList(): List<String> {
        return constantsHolder.getProcessList()
    }

    fun getGrindSizeList(): List<String> {
        return constantsHolder.getGrindSizeList()
    }
}