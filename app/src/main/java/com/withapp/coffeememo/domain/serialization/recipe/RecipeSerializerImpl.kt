package com.withapp.coffeememo.domain.serialization.recipe

import android.util.Log
import com.withapp.coffeememo.domain.model.recipe.FilterRecipeInputData
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RecipeSerializerImpl @Inject constructor()
    : RecipeSerializer {
    override fun serialize(filterRecipeInputData: FilterRecipeInputData): String {
        try {
            return Json.encodeToString(filterRecipeInputData)
        } catch (e: SerializationException) {
            Log.d(e.toString(), e.message.toString())
        } catch (e: IllegalArgumentException) {
            Log.e(e.toString(), e.message.toString())
        }
        return ""
    }

    override fun deserialize(json: String): FilterRecipeInputData? {
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