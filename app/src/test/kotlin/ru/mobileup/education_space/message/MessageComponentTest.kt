package ru.mobileup.education_space.message

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arkivanov.essenty.lifecycle.Lifecycle
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTestRule
import ru.mobileup.core.message.createMessagesComponent
import ru.mobileup.core.message.data.MessageService
import ru.mobileup.education_space.utils.TestComponentContext
import ru.mobileup.education_space.utils.componentFactory
import ru.mobileup.education_space.utils.testKoin

@RunWith(AndroidJUnit4::class)
class MessageComponentTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create()

    @Test
    fun `shows data with icon when it is received`() {
        val koin = koinTestRule.testKoin()
        val messageService = koin.get<MessageService>()
        val componentContext = TestComponentContext()
        val sut = koin
            .componentFactory
            .createMessagesComponent(componentContext)
        componentContext.moveToState(Lifecycle.State.CREATED)

        messageService.showMessage(FakeMessage.messageWithIcon)
        val actualMessageData = sut.visibleMessageData

        Assert.assertEquals(FakeMessage.messageWithIcon, actualMessageData)
    }

    @Test
    fun `shows data without icon when it is received`() {
        val koin = koinTestRule.testKoin()
        val messageService = koin.get<MessageService>()
        val componentContext = TestComponentContext()
        val sut = koin
            .componentFactory
            .createMessagesComponent(componentContext)
        componentContext.moveToState(Lifecycle.State.CREATED)

        messageService.showMessage(FakeMessage.messageWithoutIcon)
        val actualMessageData = sut.visibleMessageData

        Assert.assertEquals(FakeMessage.messageWithoutIcon, actualMessageData)
    }
}