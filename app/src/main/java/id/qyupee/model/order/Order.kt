package id.qyupee.model.order


import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: ArrayList<Data>?,
    @SerializedName("msg")
    val msg: String?
) {
    data class Data(
        @SerializedName("status")
        val status: String?,
        @SerializedName("date_add")
        val dateAdd: String?,
        @SerializedName("customer")
        val customer: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("total_harga")
        val totalHarga: String?,
        @SerializedName("total_profit")
        val totalProfit: String?,
        @SerializedName("total_qty")
        val totalQty: String?
    )
}