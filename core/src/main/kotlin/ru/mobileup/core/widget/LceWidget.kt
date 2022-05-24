package ru.mobileup.core.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.aartikov.sesame.loading.paged.PagedLoading
import me.aartikov.sesame.loading.simple.Loading
import ru.mobileup.core.error_handling.errorMessage
import ru.mobileup.core.utils.resolve

@Composable
fun <T> LceWidget(
    data: Loading.State<T>,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    emptyContent: @Composable (() -> Unit)? = null,
    content: @Composable (data: T) -> Unit
) {
    LceWidget(
        data = data,
        onRetryClick = onRetryClick,
        modifier = modifier,
        emptyContent = emptyContent
    ) { result, _ ->
        content(result)
    }
}

@Composable
fun <T> LceWidget(
    data: Loading.State<T>,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    emptyContent: @Composable (() -> Unit)? = null,
    content: @Composable (data: T, refreshing: Boolean) -> Unit
) {
    when (data) {
        is Loading.State.Error -> ErrorPlaceholder(
            modifier = modifier,
            errorMessage = data.throwable.errorMessage.resolve(),
            onRetryClick = onRetryClick
        )

        is Loading.State.Loading -> FullscreenCircularProgress(modifier = modifier)

        is Loading.State.Empty -> {
            if (emptyContent != null) {
                emptyContent()
            }
        }

        is Loading.State.Data -> {
            content(data.data, data.refreshing)
        }
    }
}

@Composable
fun <T> PagedLceWidget(
    data: PagedLoading.State<T>,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    emptyContent: @Composable (() -> Unit)? = null,
    loadingContent: (@Composable () -> Unit)? = {
        FullscreenCircularProgress(
            modifier = modifier
        )
    },
    content: @Composable (PagedLoading.State.Data<T>) -> Unit,
) {
    when (data) {
        is PagedLoading.State.Error -> ErrorPlaceholder(
            modifier = modifier,
            errorMessage = data.throwable.errorMessage.resolve(),
            onRetryClick = onRetryClick
        )

        is PagedLoading.State.Loading -> loadingContent?.invoke()

        is PagedLoading.State.Empty -> {
            if (emptyContent != null) {
                emptyContent()
            }
        }

        is PagedLoading.State.Data -> content(data)
    }
}