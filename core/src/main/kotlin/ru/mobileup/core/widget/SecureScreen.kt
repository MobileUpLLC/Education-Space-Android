package ru.mobileup.core.widget

import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import ru.mobileup.core.utils.getActivity

@Composable
fun SecureScreen() {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.getActivity()
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        onDispose {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }
}