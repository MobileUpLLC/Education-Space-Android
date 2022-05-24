package ru.mobileup.education_space.app_theme

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arkivanov.essenty.lifecycle.Lifecycle
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.qualifier.named
import org.koin.test.KoinTestRule
import ru.mobileup.core.message.createMessagesComponent
import ru.mobileup.core.theme.ThemeType
import ru.mobileup.education_space.utils.TestComponentContext
import ru.mobileup.education_space.utils.componentFactory
import ru.mobileup.education_space.utils.testKoin
import ru.mobileup.features.app_theme.appThemePrefsName
import ru.mobileup.features.app_theme.createAppThemeComponent
import ru.mobileup.features.app_theme.data.AppThemeStorage
import ru.mobileup.features.app_theme.data.AppThemeStorageImpl

@RunWith(AndroidJUnit4::class)
class AppThemeComponentTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create()

    @Test
    fun `takes initial value from storage`() {
        val koin = koinTestRule.testKoin()
        val componentContext = TestComponentContext()
        val appThemeStorage = koin.get<AppThemeStorage>()
        appThemeStorage.updateDarkThemeEnabled(isEnabled = true)
        val sut = koin.componentFactory.createAppThemeComponent(componentContext)

        componentContext.moveToState(Lifecycle.State.CREATED)
        val actualThemeType = sut.themeType

        Assert.assertEquals(ThemeType.DarkTheme, actualThemeType)
    }

    @Test
    fun `updates data when theme change`() {
        val koin = koinTestRule.testKoin()
        val componentContext = TestComponentContext()
        val appThemeStorage = koin.get<AppThemeStorage>()
        val sut = koin.componentFactory.createAppThemeComponent(componentContext)
        componentContext.moveToState(Lifecycle.State.CREATED)
        appThemeStorage.updateDarkThemeEnabled(isEnabled = true)

        sut.onThemeChange()
        val actualThemeType = sut.themeType

        Assert.assertEquals(ThemeType.DarkTheme, actualThemeType)
    }

    @Test
    fun `does not show error when loading data failed`() {
        val koin = koinTestRule.testKoin()
        val sharedPrefs = koin.get<SharedPreferences>(named(appThemePrefsName))
        sharedPrefs.edit { putString(AppThemeStorageImpl.DARK_THEME_ENABLED_KEY, "") }
        val componentContext = TestComponentContext()
        koin.componentFactory.createAppThemeComponent(componentContext)
        val sut = koin
            .componentFactory
            .createMessagesComponent(componentContext)
        componentContext.moveToState(Lifecycle.State.CREATED)

        val actualMessageData = sut.visibleMessageData

        Assert.assertNull(actualMessageData)
    }
}