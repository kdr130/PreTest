package com.example.pretest.utils

import android.util.Log

object UrlUtil {
    fun getQueryParameterValue(url: String, parameterName: String): String? {
        Log.d("UrlUtil", "url: ${url}")
        if (url.isEmpty()) return null

        val params: List<String> = url.split("&")
        val map: MutableMap<String, String> = HashMap()
        for (param in params) {
            val name = param.split("=")[0]
            val value = param.split("=")[1]
            map[name] = value
        }

        return map[parameterName]
    }
}