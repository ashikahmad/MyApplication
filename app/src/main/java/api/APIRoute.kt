package api

import android.content.Context
import com.android.volley.Request

sealed class ApiRoute {

    val timeOut: Int
        get() {
            return 3000
        }


    val baseUrl: String
        get() {
            return "https://restcountries.eu/rest/v2"
        }

    data class GetAll(var ctx: Context) : ApiRoute()
    data class GetCountry(var name: String, var ctx: Context) : ApiRoute()

    val httpMethod: Int
        get() {
            return when (this) {
                is GetAll -> Request.Method.GET
                is GetCountry -> Request.Method.GET
            }
        }

    val params: HashMap<String, String>
        get() {
            return hashMapOf()
//            return when (this) {
//                is GetCountry -> {
//                    hashMapOf(Pair("email", this.email), Pair("password", this.password))
//                }
//                else -> hashMapOf()
//            }
        }

    val headers: HashMap<String, String>
        get() {
            val map: HashMap<String, String> = hashMapOf()
            map["Accept"] = "application/json"
            return  map
//            return when (this) {
//                is GetUser -> {
//                    map["Authorization"] = "Bearer ${UserDefaults(this.ctx).accessToken}"
//                    map
//                }
//                is GetFeature -> {
//                    map["Authorization"] = "Bearer ${UserDefaults(this.ctx).accessToken}"
//                    map
//                }
//                else -> map
//            }
        }

    val url: String
        get() {
            return "$baseUrl/${when (this@ApiRoute) {
                is GetAll -> "all"
                is GetCountry -> "name/$name"
            }}"
        }
}
