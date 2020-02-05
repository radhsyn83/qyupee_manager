package id.qyupee.utils

import android.content.Context
import android.content.SharedPreferences
import id.qyupee.model.auth.Auth
import id.qyupee.ui.auth.AuthActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

//
// Created by Fathur Radhy 
// on May 2019-05-26.
//
class UserPreference(private val mSharedPreferences: SharedPreferences) {
    private val USER_TOKEN = "USER_TOKEN"
    private val USER_ID = "USER_ID"
    private val USER_PHONE = "USER_PHONE"
    private val USER_NAME = "USER_NAME"
    private val USER_USERNAME = "USER_USERNAME"

    val token
        get() = mSharedPreferences.getString(USER_TOKEN, null)

    val userData = Auth.Data(
        null,
        mSharedPreferences.getString(USER_ID, null),
        mSharedPreferences.getString(USER_NAME, null),
        mSharedPreferences.getString(USER_PHONE, null),
        null,
        mSharedPreferences.getString(USER_TOKEN, null),
        mSharedPreferences.getString(USER_USERNAME, null)
    )

    fun storeData(data: Auth.Data) {
        val editor = mSharedPreferences.edit()
        editor.putString(USER_TOKEN, data.token)
        editor.putString(USER_ID, data.id)
        editor.putString(USER_NAME, data.nama)
        editor.putString(USER_PHONE, data.phone)
        editor.putString(USER_USERNAME, data.username)
        editor.apply()
    }

    fun removeToken() {
        val editor = mSharedPreferences.edit()
        editor.remove(USER_TOKEN)
        editor.apply()
    }

    fun logout(mContext: Context) {
        removeToken()
        mContext.startActivity(mContext.intentFor<AuthActivity>().clearTask().newTask())
    }
}