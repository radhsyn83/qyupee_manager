package id.qyupee.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DefaultModel(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("msg")
    var msg: String?
) : Parcelable