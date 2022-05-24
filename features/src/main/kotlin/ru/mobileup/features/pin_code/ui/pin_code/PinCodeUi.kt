package ru.mobileup.features.pin_code.ui.pin_code

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mobileup.features.R
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.core.utils.resolve
import ru.mobileup.features.pin_code.domain.PinCode
import me.aartikov.sesame.localizedstring.LocalizedString

@Composable
fun PinCodeUi(
    component: PinCodeComponent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        PinCodeProgress(state = component.progress)
        Spacer(modifier = Modifier.height(48.dp))
        PinCodeKeyboard(
            onDigitClick = component::onDigitClick,
            onBackspaceClick = component::onBackspaceClick,
            onForgotClick = component::onForgotPinCodeClick,
            onFingerprintClick = component::onFingerprintClick,
            isBackspaceVisible = component.isBackspaceVisible,
            isForgotVisible = component.isForgotButtonVisible,
            isFingerprintVisible = component.isFingerprintButtonVisible
        )
    }
}

@Composable
fun PinCodeProgress(
    state: PinCodeComponent.ProgressState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state is PinCodeComponent.ProgressState.Error) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = state.message.resolve(),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        val compensatingOffset = if (state is PinCodeComponent.ProgressState.Error) -20f else 0f

        val animatingOffset by animateFloatAsState(
            if (state is PinCodeComponent.ProgressState.Error) 20f else 0F,
            spring(dampingRatio = Spring.DampingRatioHighBouncy)
        )

        val progressDotsOffset =
            if (state is PinCodeComponent.ProgressState.Error) animatingOffset else 0f

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = compensatingOffset.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.offset(x = progressDotsOffset.dp)
            ) {
                when (state) {
                    is PinCodeComponent.ProgressState.Progress -> {
                        repeat(PinCode.PIN_CODE_LENGTH) { index ->
                            ProgressDot(
                                color = if (state.count > index) {
                                    MaterialTheme.colors.primary
                                } else {
                                    MaterialTheme.colors.surface
                                }
                            )
                        }
                    }
                    is PinCodeComponent.ProgressState.Error -> {
                        repeat(PinCode.PIN_CODE_LENGTH) { ProgressDot(MaterialTheme.colors.error) }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProgressDot(color: Color) {
    Box(
        modifier = Modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun PinCodeKeyboard(
    onDigitClick: (String) -> Unit,
    onBackspaceClick: () -> Unit,
    onForgotClick: () -> Unit,
    onFingerprintClick: () -> Unit,
    isBackspaceVisible: Boolean,
    isForgotVisible: Boolean,
    isFingerprintVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            KeyboardColumn {
                DigitButton(text = "1", onClick = { onDigitClick("1") })
                DigitButton(text = "4", onClick = { onDigitClick("4") })
                DigitButton(text = "7", onClick = { onDigitClick("7") })
                if (isForgotVisible) {
                    TextButton(
                        onClick = onForgotClick,
                        modifier = Modifier.size(64.dp),
                        contentPadding = PaddingValues(0.dp),
                        shape = CircleShape
                    ) {
                        Text(
                            text = stringResource(R.string.pincode_forgot_pincode_btn),
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.onBackground,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                if (isFingerprintVisible) {
                    IconButton(
                        onClick = onFingerprintClick,
                        modifier = Modifier
                            .size(64.dp)
                            .padding(18.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = ru.mobileup.core.R.drawable.ic_36_fingerprint),
                            contentDescription = null
                        )
                    }
                }
            }
            KeyboardColumn {
                DigitButton(text = "2", onClick = { onDigitClick("2") })
                DigitButton(text = "5", onClick = { onDigitClick("5") })
                DigitButton(text = "8", onClick = { onDigitClick("8") })
                DigitButton(text = "0", onClick = { onDigitClick("0") })
            }
            KeyboardColumn {
                DigitButton(text = "3", onClick = { onDigitClick("3") })
                DigitButton(text = "6", onClick = { onDigitClick("6") })
                DigitButton(text = "9", onClick = { onDigitClick("9") })
                if (isBackspaceVisible) {
                    IconButton(
                        onClick = onBackspaceClick,
                        modifier = Modifier
                            .size(64.dp)
                            .padding(18.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = ru.mobileup.core.R.drawable.ic_36_backspace),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun KeyboardColumn(content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        content()
    }
}

@Composable
fun DigitButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(64.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onBackground
        ),
        elevation = null
    ) {
        Text(text = text, style = MaterialTheme.typography.h5)
    }
}

@Preview(showSystemUi = true)
@Composable
fun PinCodeUiPreview() {
    AppTheme {
        PinCodeUi(FakePinCodeComponent())
    }
}

class FakePinCodeComponent : PinCodeComponent {

    override val progress: PinCodeComponent.ProgressState = PinCodeComponent.ProgressState.Progress(
        0
    )

    override fun onDigitClick(digit: String) = Unit

    override fun onBackspaceClick() = Unit

    override fun showError(message: LocalizedString) = Unit

    override fun onForgotPinCodeClick() = Unit

    override fun onFingerprintClick() = Unit

    override fun clearInput() = Unit

    override val isBackspaceVisible: Boolean = true

    override val isForgotButtonVisible: Boolean = true

    override val isFingerprintButtonVisible: Boolean = true
}