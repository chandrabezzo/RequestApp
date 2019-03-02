package com.bezzo.core.data.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object Service {
    private lateinit var context: Context

    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(context) }

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }
}