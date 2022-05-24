package ru.mobileup.core.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.ValueObserver
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

fun <T : Any> createFakeRouterState(instance: T): RouterState<*, T> {
    return RouterState(
        configuration = "<fake>",
        instance = instance
    )
}

fun <T : Any> Value<T>.toComposeState(lifecycle: Lifecycle): State<T> {
    val state: MutableState<T> = mutableStateOf(this.value)

    if (lifecycle.state != Lifecycle.State.DESTROYED) {
        val observer: ValueObserver<T> = { state.value = it }
        subscribe(observer)
        lifecycle.doOnDestroy {
            unsubscribe(observer)
        }
    }

    return state
}

fun LifecycleOwner.componentCoroutineScope(): CoroutineScope {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    if (lifecycle.state != Lifecycle.State.DESTROYED) {
        lifecycle.doOnDestroy {
            scope.cancel()
        }
    } else {
        scope.cancel()
    }

    return scope
}

/**
 * Возвращает элемент (Child) с вершины back-стека роутера.
 * Никогда не null, потому что Decompose не допускает, чтобы стек стал пустым.
 */
val <C : Any, T : Any> RouterState<C, T>.currentInstance get() = activeChild.instance
val <C : Any, T : Any> Router<C, T>.currentInstance get() = state.value.currentInstance

/**
 * Возвращает конфигурацию с вершины back-стека роутера.
 * Никогда не null, потому что Decompose не допускает, чтобы стек стал пустым.
 */
val <C : Any, T : Any> RouterState<C, T>.currentConfiguration get() = activeChild.configuration
val <C : Any, T : Any> Router<C, T>.currentConfiguration get() = state.value.currentConfiguration

/**
 * Заменяет весь стек навигации на новый, состоящий из одного элемента
 */
fun <C : Any> Router<C, *>.replaceAll(configuration: C) {
    navigate { listOf(configuration) }
}