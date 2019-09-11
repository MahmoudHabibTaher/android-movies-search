package com.bigo.data.parsers

import com.bigo.data.parsers.excpetions.JsonParseException

interface JsonParser {
    @Throws(JsonParseException::class)
    fun <T> parse(json: String): T
}