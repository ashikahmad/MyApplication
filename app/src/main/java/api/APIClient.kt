package api

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import model.Country

class ApiClient(private val ctx: Context) {

    /***
     * PERFORM REQUEST
     */
    private fun performRequest(route: ApiRoute, completion: (success: Boolean, apiResponse: ApiResponse) -> Unit) {
        val request: StringRequest = object : StringRequest(route.httpMethod, route.url, { response ->
            this.handle(response, completion)
        }, {
            it.printStackTrace()
            if (it.networkResponse != null && it.networkResponse.data != null)
                this.handle(String(it.networkResponse.data), completion)
            else
                this.handle(getStringError(it), completion)
        }) {
            override fun getParams(): MutableMap<String, String> {
                return route.params
            }

            override fun getHeaders(): MutableMap<String, String> {
                return route.headers
            }
        }
        request.retryPolicy = DefaultRetryPolicy(route.timeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        getRequestQueue().add(request)
    }

    /**
     * This method will make the creation of the answer as ApiResponse
     **/
    private fun handle(response: String, completion: (success: Boolean, apiResponse: ApiResponse) -> Unit) {
        val ar = ApiResponse(response)
        completion.invoke(ar.success, ar)
    }

    /**
     * This method will return the error as String
     **/
    private fun getStringError(volleyError: VolleyError): String {
        return when (volleyError) {
            is TimeoutError -> "The conection timed out."
            is NoConnectionError -> "The conection couldn´t be established."
            is AuthFailureError -> "There was an authentication failure in your request."
            is ServerError -> "Error while prosessing the server response."
            is NetworkError -> "Network error, please verify your conection."
            is ParseError -> "Error while prosessing the server response."
            else -> "Internet error"
        }
    }
    /**
     * We create and return a new instance for the queue of Volley requests.
     **/
    private fun getRequestQueue(): RequestQueue {
        val maxCacheSize = 20 * 1024 * 1024
        val cache = DiskBasedCache(ctx.cacheDir, maxCacheSize)
        val netWork = BasicNetwork(HurlStack())
        val mRequestQueue = RequestQueue(cache, netWork)
        mRequestQueue.start()
        System.setProperty("http.keepAlive", "false")
        return mRequestQueue
    }

    fun getAllCountries(completion: (countries: List<Country>?, message: String) -> Unit) {
        val route = ApiRoute.GetAll(ctx)
        performRequest(route) { success, response ->
            if (success) {
                val countries: List<Country> =  response.json.toArray()
                completion.invoke(countries, "")
            } else {
                completion.invoke(null, response.message)
            }
        }
    }

//    fun getAll(completion: (user: [Country]?, message: String) -> Unit) {
//        val route = ApiRoute.GetAll(ctx)
//        this.performRequest(route) { success, response ->
//            if (success) {
//                // this object creation could be created at the other class
//                // like *ApiResponseManager* and do a CRUD if it is necesary
//                val user: User = response.json.toObject()
//                completion.invoke(user, "")
//            } else {
//                completion.invoke(null, response.message)
//            }
//        }
//    }
}