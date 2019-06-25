package api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

interface JSONConvertable {
    fun toJSON(): String = Gson().toJson(this)
}

//inline fun <reified T: JSONConvertable> String.toObject(): T = Gson().fromJson(this, T::class.java)
//inline fun <reified T: JSONConvertable> String.toList(): List<T> = Gson().fromJson(this, object : TypeToken<List<T>>() {}.type)
inline fun <reified T: JSONConvertable> String.toObject(): T = Gson().fromJson(this, object: TypeToken<T>() {}.type)
inline fun <reified T: List<JSONConvertable>> String.toArray(): T = Gson().fromJson(this, object: TypeToken<T>() {}.type)


////From JSON
//val json = "..."
//val object = json.toObject<User>()
//
//// To JSON
//val json = object.toJSON()
