package com.example.to_do_list.data.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: String?): T? {
    json ?: return null
    return fromJson<T>(json, object : TypeToken<T>() {}.type)
}