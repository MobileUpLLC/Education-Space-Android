package ru.mobileup.core.widget

import androidx.annotation.DrawableRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.mobileup.core.utils.dispatchOnBackPressed
import ru.mobileup.core.R

@Composable
fun Toolbar(
    title: String?,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = { BackButton() },
    actionIcon: @Composable (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.onBackground
) {
    TopAppBar(
        modifier = modifier,
        title = { title?.let { Text(text = it) } },
        navigationIcon = navigationIcon,
        actions = { actionIcon?.invoke() },
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = 0.dp
    )
}

@Composable
fun BackButton(modifier: Modifier = Modifier) {
    NavigationButton(iconRes = R.drawable.ic_24_arrow_back, modifier)
}

@Composable
fun NavigationButton(
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    IconButton(onClick = { dispatchOnBackPressed(context) }, modifier = modifier) {
        Icon(
            painterResource(iconRes),
            contentDescription = null
        )
    }
}