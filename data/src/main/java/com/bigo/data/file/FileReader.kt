package com.bigo.data.file

import java.io.IOException

interface FileReader {
    @Throws(IOException::class)
    fun readFile(name: String): String
}