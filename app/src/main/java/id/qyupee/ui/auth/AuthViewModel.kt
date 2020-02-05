package id.qyupee.ui.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.qyupee.model.auth.Auth
import id.qyupee.net.NetworkConfig
import id.qyupee.utils.RetrofitHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(var mContext: Context) : ViewModel() {

    var data = MutableLiveData<Auth>()
    var status = MutableLiveData<Boolean>()

    fun login(phone: String, password: String) {
        status.value = true
        NetworkConfig.api().auth(phone, password).enqueue(object :
            Callback<Auth> {
            override fun onFailure(call: Call<Auth>, t: Throwable) {
                status.value = false
                RetrofitHandler.failure(mContext, t)
            }

            override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                val res = response.body()
                status.value = false
                RetrofitHandler.checkResponse(
                    mContext,
                    response.isSuccessful,
                    res?.code,
                    res?.msg
                ) {
                    data.value = res
                }
            }
        })
    }
}