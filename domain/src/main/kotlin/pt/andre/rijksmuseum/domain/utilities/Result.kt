package pt.andre.rijksmuseum.domain.utilities

sealed class Result<out T> {
    data class Success<out R>(val value: R) : Result<R>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()

    fun isSuccess(): Boolean = this is Success

    fun isFailure(): Boolean = this is Failure

    fun isLoading(): Boolean = this is Loading
}

fun <F, T> Result<F>.mapSuccess(transformation: (item: F) -> T): Result<T> {
    return when (this) {
        is Result.Success -> Result.Success(transformation(value))
        is Result.Failure -> Result.Failure(exception)
        is Result.Loading -> Result.Loading
    }
}
