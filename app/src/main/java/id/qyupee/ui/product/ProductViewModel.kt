package id.qyupee.ui.product

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.qyupee.model.product.Product
import id.qyupee.model.product.Variant
import id.qyupee.model.product.VariantList
import id.qyupee.net.NetworkConfig
import id.qyupee.utils.RetrofitHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel(val mContext: Context, val token: String?) : ViewModel() {
    var data = MutableLiveData<ArrayList<Product.Data>>()
    var status = MutableLiveData<Boolean>()
    var variant = MutableLiveData<ArrayList<Variant.Data>>()
    var statusVariant = MutableLiveData<Boolean>()
    var variantList = MutableLiveData<ArrayList<VariantList.Data>>()
    var statusVariantList = MutableLiveData<Boolean>()

    fun load() {
        status.value = true
        NetworkConfig.memberApi(token).product().enqueue(object :
            Callback<Product> {
            override fun onFailure(call: Call<Product>, t: Throwable) {
                status.value = false
                RetrofitHandler.failure(mContext, t)
            }

            override fun onResponse(call: Call<Product>, response: Response<Product>) {
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

    fun loadVariant(idProduct: String) {
        statusVariant.value = true
        NetworkConfig.memberApi(token).variant(idProduct).enqueue(object :
            Callback<Variant> {
            override fun onFailure(call: Call<Variant>, t: Throwable) {
                statusVariant.value = false
                RetrofitHandler.failure(mContext, t)
            }

            override fun onResponse(call: Call<Variant>, response: Response<Variant>) {
                val res = response.body()
                statusVariant.value = false
                RetrofitHandler.checkResponse(
                    mContext,
                    response.isSuccessful,
                    res?.code,
                    res?.msg
                ) {
                    variant.value = res?.data
                }
            }
        })
    }

    fun loadVariant() {
        statusVariant.value = true
        NetworkConfig.memberApi(token).variant().enqueue(object :
            Callback<VariantList> {
            override fun onFailure(call: Call<VariantList>, t: Throwable) {
                statusVariant.value = false
                RetrofitHandler.failure(mContext, t)
            }

            override fun onResponse(call: Call<VariantList>, response: Response<VariantList>) {
                val res = response.body()
                statusVariant.value = false
                RetrofitHandler.checkResponse(
                    mContext,
                    response.isSuccessful,
                    res?.code,
                    res?.msg
                ) {
                    variantList.value = res?.data
                }
            }
        })
    }


}