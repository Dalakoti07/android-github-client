package com.dalakoti07.android.core.utils

import com.google.gson.Gson
import java.io.File

open class BaseFakeService {
    protected var gsonObject = Gson()

    protected var responseBuilder: okhttp3.Response.Builder = okhttp3.Response.Builder()

    protected fun readJson(path: String): String {
        val file = File("src/test-common/$path")
        return String(file.readBytes())
    }

}