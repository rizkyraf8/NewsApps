package id.rizkyraf.bankmandiri.course.service.data.repository

import id.rizkyraf.bankmandiri.course.service.data.request.BreakningNewRequest
import id.rizkyraf.bankmandiri.course.service.data.request.SearchNewRequest
import id.rizkyraf.bankmandiri.course.service.data.response.NewsEntity
import id.rizkyraf.bankmandiri.course.service.data.response.SourceEntity
import id.rizkyraf.bankmandiri.course.service.data.service.NewsApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsApi: NewsApi
) {

    suspend fun getBreakingNews(params: BreakningNewRequest): Response<NewsEntity> {
        return newsApi.getBreakingNews(params.countryCode, params.pageNumber, params.source)
    }

    suspend fun getSearchNews(params: SearchNewRequest): Response<NewsEntity> {
        return newsApi.searchForNews(params.query, params.pageNumber)
    }

    suspend fun getSource(category: String): Response<SourceEntity> {
        return newsApi.getSources(category)
    }
}