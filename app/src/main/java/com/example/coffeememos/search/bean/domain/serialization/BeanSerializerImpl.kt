package com.example.coffeememos.search.bean.domain.serialization

import android.util.Log
import com.example.coffeememos.search.bean.domain.model.FilterBeanInputData
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class BeanSerializerImpl @Inject constructor()
    : BeanSerializer {
    override fun serialize(filterBeanInputData: FilterBeanInputData): String {
        try {
            return Json.encodeToString(filterBeanInputData)
        } catch (e: SerializationException) {
            Log.e(e.toString(), e.message.toString())
        } catch (e: IllegalArgumentException) {
            Log.e(e.toString(), e.message.toString())
        }
        return ""
    }

    override fun deserialize(json: String): FilterBeanInputData? {
        try {
            return Json.decodeFromString(json)
        } catch (e: SerializationException) {
            Log.d(e.toString(), e.message.toString())
        } catch (e: IllegalArgumentException) {
            Log.e(e.toString(), e.message.toString())
        }
        return null
    }
}