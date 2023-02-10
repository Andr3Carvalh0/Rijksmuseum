package pt.andre.rijksmuseum.data.mapper

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pt.andre.rijksmuseum.domain.exceptions.AuthenticationException
import pt.andre.rijksmuseum.domain.exceptions.EmptyResponseException
import pt.andre.rijksmuseum.domain.exceptions.ServerException
import pt.andre.rijksmuseum.domain.utilities.Result
import pt.andre.rijksmuseum.domain.utilities.asFailure
import pt.andre.rijksmuseum.domain.utilities.asSuccess
import retrofit2.Response

@Suppress("TooGenericExceptionCaught")
internal suspend inline fun <reified T> mapResult(
    crossinline networkCall: suspend () -> Response<T>,
    dispatcher: CoroutineDispatcher
): Result<T> = withContext(dispatcher) {
    try {
        networkCall().mapResult()
    } catch (e: Exception) {
        e.asFailure()
    }
}

internal fun <T> Response<T>.mapResult(): Result<T> {
    return when {
        isSuccessful -> {
            body()?.asSuccess() ?: EmptyResponseException().asFailure()
        }
        else -> code().toException().asFailure()
    }
}

@Suppress("MagicNumber")
private fun Int.toException() = when (this) {
    in 401..403 -> AuthenticationException()
    in 500..511 -> ServerException()
    else -> IllegalArgumentException("Something went wrong: code($this)")
}
