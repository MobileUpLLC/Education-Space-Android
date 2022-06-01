package ru.mobileup.education_space.settings

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.runBlocking
import me.aartikov.sesame.dialog.DialogControl
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTestRule
import ru.mobileup.core.widget.dialog.DialogResult
import ru.mobileup.core.widget.dialog.dataOrNull
import ru.mobileup.education_space.utils.OutputCaptor
import ru.mobileup.education_space.utils.TestComponentContext
import ru.mobileup.education_space.utils.componentFactory
import ru.mobileup.education_space.utils.testKoin
import ru.mobileup.features.app_theme.data.AppThemeStorage
import ru.mobileup.features.pin_code.data.PinCodeStorage
import ru.mobileup.features.settings.createSettingsComponent
import ru.mobileup.features.settings.ui.SettingsComponent
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class SettingsComponentTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create()

    @Test
    fun `takes initial value from storage`() {
        val koin = koinTestRule.testKoin()
        val appThemeStorage = koin.get<AppThemeStorage>()
        appThemeStorage.updateDarkThemeEnabled(isEnabled = true)
        val componentContext = TestComponentContext()
        val sut = koin.componentFactory.createSettingsComponent(componentContext) {}
        componentContext.moveToState(Lifecycle.State.RESUMED)

        val actualDarkThemeEnabled = sut.darkThemeEnabled

        assertEquals(expected = true, actual = actualDarkThemeEnabled)
    }

    @Test
    fun `shows dialog when exit click`() {
        val koin = koinTestRule.testKoin()

        val componentContext = TestComponentContext()
        val sut = koin.componentFactory.createSettingsComponent(componentContext) {}
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onExitClick()
        val actualLogoutDialogControl = sut.logoutDialogControl

        assertTrue(actualLogoutDialogControl.stateFlow.value is DialogControl.State.Shown<*>)
        assertEquals(FakeSettings.logoutAlertDialog, actualLogoutDialogControl.dataOrNull)
    }

    @Test
    fun `hides dialog when cancel click in dialog`() {
        val koin = koinTestRule.testKoin()
        val componentContext = TestComponentContext()
        val sut = koin.componentFactory.createSettingsComponent(componentContext) {}
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onExitClick()
        sut.logoutDialogControl.sendResult(DialogResult.Cancel)
        val actualLogoutDialogControl = sut.logoutDialogControl

        assertTrue(actualLogoutDialogControl.stateFlow.value is DialogControl.State.Hidden)
    }

    @Test
    fun `clears data when exit click in dialog`() {
        val koin = koinTestRule.testKoin()
        val pinCodeStorage = koin.get<PinCodeStorage>()
        val componentContext = TestComponentContext()
        val sut = koin.componentFactory.createSettingsComponent(componentContext) {}
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onExitClick()
        sut.logoutDialogControl.sendResult(DialogResult.Confirm)
        val actualPinCode = runBlocking { pinCodeStorage.getPinCode() }

        assertNull(actualPinCode)
    }

    @Test
    fun `sends output when exit click in dialog`() {
        val koin = koinTestRule.testKoin()
        val outputCaptor = OutputCaptor<SettingsComponent.Output>()
        val componentContext = TestComponentContext()
        val sut = koin.componentFactory.createSettingsComponent(componentContext, outputCaptor)
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onExitClick()
        sut.logoutDialogControl.sendResult(DialogResult.Confirm)

        assertEquals(
            expected = listOf(SettingsComponent.Output.LoggedOut),
            actual = outputCaptor.outputs
        )
    }

    @Test
    fun `sends output when pin code click`() {
        val koin = koinTestRule.testKoin()
        val outputCaptor = OutputCaptor<SettingsComponent.Output>()
        val componentContext = TestComponentContext()
        val sut = koin.componentFactory.createSettingsComponent(componentContext, outputCaptor)
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onPinCodeClick()

        assertEquals(
            expected = listOf(SettingsComponent.Output.PinCodeChangingRequested),
            actual = outputCaptor.outputs
        )
    }

    @Test
    fun `update data when dark theme settings click`() {
        val koin = koinTestRule.testKoin()
        val componentContext = TestComponentContext()
        val sut = koin.componentFactory.createSettingsComponent(componentContext) {}
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onDarkThemeSettingsChecked()
        val actualDarkThemeEnabled = sut.darkThemeEnabled

        assertEquals(true, actualDarkThemeEnabled)
    }

    @Test
    fun `sends output when dark theme settings click`() {
        val koin = koinTestRule.testKoin()
        val outputCaptor = OutputCaptor<SettingsComponent.Output>()
        val componentContext = TestComponentContext()
        val sut = koin.componentFactory.createSettingsComponent(componentContext, outputCaptor)
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onDarkThemeSettingsChecked()

        assertEquals(
            expected = listOf(SettingsComponent.Output.ThemeChanged),
            actual = outputCaptor.outputs
        )
    }
}