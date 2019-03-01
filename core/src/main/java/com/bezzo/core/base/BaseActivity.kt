package com.bezzo.core.base

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.Toast
import com.bezzo.core.R
import com.bezzo.core.util.CommonUtils
import com.bezzo.core.util.KeyboardUtils
import com.bezzo.core.util.LocaleHelper
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.default_toolbar.*

/**
 * Created by bezzo on 26/09/17.
 */

open abstract class BaseActivity : AppCompatActivity(), BaseActivityView, BaseFragment.Callback {

    lateinit var mProgressDialog: ProgressDialog
    var mActionBar: ActionBar? = null
    var dataReceived: Bundle? = null
    lateinit var mContext: Context

    val rootView: View
        get() = findViewById(android.R.id.content)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDependencyInjection()
        setContentView(setLayout())
        mProgressDialog = ProgressDialog(this)
        mContext = this
        dataReceived = intent.extras

        setSupportActionBar(toolbar)

        mActionBar = supportActionBar

        if (toolbar != null){
            toolbar.setNavigationOnClickListener(View.OnClickListener { view: View? ->
                onNavigationClick(view!!)
            })
        }

        if (intent != null) {
            dataReceived = intent.extras
        }

        onInitializedView(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    protected abstract fun onInitializedView(savedInstanceState: Bundle?)

    fun onNavigationClick(view: View) {
        onClickBack()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(TAG: String) {

    }

    abstract fun setLayout() : Int

    override fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }

    override fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    override fun openActivityOnTokenExpire() {

    }

    override fun isNetworkConnected(): Boolean {
        return CommonUtils.isNetworkConnected(applicationContext)
    }

    override fun hideKeyboard() {
        KeyboardUtils.hideSoftInput(this)
    }

    override fun showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

    override fun showToast(resId: Int, duration: Int) {
        Toast.makeText(this, getString(resId), duration).show()
    }

    override fun goToActivity(c: Class<*>, bundle: Bundle?, isFinish: Boolean) {
        val i = Intent(this, c)
        if (bundle != null) {
            i.putExtras(bundle)
        }
        startActivity(i)
        if (isFinish) {
            finish()
        }
    }

    override fun goToActivityClearAllStack(c: Class<*>, bundle: Bundle?) {
        val i = Intent(this, c)
        if (bundle != null) {
            i.putExtras(bundle)
        }
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(i)
        finish()
    }

    override fun goToActivityForResult(c: Class<*>, bundle: Bundle?, result: Int) {
        val i = Intent(this, c)
        if (bundle != null) {
            i.putExtras(bundle)
        }
        startActivityForResult(i, result)
    }

    override fun finishActivityforResult(bundle: Bundle?, result: Int) {
        val i = Intent()
        if (bundle != null) {
            i.putExtras(bundle)
        }
        setResult(result, i)
        finish()
    }

    override fun gotoFragment(contentReplace: Int, data: Bundle?, classFragment: Class<*>) {
        lateinit var fragment: Fragment

        try {
            fragment = classFragment.newInstance() as Fragment
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        val transaction = supportFragmentManager
                .beginTransaction()

        if (data != null) {
            fragment.arguments = data
        }

        transaction.replace(contentReplace, fragment)

        transaction.commit()
    }

    override fun showProgressDialog(message: String, cancelable: Boolean) {
        mProgressDialog.setMessage(message)
        mProgressDialog.setCancelable(cancelable)
        if (!mProgressDialog.isShowing) {
            mProgressDialog.show()
        }
    }

    override fun dismissProgressDialog() {
        if (mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }

    override fun gotoDialog(dialogClass: Class<*>, data: Bundle?) {
        val fm = supportFragmentManager
        var fragment: DialogFragment? = null

        try {
            fragment = dialogClass.newInstance() as DialogFragment
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        if (data != null) {
            fragment!!.arguments = data
        }

        fragment!!.show(fm, "TAG")
    }

    override fun getContext(): Context? {
        return mContext
    }

    override fun displayHome() {
        if (mActionBar != null) {
            mActionBar!!.setHomeButtonEnabled(true)
            mActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
            mActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun setActionBarTitle(title: String) {
        if (mActionBar != null) {
            mActionBar!!.title = title
        }
    }

    override fun showSnackBar(message: String, duration: Int) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, duration)
        val subView = snackbar.view
        val textView = subView.findViewById<View>(android.support.design
                .R.id.snackbar_text) as AppCompatTextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackbar.show()
    }

    override fun showSnackBar(resId: Int, duration: Int) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content),
                getString(resId), duration)
        val subView = snackbar.view
        val textView = subView.findViewById<View>(android.support.design
                .R.id.snackbar_text) as AppCompatTextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackbar.show()
    }

    override fun onClickBack() {
        finish()
    }

    override fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    override fun handleError(case: Int) {
        when(case) {
            1 -> {
                showToast(getString(R.string.service_not_found), Toast.LENGTH_SHORT)
            }
            2 -> {
                showToast(getString(R.string.network_not_stable), Toast.LENGTH_SHORT)
            }
            3 -> {
                showToast(getString(R.string.server_error), Toast.LENGTH_SHORT)
            }
            4 -> {
                showToast(getString(R.string.service_not_connected), Toast.LENGTH_SHORT)
            }
            5 -> {
                showToast(getString(R.string.no_access), Toast.LENGTH_SHORT)
            }
            6 -> {
                showToast(getString(R.string.some_error), Toast.LENGTH_SHORT)
            }
        }
    }
}
