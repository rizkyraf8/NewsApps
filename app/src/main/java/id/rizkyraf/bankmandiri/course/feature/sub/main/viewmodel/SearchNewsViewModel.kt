package id.rizkyraf.bankmandiri.course.feature.sub.main.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import id.rizkyraf.bankmandiri.course.service.data.repository.NewsRepository
import id.rizkyraf.bankmandiri.course.service.data.request.SearchNewRequest
import id.rizkyraf.bankmandiri.course.service.data.response.NewsEntity
import id.rizkyraf.bankmandiri.course.util.NetworkUtil.Companion.hasInternetConnection
import id.rizkyraf.bankmandiri.course.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    @ApplicationContext context: Context
) : ViewModel() {

    private val mContext by lazy {
        context
    }

    val searchNews: MutableLiveData<Resource<NewsEntity>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsEntity? = null

    fun getSearchNews(query: String) = viewModelScope.launch {
        safeSearchNewsCall(query)
    }

    private suspend fun safeSearchNewsCall(query: String) {
        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(mContext)) {
                val response = newsRepository.getSearchNews(
                    SearchNewRequest(
                        query, searchNewsPage
                    )
                )
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (ex: Exception) {
            when (ex) {
                is IOException -> searchNews.postValue(Resource.Error("Network Failure"))
                else -> searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleSearchNewsResponse(response: Response<NewsEntity>): Resource<NewsEntity> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null) searchNewsResponse = resultResponse
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}