package com.bezzo.coreandroid.data

import com.bezzo.coreandroid.data.local.LocalStorageHelper
import com.bezzo.coreandroid.data.local.LocalStorageHelperContract
import com.bezzo.coreandroid.data.network.ApiHelperContract
import com.bezzo.coreandroid.data.session.SessionHelperContract

/**
 * Created by bezzo on 26/09/17.
 */

interface DataManagerContract : ApiHelperContract, SessionHelperContract {
    val localStorageHelper: LocalStorageHelper
}
