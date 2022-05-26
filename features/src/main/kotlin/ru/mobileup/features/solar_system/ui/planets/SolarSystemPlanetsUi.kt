package ru.mobileup.features.solar_system.ui.planets

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.aartikov.replica.single.Loadable
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.core.utils.resolve
import ru.mobileup.core.widget.LceWidget
import ru.mobileup.core.widget.Toolbar
import ru.mobileup.features.R
import ru.mobileup.features.solar_system.domain.PlanetId
import ru.mobileup.features.solar_system.ui.PlanetShortInfoViewData

@Composable
fun SolarSystemPlanetsUi(
    component: SolarSystemPlanetsComponent,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.solar_system_title),
                navigationIcon = null
            )
        },
        content = { paddingValue ->
            LceWidget(
                state = component.planetsViewState,
                onRetryClick = component::onRetryClick,
                modifier = Modifier.padding(paddingValue)
            ) { items, _ ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(items = items, key = { item -> item.id.value }) {
                        PlanetCard(data = it, onItemClick = {})
                    }
                }
            }
        }
    )
}

@Composable
fun PlanetCard(
    data: PlanetShortInfoViewData,
    onItemClick: (PlanetId) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onItemClick(data.id) },
        elevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            data.imageRes?.let {
                Image(
                    painter = painterResource(id = it),
                    alignment = Alignment.BottomEnd,
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = (-58).dp, y = (-58).dp)
                        .size(200.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 150.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Text(
                    text = data.name.resolve(),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h4
                )
                Text(
                    text = data.description.resolve(),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.65f),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SolarSystemPlanetsUiPreview() {
    AppTheme {
        SolarSystemPlanetsUi(FakeSolarSystemPlanetsComponent())
    }
}

class FakeSolarSystemPlanetsComponent : SolarSystemPlanetsComponent {

    override val planetsViewState = Loadable(
        loading = false,
        data = PlanetShortInfoViewData.mocks()
    )

    override fun onRetryClick() = Unit
}