package com.co.jasamedika.coreandroid.data

import com.co.jasamedika.coreandroid.data.local.LocalStorageHelper
import com.co.jasamedika.coreandroid.data.local.LocalStorageHelperContract
import com.co.jasamedika.coreandroid.data.network.ApiHelperContract
import com.co.jasamedika.coreandroid.data.session.SessionHelperContract

/**
 * Created by bezzo on 26/09/17.
 */

interface DataManagerContract : ApiHelperContract, SessionHelperContract {
    val localStorageHelper: LocalStorageHelper
}
