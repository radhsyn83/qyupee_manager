package id.qyupee.model.feed


import com.google.gson.annotations.SerializedName

data class Feed(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: ArrayList<Data>?,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("add_by")
        val addBy: String,
        @SerializedName("date_add")
        val dateAdd: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("id_variant")
        val idVariant: String,
        @SerializedName("detail_variant")
        val detailVariant: FeedVariant?,
        @SerializedName("jenis")
        val jenis: Int,
        @SerializedName("jumlah_stok")
        val jumlahStok: String,
        @SerializedName("nominal")
        val nominal: String,
        @SerializedName("note")
        val note: String
    ) {
        data class FeedVariant(
            val kode: String,
            val nama: String,
            val ukuran: String
        )
    }
}