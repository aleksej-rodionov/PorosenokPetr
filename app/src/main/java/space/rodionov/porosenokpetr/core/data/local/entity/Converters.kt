package space.rodionov.porosenokpetr.core.data.local.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExampleConverters {

    @TypeConverter
    fun toStringList(json: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun toJson(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}