package ru.mobileup.education_space.launchers

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arkivanov.essenty.lifecycle.Lifecycle
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTestRule
import ru.mobileup.education_space.utils.*
import ru.mobileup.features.launchers.createUpcomingLaunchersComponent

@RunWith(AndroidJUnit4::class)
class UpcomingLaunchersComponentTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create()

    @Test
    fun `loads launchers list for a specified id on start`() {
        val koin = koinTestRule.testKoin()
        val fakeWebServer = koin.fakeWebServer
        val componentContext = TestComponentContext()
        val sut = koin
            .componentFactory
            .createUpcomingLaunchersComponent(componentContext)
        componentContext.moveToState(Lifecycle.State.RESUMED)

        fakeWebServer.prepareResponse("/v4/launches/upcoming", FakeLaunchers.upcomingLaunchersJson)
        awaitUntil { sut.upcomingLaunchersViewState.loading }
        awaitUntil { !sut.upcomingLaunchersViewState.loading }
        val actualLauncherViewDataList = sut.upcomingLaunchersViewState.data

        Assert.assertEquals(2, actualLauncherViewDataList?.count())
        Assert.assertEquals(FakeLaunchers.upcomingLauncher, actualLauncherViewDataList?.firstOrNull())
    }

    @Test
    fun `shows fullscreen error when details loading failed`() {
        val koin = koinTestRule.testKoin()
        val fakeWebServer = koin.fakeWebServer
        val componentContext = TestComponentContext()
        val sut = koin
            .componentFactory
            .createUpcomingLaunchersComponent(componentContext)
        componentContext.moveToState(Lifecycle.State.RESUMED)

        fakeWebServer.prepareResponse("/v4/launches/upcoming", FakeResponse.BadRequest)
        awaitUntil { sut.upcomingLaunchersViewState.loading }
        awaitUntil { !sut.upcomingLaunchersViewState.loading }
        val upcomingLaunchersViewState = sut.upcomingLaunchersViewState

        Assert.assertTrue(upcomingLaunchersViewState.error != null)
    }

    @Test
    fun `reloads list when retry is clicked after failed loading`() {
        val koin = koinTestRule.testKoin()
        val fakeWebServer = koin.fakeWebServer
        val componentContext = TestComponentContext()
        val sut = koin
            .componentFactory
            .createUpcomingLaunchersComponent(componentContext)

        fakeWebServer.prepareResponse("/v4/launches/upcoming", FakeResponse.BadRequest)
        componentContext.moveToState(Lifecycle.State.RESUMED)
        awaitUntil { sut.upcomingLaunchersViewState.loading }
        awaitUntil { !sut.upcomingLaunchersViewState.loading }

        fakeWebServer.prepareResponse("/v4/launches/upcoming", FakeLaunchers.upcomingLaunchersJson)
        sut.onRetryClick()
        awaitUntil { sut.upcomingLaunchersViewState.loading }
        awaitUntil { !sut.upcomingLaunchersViewState.loading }
        val actualLaunchersViewDataList = sut.upcomingLaunchersViewState.data

        Assert.assertEquals(2, actualLaunchersViewDataList?.count())
        Assert.assertEquals(FakeLaunchers.upcomingLauncher, actualLaunchersViewDataList?.firstOrNull())
    }
}