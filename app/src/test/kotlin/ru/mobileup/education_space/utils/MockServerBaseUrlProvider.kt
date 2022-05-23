package ru.mobileup.education_space.utils

import ru.mobileup.core.network.BaseUrlProvider

class MockServerBaseUrlProvider(private val mockServerRule: MockServerRule) : BaseUrlProvider {

    override fun getUrl(): String = mockServerRule.url ?: ""
}