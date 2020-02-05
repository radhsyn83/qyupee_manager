package id.qyupee.utils

import android.app.Activity
import android.content.Context
import android.preference.PreferenceManager
import id.qyupee.R
import org.jetbrains.anko.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


//
// Created by Fathur Radhy 
// on May 2019-05-31.
//
object RetrofitHandler {
    fun checkResponse(mContext: Context, success: Boolean, code: Int?, msg: String?, onSuccess: () -> Unit) {
        if(success){
            when(code) {
                403 -> {
                    val userPreference = UserPreference(PreferenceManager.getDefaultSharedPreferences(mContext))
                    userPreference.removeToken()
                    msg?.let {
                        mContext.toast(it)
                    }
                }
                200 -> {
                    onSuccess()
                }
                else -> {
                    val activity = mContext as Activity
                    activity.finish()
                    mContext.toast(msg.toString())
                }
            }
        } else{
            val activity = mContext as Activity
            activity.finish()
            mContext.toast(msg.toString())
        }
    }

    fun failure(mContext: Context, t: Throwable) {

        if (t is SocketTimeoutException) {
            mContext.toast(mContext.resources.getString(R.string.rto))
        } else if (t is ConnectException) {
            mContext.toast(mContext.resources.getString(R.string.no_internet))
        } else if (t is UnknownHostException) {
            mContext.toast(mContext.resources.getString(R.string.unknown_server))
        } else {
            if (t.message != null) {
                mContext.toast(t.message.toString())
            }
        }
    }

//    fun failure(mContext: Context, t: Throwable) {
//
//        if (t is SocketTimeoutException) {
//            Tools.toastWarning(mContext, mContext.resources.getString(R.string.rto))
//        } else if (t is ConnectException) {
//            Tools.toastWarning(mContext, mContext.resources.getString(R.string.no_internet))
//        } else if (t is UnknownHostException) {
//            Tools.toastWarning(
//                mContext,
//                mContext.resources.getString(R.string.unknown_server)
//            )
//        } else {
//            if (t.message != null) {
//                Tools.toastWarning(mContext, t.message.toString())
//                Log.d("RetrofitError", t.message.toString())
//            }
//        }
//    }
}