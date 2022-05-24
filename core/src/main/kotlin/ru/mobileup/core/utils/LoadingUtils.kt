package ru.mobileup.core.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import me.aartikov.sesame.loading.paged.PagedLoading
import me.aartikov.sesame.loading.paged.handleErrors
import me.aartikov.sesame.loading.simple.Loading
import me.aartikov.sesame.loading.simple.handleErrors
import ru.mobileup.core.error_handling.ErrorHandler
import timber.log.Timber
import kotlin.reflect.KMutableProperty0

fun <T : Any> Loading<T>.handleErrors(
    scope: CoroutineScope,
    errorHandler: ErrorHandler,
    showError: Boolean = true,
    onErrorHandled: ((e: Throwable) -> Unit)? = null
): Job {
    return handleErrors(scope) {
        errorHandler.handleLoadingError(it.throwable, showError)
        onErrorHandled?.invoke(it.throwable)
    }
}

fun <T : Any> PagedLoading<T>.handleErrors(
    scope: CoroutineScope,
    errorHandler: ErrorHandler,
    showError: Boolean = true,
    onErrorHandled: ((e: Throwable) -> Unit)? = null
): Job {
    return handleErrors(scope) {
        errorHandler.handleLoadingError(it.throwable, showError)
        onErrorHandled?.invoke(it.throwable)
    }
}

private fun ErrorHandler.handleLoadingError(throwable: Throwable, hasData: Boolean) {
    if (hasData) {
        handleError(throwable)
    } else {
        Timber.e(throwable)
    }
}

suspend fun withProgress(
    progressProperty: KMutableProperty0<Boolean>,
    block: suspend () -> Unit
) {
    try {
        progressProperty.set(true)
        block()
    } finally {
        progressProperty.set(false)
    }
}

/**
 * Возвращает [PagedLoading.State.Data] если это возможно, в противном случае вернет null
 */
val <T : Any> PagedLoading.State<T>.dataStateOrNull: PagedLoading.State.Data<T>? get() = this as? PagedLoading.State.Data