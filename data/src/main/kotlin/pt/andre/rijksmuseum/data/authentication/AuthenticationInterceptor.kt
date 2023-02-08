package pt.andre.rijksmuseum.data.authentication

import okhttp3.Interceptor
import okhttp3.Response
import pt.andre.rijksmuseum.data.network.ApiKey
import javax.inject.Inject

private const val AUTHENTICATION_PARAM = "key"

internal class AuthenticationInterceptor @Inject constructor(
    @ApiKey private val key: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url
            .newBuilder()
            .addQueryParameter(AUTHENTICATION_PARAM, key)
            .build()

        val authenticatedRequest = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(authenticatedRequest)
    }
}
