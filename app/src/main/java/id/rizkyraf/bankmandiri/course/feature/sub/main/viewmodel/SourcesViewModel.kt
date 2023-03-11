package id.rizkyraf.bankmandiri.course.feature.sub.main.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import id.rizkyraf.bankmandiri.course.service.data.repository.NewsRepository
import id.rizkyraf.bankmandiri.course.service.data.response.SourceEntity
import id.rizkyraf.bankmandiri.course.util.NetworkUtil.Companion.hasInternetConnection
import id.rizkyraf.bankmandiri.course.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    @ApplicationContext context: Context
) : ViewModel() {

    private val mContext by lazy {
        context
    }

    val sourceNews: MutableLiveData<Resource<SourceEntity>> = MutableLiveData()
    var sourceNewsPage = 1
    var sourceNewsResponse: SourceEntity? = null


    fun getSourceNews(category: String) = viewModelScope.launch {
        safeSourceNewsCall(category)
    }

    private suspend fun safeSourceNewsCall(category: String) {
        sourceNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(mContext)) {
                val response = newsRepository.getSource(category)
                sourceNews.postValue(handleSourceNewsResponse(response))
            } else {
                sourceNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (ex: Exception) {
            when (ex) {
                is IOException -> sourceNews.postValue(Resource.Error("Network Failure"))
                else -> sourceNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleSourceNewsResponse(response: Response<SourceEntity>): Resource<SourceEntity> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                sourceNewsPage++
                if (sourceNewsResponse == null) sourceNewsResponse = resultResponse
                return Resource.Success(sourceNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}