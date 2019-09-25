package com.bigo.data.file

import android.content.Context
import java.io.IOException

class AssetFileReader(private val context: Context) : FileReader {
    @Throws(IOException::class)
    override fun readFile(name: String): String {
        val inputStream = context.assets.open(name)
        return inputStream.use { `is` ->
            `is`.bufferedReader().use { reader ->
                reader.readText()
            }
        }
    }

}