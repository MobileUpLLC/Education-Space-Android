package ru.mobileup.features.app_theme.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.theme.ThemeType
import ru.mobileup.core.utils.componentCoroutineScope
import ru.mobileup.core.utils.toComposeState
import ru.mobileup.features.app_theme.domain.IsDarkThemeEnabledInteractor
import ru.mobileup.core.utils.handleErrors
import me.aartikov.sesame.loading.simple.OrdinaryLoading
import me.aartikov.sesame.loading.simple.dataOrNull
import me.aartikov.sesame.loading.simple.refresh

class RealAppThemeComponent(
    componentContext: ComponentContext,
    private val isDarkThemeEnabledInteractor: IsDarkThemeEnabledInteractor,
    private val errorHandler: ErrorHandler
) : ComponentContext by componentContext, AppThemeComponent {

    private val coroutineScope = componentCoroutineScope()

    private val themeTypeLoading = OrdinaryLoading(
        scope = coroutineScope,
        load = { isDarkThemeEnabledInteractor.execute() }
    )
    private val themeTypeState by themeTypeLoading.stateFlow.toComposeState(
        coroutineScope
    )

    override val themeType by derivedStateOf {
        when (themeTypeState.dataOrNull) {
            false -> ThemeType.LightTheme
            true -> ThemeType.DarkTheme
            else -> ThemeType.Default
        }
    }

    init {
        lifecycle.doOnCreate {
            themeTypeLoading.handleErrors(
                scope = coroutineScope,
                errorHandler = errorHandler,
                showError = false
            )
            themeTypeLoading.refresh()
        }
    }

    override fun onThemeChange() {
        themeTypeLoading.refresh()
    }
}