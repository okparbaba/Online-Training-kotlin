package com.greenhacker.greenhackeronlinetraining.caches

import android.content.Context
import android.os.Build
import android.util.Log

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets


import com.greenhacker.greenhackeronlinetraining.BuildConfig.DEBUG


class CacheManager(private val context: Context) {

    fun writeJson(`object`: Any, type: Type, fileName: String) {
        val file = File(context.cacheDir, fileName)
        var outputStream: OutputStream? = null
        val gson = GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create()
        try {
            outputStream = FileOutputStream(file)
            val bufferedWriter: BufferedWriter
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream,
                        StandardCharsets.UTF_8))
            } else {
                bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
            }

            gson.toJson(`object`, type, bufferedWriter)
            bufferedWriter.close()

        } catch (e: FileNotFoundException) {
            M.l(TAG, e)
        } catch (e: IOException) {
            M.l(TAG, e)
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush()
                    outputStream.close()
                } catch (e: IOException) {
                    M.l(TAG, e)
                }

            }
        }

    }


    fun readJson(type: Type, fileName: String): Any? {
        var jsonData: Any? = null

        val file = File(context.cacheDir, fileName)
        var inputStream: InputStream? = null
        val gson = GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create()
        try {
            inputStream = FileInputStream(file)
            val streamReader: InputStreamReader
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                streamReader = InputStreamReader(inputStream,
                        StandardCharsets.UTF_8)
            } else {
                streamReader = InputStreamReader(inputStream, "UTF-8")
            }

            jsonData = gson.fromJson<Any>(streamReader, type)
            streamReader.close()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            if (DEBUG) Log.e(TAG, "loadJson, FileNotFoundException e: '$e'")
        } catch (e: IOException) {
            e.printStackTrace()
            if (DEBUG) Log.e(TAG, "loadJson, IOException e: '$e'")
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    if (DEBUG) Log.e(TAG, "loadJson, finally, e: '$e'")
                }

            }
        }
        return jsonData
    }

    companion object {
        private val TAG = CacheManager::class.java!!.getSimpleName()
    }
}