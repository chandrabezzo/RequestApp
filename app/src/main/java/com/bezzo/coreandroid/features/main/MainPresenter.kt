package com.bezzo.coreandroid.features.main

import android.widget.Toast
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.bezzo.core.base.BasePresenter
import com.bezzo.core.data.local.LocalStorageHelper
import com.bezzo.core.data.model.Country
import com.bezzo.core.data.network.ApiHelper
import com.bezzo.core.data.network.HandleRequest
import com.bezzo.core.data.session.SessionHelper
import com.bezzo.core.util.SchedulerProvider
import com.google.gson.reflect.TypeToken
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONArray
import javax.inject.Inject


/**
 * Created by bezzo on 24/01/18.
 * if you use kotlin, when send to view you must add "?" for null check pointer
 * but if you use java, when send to view you must add if(!isViewAttached) return;
 * before you send data to view
 */

class MainPresenter<V : MainContracts.View> @Inject
constructor(apiHelper: ApiHelper, sessionHelper: SessionHelper, localHelper: LocalStorageHelper,
            schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(apiHelper, sessionHelper, localHelper, schedulerProvider, compositeDisposable), MainContracts.Presenter<V> {

    override fun getCountries(limit: Int) {
        val request = apiHelper.getCountry(limit, object : HandleRequest{
            override fun onSuccess(response: JSONArray) {
                val listType = object : TypeToken<List<Country>>() {}.type
                val countries = gson.fromJson<List<Country>>(response.toString(), listType)
                view?.showCountries(countries)
            }

            override fun onFailure(error: VolleyError) {
                view?.showToast(error.message ?: "Error", Toast.LENGTH_SHORT)
            }
        })

        view?.getContext()?.let { context -> Volley.newRequestQueue(context).add(request) }
    }
}