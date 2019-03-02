package com.bezzo.core.data.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.bezzo.core.data.session.SessionHelper
import com.bezzo.core.util.SchedulerProvider
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by bezzo on 11/01/18.
 */

@Singleton
class ApiHelper @Inject
constructor(val schedulerProvider: SchedulerProvider) {

    @Inject
    lateinit var session : SessionHelper

    @Inject
    lateinit var context: Context

    fun getCountry(limit: Int, listener: HandleRequest): JsonArrayRequest {
        return JsonArrayRequest(Request.Method.GET, "${ApiEndPoint.COUNTRY}?limit=$limit", null,
                Response.Listener<JSONArray> { response ->
                    listener.onSuccess(response)
                },
                Response.ErrorListener {
                    listener.onFailure(it)
                })
    }
}

interface HandleRequest {
    fun onSuccess(response: JSONArray)

    fun onFailure(error: VolleyError)
}
