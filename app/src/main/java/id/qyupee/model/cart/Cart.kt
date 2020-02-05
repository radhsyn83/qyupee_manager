package id.qyupee.model.cart


import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: ArrayList<Data>?,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("id_variant")
        val idVariant: String,
        @SerializedName("id_produk")
        val idProduk: String,
        @SerializedName("kode")
        val kode: String,
        @SerializedName("nama")
        val nama: String,
        @SerializedName("thumbnail")
        val thumbnail: String?,
        @SerializedName("ukuran")
        val ukuran: String,
        @SerializedName("warna")
        val warna: String
    ) {
        override fun toString(): String {
            return "Data(idVariant='$idVariant', idProduk='$idProduk', kode='$kode', nama='$nama', thumbnail=$thumbnail, ukuran='$ukuran', warna='$warna')"
        }
    }
}