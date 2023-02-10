package pt.andre.rijksmuseum.domain.utilities

sealed class Result<out T> {
    data class Success<out R>(val value: R) : Result<R>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()

    fun isSuccess(): Boolean = this is Success

    fun isFailure(): Boolean = this is Failure

    fun isLoading(): Boolean = this is Loading
}

fun <F, T> Result<F>.mapSuccess(transformation: (item: F) -> Result<T>): Result<T> {
    return when (this) {
        is Result.Success -> transformation(value)
        is Result.Failure -> exception.asFailure()
        is Result.Loading -> Result.Loading
    }
}

fun <T> T.asSuccess(): Result.Success<T> = Result.Success(this)

fun Throwable.asFailure(): Result.Failure = Result.Failure(this)
