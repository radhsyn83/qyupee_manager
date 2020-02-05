package id.qyupee.ui.order

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.qyupee.model.DefaultModel
import id.qyupee.model.feed.Feed
import id.qyupee.model.order.Order
import id.qyupee.model.order.OrderDetail
import id.qyupee.net.NetworkConfig
import id.qyupee.utils.RetrofitHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderViewModel(val mContext: Context, val token: String?) : ViewModel() {
    var data = MutableLiveData<ArrayList<Order.Data>>()
    var detail = MutableLiveData<ArrayList<OrderDetail.Data>>()
    var status = MutableLiveData<Boolean>()

    fun load() {
        status.value = true
        NetworkConfig.memberApi(token).order().enqueue(object :
            Callback<Order> {
            override fun onFailure(call: Call<Order>, t: Throwable) {
                status.value = false
                RetrofitHandler.failure(mContext, t)
            }

            override fun onResponse(call: Call<Order>, response: Response<Order>) {
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

    fun detail(idOrder: String) {
        status.value = true
        NetworkConfig.memberApi(token).order(idOrder).enqueue(object :
            Callback<OrderDetail> {
            override fun onFailure(call: Call<OrderDetail>, t: Throwable) {
                status.value = false
                RetrofitHandler.failure(mContext, t)
            }

            override fun onResponse(call: Call<OrderDetail>, response: Response<OrderDetail>) {
                val res = response.body()
                status.value = false
                RetrofitHandler.checkResponse(
                    mContext,
                    response.isSuccessful,
                    res?.code,
                    res?.msg
                ) {
                    detail.value = res?.data
                }
            }
        })
    }
}