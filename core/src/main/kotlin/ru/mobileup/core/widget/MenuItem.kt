package ru.mobileup.core.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.mobileup.core.R

@Composable
fun MenuItem(
    painter: Painter,
    text: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    actionElement: @Composable (RowScope.() -> Unit)? = {
        Icon(
            painter = painterResource(id = R.drawable.ic_24_chevron_right),
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface
        )
    }
) {
    Row(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painter,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 4.dp)
                .padding(vertical = 8.dp),
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body1
        )

        if (actionElement != null) {
            actionElement()
        }
    }
}