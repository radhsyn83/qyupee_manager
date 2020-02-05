package id.qyupee.model.order


import com.google.gson.annotations.SerializedName

data class OrderDetail(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: ArrayList<Data>?,
    @SerializedName("msg")
    val msg: String?
) {
    data class Data(
        @SerializedName("harga_jual")
        val hargaJual: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("jumlah")
        val jumlah: String?,
        @SerializedName("kode")
        val kode: String?,
        @SerializedName("total_harga_jual")
        val totalHargaJual: String?,
        @SerializedName("total_profit")
        val totalProfit: String?,
        @SerializedName("ukuran")
        val ukuran: String?,
        @SerializedName("warna")
        val warna: String?
    )
}