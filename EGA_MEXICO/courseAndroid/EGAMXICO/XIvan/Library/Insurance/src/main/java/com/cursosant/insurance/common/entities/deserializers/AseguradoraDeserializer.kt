package com.cursosant.insurance.common.entities.deserializers

import com.cursosant.insurance.common.entities.Aseguradora
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import java.lang.reflect.Type

/**
 * Permite interpretar respuestas del API donde el campo "aseguradora" puede
 * llegar como un objeto JSON o simplemente como una cadena con el alias.
 */
class AseguradoraDeserializer : JsonDeserializer<Aseguradora?> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Aseguradora? {
        if (json == null || json is JsonNull) return null

        return when (json) {
            is JsonPrimitive -> parsePrimitive(json)
            is JsonObject -> parseObject(json)
            else -> null
        }
    }

    private fun parsePrimitive(json: JsonPrimitive): Aseguradora {
        val alias = if (json.isString) json.asString.orEmpty() else json.toString()
        return Aseguradora(alias = alias, phone = null)
    }

    private fun parseObject(json: JsonObject): Aseguradora {
        val alias = json.get("alias")?.takeIf { !it.isJsonNull }?.asString
        val phone = json.get("phone")?.takeIf { !it.isJsonNull }?.asString
        val fallbackAlias = json.get("compania")?.takeIf { !it.isJsonNull }?.asString
            ?: json.get("name")?.takeIf { !it.isJsonNull }?.asString
            ?: json.toString()

        return Aseguradora(alias = alias ?: fallbackAlias ?: "", phone = phone)
    }
}
