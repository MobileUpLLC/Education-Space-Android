package ru.mobileup.features.start.ui

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.mobileup.core.theme.AppTheme

@Composable
fun StartUi() {
    CircularProgressIndicator()
}

@Preview(showSystemUi = true)
@Composable
fun StartUiPreview() {
    AppTheme {
        StartUi()
    }
}