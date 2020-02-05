package id.qyupee.ui.feed

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

class FeedViewModel(val mContext: Context, val token: String?) : ViewModel() {
    var data = MutableLiveData<ArrayList<Feed.Data>>()
    var addFeed = MutableLiveData<DefaultModel>()
    var status = MutableLiveData<Boolean>()

    fun load() {
        status.value = true
        NetworkConfig.memberApi(token).feed().enqueue(object :
            Callback<Feed> {
            override fun onFailure(call: Call<Feed>, t: Throwable) {
                status.value = false
                RetrofitHandler.failure(mContext, t)
            }

            override fun onResponse(call: Call<Feed>, response: Response<Feed>) {
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

    fun add(jenis: String, note: String, nominal: String, jml_stok: String = "0", id_variant: String = "0") {
        status.value = true
        NetworkConfig.memberApi(token).addFeed(jenis, note, nominal, jml_stok, id_variant).enqueue(object :
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
                    addFeed.value = res
                }
            }
        })
    }
}