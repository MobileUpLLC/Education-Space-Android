package ru.mobileup.education_space.pin_code

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arkivanov.essenty.lifecycle.Lifecycle
import ru.mobileup.education_space.utils.TestComponentContext
import ru.mobileup.education_space.utils.componentFactory
import ru.mobileup.education_space.utils.testKoin
import me.aartikov.sesame.localizedstring.LocalizedString
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTestRule
import ru.mobileup.features.pin_code.createPinCodeComponent
import ru.mobileup.features.pin_code.domain.PinCode
import ru.mobileup.features.pin_code.ui.pin_code.PinCodeComponent

@RunWith(AndroidJUnit4::class)
class PinCodeComponentTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create()

    @Test
    fun `fills progress when digit click`() {
        val koin = koinTestRule.testKoin()
        val componentContext = TestComponentContext()
        val sut = koin
            .componentFactory
            .createPinCodeComponent(
                componentContext = componentContext,
                isForgetPinCodeButtonVisible = false,
                isFingerprintButtonVisible = false,
                onOutput = {}
            )
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onDigitClick("1")
        sut.onDigitClick("2")
        sut.onDigitClick("3")
        val actualPinCodeProgress = sut.progress

        Assert.assertEquals(PinCodeComponent.ProgressState.Progress(3), actualPinCodeProgress)
    }

    @Test
    fun `decreases progress when backspace click`() {
        val koin = koinTestRule.testKoin()
        val componentContext = TestComponentContext()
        val sut = koin
            .componentFactory
            .createPinCodeComponent(
                componentContext = componentContext,
                isForgetPinCodeButtonVisible = false,
                isFingerprintButtonVisible = false,
                onOutput = {}
            )
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onDigitClick("1")
        sut.onDigitClick("2")
        sut.onDigitClick("3")
        sut.onBackspaceClick()
        val actualPinCodeProgress = sut.progress

        Assert.assertEquals(PinCodeComponent.ProgressState.Progress(2), actualPinCodeProgress)
    }

    @Test
    fun `sends output when pin code entering complete`() {
        val koin = koinTestRule.testKoin()
        val componentContext = TestComponentContext()
        var actualOutput: PinCodeComponent.Output? = null
        val sut = koin
            .componentFactory
            .createPinCodeComponent(
                componentContext = componentContext,
                isForgetPinCodeButtonVisible = false,
                isFingerprintButtonVisible = false,
                onOutput = { actualOutput = it }
            )
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onDigitClick("1")
        sut.onDigitClick("2")
        sut.onDigitClick("3")
        sut.onDigitClick("4")

        Assert.assertEquals(PinCodeComponent.Output.PinCodeEntered(PinCode("1234")), actualOutput)
    }

    @Test
    fun `shows empty progress when clear input`() {
        val koin = koinTestRule.testKoin()
        val componentContext = TestComponentContext()
        val sut = koin
            .componentFactory
            .createPinCodeComponent(
                componentContext = componentContext,
                isForgetPinCodeButtonVisible = false,
                isFingerprintButtonVisible = false,
                onOutput = {}
            )
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onDigitClick("1")
        sut.onDigitClick("2")
        sut.onDigitClick("3")
        sut.clearInput()
        val actualPinCodeProgress = sut.progress

        Assert.assertEquals(
            PinCodeComponent.ProgressState.Progress(0),
            actualPinCodeProgress
        )
    }

    @Test
    fun `fills progress from starting position after progress failed and digit click`() {
        val koin = koinTestRule.testKoin()
        val componentContext = TestComponentContext()
        val sut = koin
            .componentFactory
            .createPinCodeComponent(
                componentContext = componentContext,
                isForgetPinCodeButtonVisible = false,
                isFingerprintButtonVisible = false,
                onOutput = {}
            )
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onDigitClick("1")
        sut.onDigitClick("2")
        sut.onDigitClick("3")
        sut.showError(LocalizedString.raw("abcd"))
        sut.onDigitClick("4")
        sut.onDigitClick("5")
        val actualPinCodeProgress = sut.progress

        Assert.assertEquals(PinCodeComponent.ProgressState.Progress(2), actualPinCodeProgress)
    }

    @Test
    fun `sends output when fingerprint click`() {
        val koin = koinTestRule.testKoin()
        val componentContext = TestComponentContext()
        var actualOutput: PinCodeComponent.Output? = null
        val sut = koin
            .componentFactory
            .createPinCodeComponent(
                componentContext = componentContext,
                isForgetPinCodeButtonVisible = false,
                isFingerprintButtonVisible = false,
                onOutput = { actualOutput = it }
            )
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onFingerprintClick()

        Assert.assertEquals(PinCodeComponent.Output.FingerprintAuthentication, actualOutput)
    }

    @Test
    fun `sends output when forgot pin code click`() {
        val koin = koinTestRule.testKoin()
        val componentContext = TestComponentContext()
        var actualOutput: PinCodeComponent.Output? = null
        val sut = koin
            .componentFactory
            .createPinCodeComponent(
                componentContext = componentContext,
                isForgetPinCodeButtonVisible = false,
                isFingerprintButtonVisible = false,
                onOutput = { actualOutput = it }
            )
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.onForgotPinCodeClick()

        Assert.assertEquals(PinCodeComponent.Output.PinCodeForgotten, actualOutput)
    }

    @Test
    fun `shows progress error when pin code invalid`() {
        val koin = koinTestRule.testKoin()
        val componentContext = TestComponentContext()
        val sut = koin
            .componentFactory
            .createPinCodeComponent(
                componentContext = componentContext,
                isForgetPinCodeButtonVisible = false,
                isFingerprintButtonVisible = false,
                onOutput = {}
            )
        componentContext.moveToState(Lifecycle.State.RESUMED)

        sut.showError(LocalizedString.raw("abcd"))
        val actualPinCodeProgress = sut.progress

        Assert.assertEquals(
            PinCodeComponent.ProgressState.Error(LocalizedString.raw("abcd")),
            actualPinCodeProgress
        )
    }
}