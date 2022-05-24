package ru.mobileup.education_space.launchers

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arkivanov.essenty.lifecycle.Lifecycle
import ru.mobileup.education_space.utils.*
import kotlinx.datetime.Instant
import me.aartikov.sesame.loading.simple.Loading
import me.aartikov.sesame.loading.simple.dataOrNull
import me.aartikov.sesame.localizedstring.LocalizedString
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTestRule
import ru.mobileup.core.message.createMessagesComponent
import ru.mobileup.core.network.BaseUrlProvider
import ru.mobileup.core.utils.DateTimeLocalizedString
import ru.mobileup.education_space.utils.MockServerRule
import ru.mobileup.features.launchers.createUpcomingLaunchersComponent
import ru.mobileup.features.launchers.domain.LauncherId
import ru.mobileup.features.launchers.ui.LauncherViewData
import ru.mobileup.features.R

@RunWith(AndroidJUnit4::class)
class UpcomingLaunchersComponentTest {

    @get:Rule
    val mockServerRule = MockServerRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create()

    @Test
    fun `shows empty state when loaded data is empty`() {
        mockServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(FakeData.launchersEmptyResponse)
        )
        val koin = koinTestRule.testKoin {
            single<BaseUrlProvider> { MockServerBaseUrlProvider(mockServerRule) }
        }
        val componentContext = TestComponentContext()
        val sut = koin
            .componentFactory
            .createUpcomingLaunchersComponent(componentContext)
        componentContext.moveToState(Lifecycle.State.RESUMED)

        awaitUntil { sut.upcomingLaunchersViewState is Loading.State.Loading }
        awaitUntil { sut.upcomingLaunchersViewState !is Loading.State.Loading }
        val actualLaunchersViewState = sut.upcomingLaunchersViewState

        Assert.assertEquals(Loading.State.Empty, actualLaunchersViewState)
    }

    @Test
    fun `shows data when it is loaded`() {
        mockServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(FakeData.launchersListResponse)
        )
        val koin = koinTestRule.testKoin {
            single<BaseUrlProvider> { MockServerBaseUrlProvider(mockServerRule) }
        }
        val componentContext = TestComponentContext()
        val sut = koin
            .componentFactory
            .createUpcomingLaunchersComponent(componentContext)
        val data = LauncherViewData(
            id = LauncherId("61eefaa89eb1064137a1bd73"),
            name = LocalizedString.raw("Ax-1"),
            flightNumber = LocalizedString.resource(R.string.launchers_flight_number, 155),
            launchDate = DateTimeLocalizedString(Instant.parse("2022-03-30T18:45:00Z")),
            patchImage = "https://i.imgur.com/h4x6VFd.png"
        )
        componentContext.moveToState(Lifecycle.State.RESUMED)

        awaitUntil { sut.upcomingLaunchersViewState is Loading.State.Loading }
        awaitUntil { sut.upcomingLaunchersViewState !is Loading.State.Loading }
        val actualLauncherViewDataList = sut.upcomingLaunchersViewState.dataOrNull

        Assert.assertEquals(2, actualLauncherViewDataList?.count())
        Assert.assertEquals(data, actualLauncherViewDataList?.firstOrNull())
    }

    @Test
    fun `shows error when loader failed`() {
        mockServerRule.server.enqueue(MockResponse().setResponseCode(404))
        val koin = koinTestRule.testKoin {
            single<BaseUrlProvider> { MockServerBaseUrlProvider(mockServerRule) }
        }
        val componentContext = TestComponentContext()
        val messageComponent = koin
            .componentFactory
            .createMessagesComponent(componentContext)
        val sut = koin
            .componentFactory
            .createUpcomingLaunchersComponent(componentContext)
        componentContext.moveToState(Lifecycle.State.RESUMED)

        awaitUntil { sut.upcomingLaunchersViewState is Loading.State.Loading }
        awaitUntil { sut.upcomingLaunchersViewState !is Loading.State.Loading }
        val actualMessageDialogControlData = messageComponent.visibleMessageData
        val upcomingLaunchersViewState = sut.upcomingLaunchersViewState

        Assert.assertNotNull(actualMessageDialogControlData)
        Assert.assertTrue(upcomingLaunchersViewState is Loading.State.Error)
    }

    @Test
    fun `update data when retry click`() {
        mockServerRule.server.enqueue(MockResponse().setResponseCode(404))
        mockServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(FakeData.launchersListResponse)
        )
        val koin = koinTestRule.testKoin {
            single<BaseUrlProvider> { MockServerBaseUrlProvider(mockServerRule) }
        }
        val componentContext = TestComponentContext()
        val sut = koin
            .componentFactory
            .createUpcomingLaunchersComponent(componentContext)
        val data = LauncherViewData(
            id = LauncherId("61eefaa89eb1064137a1bd73"),
            name = LocalizedString.raw("Ax-1"),
            flightNumber = LocalizedString.resource(R.string.launchers_flight_number, 155),
            launchDate = DateTimeLocalizedString(Instant.parse("2022-03-30T18:45:00Z")),
            patchImage = "https://i.imgur.com/h4x6VFd.png"
        )
        componentContext.moveToState(Lifecycle.State.RESUMED)
        awaitUntil { sut.upcomingLaunchersViewState is Loading.State.Loading }
        awaitUntil { sut.upcomingLaunchersViewState !is Loading.State.Loading }

        sut.onRetryClick()
        awaitUntil { sut.upcomingLaunchersViewState is Loading.State.Loading }
        awaitUntil { sut.upcomingLaunchersViewState !is Loading.State.Loading }
        val actualLaunchersViewDataList = sut.upcomingLaunchersViewState.dataOrNull

        Assert.assertEquals(2, actualLaunchersViewDataList?.count())
        Assert.assertEquals(data, actualLaunchersViewDataList?.firstOrNull())
    }
}