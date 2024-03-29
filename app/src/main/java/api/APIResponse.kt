package api

import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

class ApiResponse(response: String) {

    var success: Boolean = false
    var message: String = ""
    var json: String = ""

    private val data = "data"
    private val msg = "error"

    init {
        try {
            val jsonToken = JSONTokener(response).nextValue()
            if (jsonToken is JSONObject) {
                val jsonRsponse = JSONObject(response)

                message = if (jsonRsponse.has(msg)) {
                    jsonRsponse.getJSONObject(msg).getString("message")
                } else {
                    "An error was occurred while processing the response"
                }

                if (jsonRsponse.optJSONObject(data) != null) {
                    json = jsonRsponse.getJSONObject(data).toString()
                    success = true
                } else {
                    success = false
                }
            } else if (jsonToken is JSONArray) {
                val jsonRsponse = JSONArray(response)
                json = jsonRsponse.toString()
                success = true
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}