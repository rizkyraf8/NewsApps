package id.rizkyraf.bankmandiri.course.service.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsEntity(
    @SerializedName("articles")
    var articles: MutableList<Article> = mutableListOf(),
    @SerializedName("totalResults")
    var totalResults: Int = 0,
) : Parcelable {
    @Parcelize
    data class Article(
        @SerializedName("author")
        var author: String? = "",
        @SerializedName("content")
        var content: String? = "",
        @SerializedName("description")
        var description: String? = "",
        @SerializedName("publishedAt")
        var publishedAt: String? = "",
        @SerializedName("source")
        var source: Source? = Source(),
        @SerializedName("title")
        var title: String? = "",
        @SerializedName("url")
        var url: String? = "",
        @SerializedName("urlToImage")
        var urlToImage: String? = ""
    ) : Parcelable {
        @Parcelize
        data class Source(
            @SerializedName("id")
            var id: String? = "",
            @SerializedName("name")
            var name: String? = ""
        ) : Parcelable
    }
}