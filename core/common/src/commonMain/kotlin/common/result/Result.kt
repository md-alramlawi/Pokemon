package common.result

sealed interface Result<out R> {
    data class Success<out T>(val data: T) : Result<T>
    data class Error(val exception: Exception) : Result<Nothing>
    data object Loading : Result<Nothing>
}

inline fun <T, R> Result<T>.mapSuccess(transform: (T) -> R): Result<R> {
    return when (this) {
        is Result.Error -> Result.Error(this.exception)
        is Result.Loading -> Result.Loading
        is Result.Success -> Result.Success(transform(this.data))
    }
}

inline fun <T, R> Result<T>.flatMapSuccess(transform: (T) -> Result<R>): Result<R> {
    return when (this) {
        is Result.Error -> Result.Error(this.exception)
        is Result.Loading -> Result.Loading
        is Result.Success -> {
            when (val result = transform(this.data)) {
                is Result.Error -> Result.Error(result.exception)
                Result.Loading -> Result.Loading
                is Result.Success -> Result.Success(result.data)
            }
        }
    }
}

inline fun <R> Result<R>.mapError(transform: (Exception) -> Unit): Result<R> {
    if (this is Result.Error) {
        transform(this.exception)
    }
    return this
}

class NoMoreException(message: String) : Exception(message)