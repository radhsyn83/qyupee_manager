package id.qyupee.model.product


import com.google.gson.annotations.SerializedName

data class Variant(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: ArrayList<Data>?,
    @SerializedName("msg")
    val msg: String?
) {
    data class Data(
        @SerializedName("berat")
        val berat: String,
        @SerializedName("harga_jual")
        val hargaJual: Int,
        @SerializedName("harga_produksi")
        val hargaProduksi: Int,
        @SerializedName("id")
        val id: String,
        @SerializedName("id_produk")
        val idProduk: String,
        @SerializedName("id_ukuran")
        val idUkuran: String,
        @SerializedName("id_warna")
        val idWarna: String,
        @SerializedName("publish")
        val publish: String,
        @SerializedName("sku")
        val sku: String,
        @SerializedName("ukuran")
        val ukuran: String,
        @SerializedName("warna")
        val warna: String
    )
}