package ru.mobileup.features.launchers.ui.upcoming

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import ru.mobileup.core.widget.Toolbar
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.features.launchers.domain.LauncherId
import ru.mobileup.features.R
import ru.mobileup.core.utils.resolve
import ru.mobileup.features.launchers.ui.LauncherViewData
import me.aartikov.sesame.loading.simple.Loading
import ru.mobileup.core.widget.LceWidget

@Composable
fun UpcomingLaunchersUi(
    component: UpcomingLaunchersComponent,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.launchers_title),
                navigationIcon = null
            )
        },
        content = { paddingValue ->
            LceWidget(
                data = component.upcomingLaunchersViewState,
                onRetryClick = component::onRetryClick,
                modifier = Modifier.padding(paddingValue)
            ) { data ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(items = data, key = { item -> item.id.value }) {
                        LauncherCard(data = it, onItemClick = {})
                    }
                }
            }
        }
    )
}

@Composable
fun LauncherCard(
    data: LauncherViewData,
    onItemClick: (LauncherId) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onItemClick(data.id) },
        elevation = 2.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = data.flightNumber.resolve(),
                    style = MaterialTheme.typography.caption
                )

                Text(
                    text = data.name.resolve(),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h4
                )

                Text(
                    text = data.launchDate.resolve(),
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.65f),
                    style = MaterialTheme.typography.body1
                )
            }
            val painter = rememberImagePainter(
                data = data.patchImage,
                builder = {
                    allowHardware(false)
                    crossfade(true)
                }
            )

            Image(
                modifier = Modifier.size(64.dp),
                painter = painter,
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun UpcomingLaunchersUiPreview() {
    AppTheme {
        UpcomingLaunchersUi(FakeUpcomingLaunchersComponent())
    }
}

class FakeUpcomingLaunchersComponent : UpcomingLaunchersComponent {
    override val upcomingLaunchersViewState = Loading.State.Data(LauncherViewData.mocks())

    override fun onRetryClick() = Unit
}