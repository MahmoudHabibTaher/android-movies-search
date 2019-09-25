package com.bigo.data.parsers

import com.google.gson.Gson
import java.lang.Exception

class JsonParserImpl(private val gson: Gson) : JsonParser {
    override fun <T> parse(json: String, clazz: Class<T>): T? {
        return try {
            gson.fromJson(json, clazz)
        } catch (ex: Exception) {
            null
        }
    }
}