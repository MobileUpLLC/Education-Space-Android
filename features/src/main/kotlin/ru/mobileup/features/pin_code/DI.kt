package ru.mobileup.features.pin_code

import com.arkivanov.decompose.ComponentContext
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.get
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.storage.SharedPreferencesFactory
import ru.mobileup.features.pin_code.data.PinCodeStorage
import ru.mobileup.features.pin_code.data.PinCodeStorageImpl
import ru.mobileup.features.pin_code.domain.CheckPinCodeInteractor
import ru.mobileup.features.pin_code.domain.ClearPinCodeInteractor
import ru.mobileup.features.pin_code.domain.IsPinCodeSetInteractor
import ru.mobileup.features.pin_code.domain.SavePinCodeInteractor
import ru.mobileup.features.pin_code.ui.change_pin_code.ChangePinCodeComponent
import ru.mobileup.features.pin_code.ui.change_pin_code.RealChangePinCodeComponent
import ru.mobileup.features.pin_code.ui.check_pin_code.CheckPinCodeComponent
import ru.mobileup.features.pin_code.ui.check_pin_code.CheckPinCodeMode
import ru.mobileup.features.pin_code.ui.check_pin_code.RealCheckPinCodeComponent
import ru.mobileup.features.pin_code.ui.create_pin_code.CreatePinCodeComponent
import ru.mobileup.features.pin_code.ui.create_pin_code.CreatePinCodeMode
import ru.mobileup.features.pin_code.ui.create_pin_code.RealCreatePinCodeComponent
import ru.mobileup.features.pin_code.ui.pin_code.PinCodeComponent
import ru.mobileup.features.pin_code.ui.pin_code.RealPinCodeComponent
import ru.mobileup.features.pin_code.ui.pin_code_protection.PinCodeProtectionComponent
import ru.mobileup.features.pin_code.ui.pin_code_protection.RealPinCodeProtectionComponent

val pinCodeModule = module {
    val alias = "pin_code_alias"
    val pinCodePrefsName = "pin_code_preferences"

    single(named(pinCodePrefsName)) {
        get<SharedPreferencesFactory>().createEncryptedPreferences(
            androidContext(),
            fileName = pinCodePrefsName,
            alias = alias
        )
    }
    single<PinCodeStorage> { PinCodeStorageImpl(get(named(pinCodePrefsName))) }
    factory { SavePinCodeInteractor(get()) }
    factory { IsPinCodeSetInteractor(get()) }
    factory { CheckPinCodeInteractor(get()) }
    factory { ClearPinCodeInteractor(get()) }
}

fun ComponentFactory.createCreatingPinCodeComponent(
    componentContext: ComponentContext,
    onOutput: (CreatePinCodeComponent.Output) -> Unit,
    mode: CreatePinCodeMode
): CreatePinCodeComponent {
    return RealCreatePinCodeComponent(componentContext, get(), onOutput, mode, get(), get())
}

fun ComponentFactory.createCheckPinCodeComponent(
    componentContext: ComponentContext,
    onOutput: (CheckPinCodeComponent.Output) -> Unit,
    isForgetPinCodeButtonVisible: Boolean = false,
    isFingerprintButtonVisible: Boolean = true,
    isAttemptsCountCheck: Boolean = false,
    mode: CheckPinCodeMode
): CheckPinCodeComponent {
    return RealCheckPinCodeComponent(
        componentContext,
        isForgetPinCodeButtonVisible,
        isFingerprintButtonVisible,
        isAttemptsCountCheck,
        onOutput,
        mode,
        get(),
        get(),
        get(),
        get(),
        get()
    )
}

fun ComponentFactory.createPinCodeComponent(
    componentContext: ComponentContext,
    onOutput: (PinCodeComponent.Output) -> Unit,
    isForgetPinCodeButtonVisible: Boolean = false,
    isFingerprintButtonVisible: Boolean = false
): PinCodeComponent {
    return RealPinCodeComponent(
        componentContext,
        isForgetPinCodeButtonVisible,
        isFingerprintButtonVisible,
        onOutput
    )
}

fun ComponentFactory.createChangePinCodeComponent(
    componentContext: ComponentContext,
    onOutput: (ChangePinCodeComponent.Output) -> Unit
): ChangePinCodeComponent {
    return RealChangePinCodeComponent(componentContext, onOutput, get(), get())
}

fun ComponentFactory.createPinCodeProtectionComponent(
    componentContext: ComponentContext,
    onOutput: (PinCodeProtectionComponent.Output) -> Unit
): PinCodeProtectionComponent {
    return RealPinCodeProtectionComponent(componentContext, onOutput, get())
}