package id.qyupee.model.product


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class VariantList(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: ArrayList<Data>?,
    @SerializedName("msg")
    val msg: String
) {
    @Parcelize
    data class Data (
        @SerializedName("harga_produksi")
        val hargaProduksi: Int,
        @SerializedName("id")
        val id: String,
        @SerializedName("nama")
        val nama: String,
        @SerializedName("kode")
        val kode: String,
        @SerializedName("ukuran")
        val ukuran: String
    ) : Parcelable
}