package id.rizkyraf.bankmandiri.course.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Error(
    var code: String,
    var message: String
) : Parcelable {
    companion object {
        const val NO_DATA = "404"
        const val NO_INTERNET = "403"
    }
}