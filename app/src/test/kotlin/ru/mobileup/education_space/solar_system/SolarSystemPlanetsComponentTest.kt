package ru.mobileup.education_space.solar_system

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arkivanov.essenty.lifecycle.Lifecycle
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTestRule
import ru.mobileup.education_space.utils.TestComponentContext
import ru.mobileup.education_space.utils.awaitUntil
import ru.mobileup.education_space.utils.componentFactory
import ru.mobileup.education_space.utils.testKoin
import ru.mobileup.features.solar_system.createSolarSystemPlanetsComponent

@RunWith(AndroidJUnit4::class)
class SolarSystemPlanetsComponentTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create()

    @Test
    fun `loads solar system list for a specified id on start`() {
        val koin = koinTestRule.testKoin()
        val componentContext = TestComponentContext()
        val sut = koin.componentFactory.createSolarSystemPlanetsComponent(componentContext)

        componentContext.moveToState(Lifecycle.State.RESUMED)
        awaitUntil { sut.planetsViewState.loading }
        awaitUntil { !sut.planetsViewState.loading }
        val actualPlanetsViewDataList = sut.planetsViewState.data

        Assert.assertEquals(10, actualPlanetsViewDataList?.count())
        Assert.assertEquals(FakeSolarSystem.detailedSun, actualPlanetsViewDataList?.firstOrNull())
    }
}