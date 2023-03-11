package id.rizkyraf.bankmandiri.course.feature.sub.main.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import id.rizkyraf.bankmandiri.course.service.data.repository.NewsRepository
import id.rizkyraf.bankmandiri.course.service.data.request.BreakningNewRequest
import id.rizkyraf.bankmandiri.course.service.data.response.NewsEntity
import id.rizkyraf.bankmandiri.course.util.NetworkUtil.Companion.hasInternetConnection
import id.rizkyraf.bankmandiri.course.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    @ApplicationContext context: Context
) : ViewModel() {

    private val mContext by lazy {
        context
    }

    val breakingNews: MutableLiveData<Resource<NewsEntity>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsEntity? = null

    fun getBreakingNews(countryCode: String = "", source: String = "") = viewModelScope.launch {
        safeBreakingNewsCall(countryCode, source)
    }

    private suspend fun safeBreakingNewsCall(countryCode: String, source: String) {
        breakingNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(mContext)) {
                val response = newsRepository.getBreakingNews(
                    BreakningNewRequest(
                        countryCode, breakingNewsPage, source
                    )
                )
                breakingNews.postValue(handleBreakingNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (ex: Exception) {
            when (ex) {
                is IOException -> breakingNews.postValue(Resource.Error("Network Failure"))
                else -> breakingNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleBreakingNewsResponse(response: Response<NewsEntity>): Resource<NewsEntity> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null) breakingNewsResponse = resultResponse
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}