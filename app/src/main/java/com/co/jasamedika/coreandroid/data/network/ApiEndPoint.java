package com.co.jasamedika.coreandroid.data.network;

import com.co.jasamedika.coreandroid.BuildConfig;

/**
 * Created by bezzo on 25/09/17.
 */

public final class ApiEndPoint {
    public static final String USER = BuildConfig.BASE_URL + "user";
    public static final String JABATAN = BuildConfig.BASE_URL + "jabatan";
    public static final String KARYAWAN = BuildConfig.BASE_URL + "karyawan";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }
}
