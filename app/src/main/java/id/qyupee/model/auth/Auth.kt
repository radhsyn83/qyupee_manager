package id.qyupee.model.auth


import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("msg")
    val msg: String?
) {
    data class Data(
        @SerializedName("email")
        val email: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("nama")
        val nama: String?,
        @SerializedName("phone")
        val phone: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("token")
        val token: String?,
        @SerializedName("username")
        val username: String?
    ) {
        override fun toString(): String {
            return "Data(email=$email, id=$id, nama=$nama, phone=$phone, status=$status, token=$token, username=$username)"
        }
    }
}