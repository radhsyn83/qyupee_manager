package id.qyupee.ui.checkout

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.qyupee.model.DefaultModel
import id.qyupee.model.feed.Feed
import id.qyupee.net.NetworkConfig
import id.qyupee.utils.RetrofitHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutViewModel(val mContext: Context, val token: String?) : ViewModel() {
    var data = MutableLiveData<DefaultModel>()
    var status = MutableLiveData<Boolean>()

    fun add(id_admin: String, customer: String, st: String, id_variant: String, harga_jual: String, qty: String) {
        status.value = true
        NetworkConfig.memberApi(token).checkout(id_admin, customer, st, id_variant, harga_jual, qty).enqueue(object :
            Callback<DefaultModel> {
            override fun onFailure(call: Call<DefaultModel>, t: Throwable) {
                status.value = false
                RetrofitHandler.failure(mContext, t)
            }

            override fun onResponse(call: Call<DefaultModel>, response: Response<DefaultModel>) {
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