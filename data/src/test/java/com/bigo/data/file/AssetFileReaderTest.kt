package com.bigo.data.file

import android.content.Context
import android.content.res.AssetManager
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

class AssetFileReaderTest {

    private val assets = mock<AssetManager>()

    private val context = mock<Context> {
        on { assets } doReturn assets
    }

    private lateinit var fileReader: AssetFileReader

    @Before
    fun setUp() {
        fileReader = AssetFileReader(context)
    }

    @Test
    fun readFile() {
        val fileName = "dummy_text.txt"

        val url = AssetFileReaderTest::class.java.classLoader.getResource(fileName)

        val inputStream = FileInputStream(url?.path ?: "")

        whenever(assets.open(fileName)).doReturn(inputStream)

        val text = fileReader.readFile(fileName)

        assertEquals("Hello, World!", text)
    }

    @Test(expected = IOException::class)
    fun readFileNotFoundThrowException() {
        val fileName = "does_not_exist.txt"

        val url = AssetFileReaderTest::class.java.classLoader.getResource(fileName)

        val inputStream = FileInputStream(url?.path ?: "")

        whenever(assets.open(fileName)).doReturn(inputStream)

        fileReader.readFile(fileName)
    }
}