package id.qyupee.model.product


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: ArrayList<Data>?,
    @SerializedName("msg")
    val msg: String?
) {
    data class Data(
        @SerializedName("id")
        val id: String,
        @SerializedName("nama")
        val nama: String,
        @SerializedName("sku")
        val sku: String,
        @SerializedName("thumbnail")
        val thumbnail: String?
    )
}