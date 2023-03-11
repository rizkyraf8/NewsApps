package id.rizkyraf.bankmandiri.course.service.utils.intercept

import id.rizkyraf.bankmandiri.course.util.Constant
import okhttp3.Interceptor
import okhttp3.Response

class RequestHeaderInterceptor : Interceptor {
    private val TAG = this::class.java.simpleName
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var builder = request.newBuilder()

        builder = builder.addHeader(X_API_KEY, Constant.API_KEY)
        request = builder.build()

        return chain.proceed(request)
    }

    companion object {
        const val X_API_KEY = "X-Api-Key"
    }
}
