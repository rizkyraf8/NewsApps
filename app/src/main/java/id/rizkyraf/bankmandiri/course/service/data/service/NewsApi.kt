package id.rizkyraf.bankmandiri.course.service.data.service

import id.rizkyraf.bankmandiri.course.service.data.response.NewsEntity
import id.rizkyraf.bankmandiri.course.service.data.response.SourceEntity
import id.rizkyraf.bankmandiri.course.util.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = Constant.COUNTRY,
        @Query("page") pageNumber: Int = 1,
        @Query("sources") sources: String = "",
    ): Response<NewsEntity>

    @GET("everything")
    suspend fun searchForNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1
    ): Response<NewsEntity>

    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("category") category: String = "",
    ): Response<SourceEntity>

}