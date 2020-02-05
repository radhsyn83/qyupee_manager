package id.qyupee.ui.cart

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.qyupee.model.cart.Cart
import id.qyupee.net.NetworkConfig
import id.qyupee.utils.RetrofitHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel(val mContext: Context, val token: String?) : ViewModel() {
    var data = MutableLiveData<ArrayList<Cart.Data>>()
    var status = MutableLiveData<Boolean>()

    fun load(id_variants: String) {
        status.value = true
        NetworkConfig.memberApi(token).cart(id_variants).enqueue(object :
            Callback<Cart> {
            override fun onFailure(call: Call<Cart>, t: Throwable) {
                status.value = false
                RetrofitHandler.failure(mContext, t)
            }

            override fun onResponse(call: Call<Cart>, response: Response<Cart>) {
                val res = response.body()
                status.value = false
                RetrofitHandler.checkResponse(
                    mContext,
                    response.isSuccessful,
                    res?.code,
                    res?.msg
                ) {
                    data.value = res?.data
                }
            }
        })
    }
}