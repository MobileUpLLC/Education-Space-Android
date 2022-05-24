package ru.mobileup.features.home.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.core.utils.createFakeRouterState
import ru.mobileup.features.launchers.ui.upcoming.FakeUpcomingLaunchersComponent
import ru.mobileup.features.launchers.ui.upcoming.UpcomingLaunchersUi
import ru.mobileup.features.settings.ui.SettingsUi
import ru.mobileup.features.R
import ru.mobileup.core.utils.currentInstance
import ru.mobileup.core.message.ui.noOverlapByMessage
import ru.mobileup.features.pin_code.ui.check_pin_code.CheckPinCodeUi
import ru.mobileup.features.solar_system.ui.planets.SolarSystemPlanetsUi

@Composable
fun HomeUi(
    component: HomeComponent,
    modifier: Modifier = Modifier
) {
    val currentChild: HomeComponent.Child = component.routerState.currentInstance
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (component.isBottomBarVisible) {
                BottomBar(currentChild, component::onPageSelected)
            }
        },
        content = { paddingValues ->
            Children(component.routerState, Modifier.padding(paddingValues)) { child ->
                when (val instance = child.instance) {
                    is HomeComponent.Child.CheckPinCode -> CheckPinCodeUi(instance.component)
                    is HomeComponent.Child.UpcomingLaunchers -> UpcomingLaunchersUi(instance.component)
                    is HomeComponent.Child.Settings -> SettingsUi(instance.component)
                    is HomeComponent.Child.SolarSystem -> SolarSystemPlanetsUi(instance.component)
                }
            }
        }
    )
}

@Composable
fun BottomBar(currentChild: HomeComponent.Child, onPageSelected: (HomeComponent.Page) -> Unit) {
    BottomNavigation(
        modifier = Modifier.noOverlapByMessage(),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        NavigationItem(
            iconRes = ru.mobileup.core.R.drawable.ic_24_timer,
            labelRes = R.string.home_upcoming_launchers_name,
            isSelected = currentChild is HomeComponent.Child.UpcomingLaunchers,
            onClick = { onPageSelected(HomeComponent.Page.UpcomingLaunchers) }
        )

        NavigationItem(
            iconRes = ru.mobileup.core.R.drawable.ic_24_home,
            labelRes = R.string.home_upcoming_solar_system_name,
            isSelected = currentChild is HomeComponent.Child.SolarSystem,
            onClick = { onPageSelected(HomeComponent.Page.SolarSystem) }
        )

        NavigationItem(
            iconRes = ru.mobileup.core.R.drawable.ic_24_settings,
            labelRes = R.string.home_settings_name,
            isSelected = currentChild is HomeComponent.Child.Settings,
            onClick = { onPageSelected(HomeComponent.Page.Settings) }
        )
    }
}

@Composable
fun RowScope.NavigationItem(
    @DrawableRes iconRes: Int,
    @StringRes labelRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    BottomNavigationItem(
        icon = { Icon(painterResource(iconRes), contentDescription = null) },
        label = { Text(stringResource(labelRes), maxLines = 1, overflow = TextOverflow.Ellipsis) },
        selected = isSelected,
        onClick = onClick,
        selectedContentColor = MaterialTheme.colors.onBackground,
        unselectedContentColor = MaterialTheme.colors.onSurface
    )
}

@Preview(showSystemUi = true)
@Composable
fun HomeUiPreview() {
    AppTheme {
        HomeUi(FakeHomeComponent())
    }
}

class FakeHomeComponent : HomeComponent {
    override val routerState =
        createFakeRouterState(HomeComponent.Child.UpcomingLaunchers(FakeUpcomingLaunchersComponent()))

    override val isBottomBarVisible: Boolean = false

    override fun onPageSelected(page: HomeComponent.Page) = Unit
}