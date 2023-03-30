package com.withapp.coffeememo.utilities

import com.withapp.coffeememo.dao.BeanDao
import com.withapp.coffeememo.entity.Bean
import com.withapp.coffeememo.entity.Recipe

class SampleData {
    companion object {

        private val timeStamp = System.currentTimeMillis()

        val sampleBean1 = Bean(
            0,
            "グアテマラ",
            "Medina農園",
            "アンティグア地区",
            "ブルボン種",
            1300,
            1500,
            1,
            "コーヒーストア",
            "ソフトな香り、クリーンな酸味、質感が高く甘味のある風味、上品でシナモンやマスカットを感じさせる後味。",
            5,
            true,
            timeStamp
        )

        val sampleBean2 = Bean(
            0,
            "タンザニア",
            "",
            "サザンハイランド地区",
            "ブルボン種",
            1700,
            1700,
            1,
            "コーヒーストア",
            "おいしい",
            4,
            true,
            timeStamp + 1000
        )

        val sampleBean3 = Bean(
            0,
            "インドネシア",
            "複数の農園",
            "スマトラ島",
            "Sigarar Utang",
            1300,
            1650,
            0,
            "コーヒーストア",
            "おいしい",
            5,
            true,
            timeStamp + 2000
        )

        suspend fun createBeans(beanDao: BeanDao) {
            beanDao.insert(sampleBean1)
            beanDao.insert(sampleBean2)
            beanDao.insert(sampleBean3)
        }
    }
}