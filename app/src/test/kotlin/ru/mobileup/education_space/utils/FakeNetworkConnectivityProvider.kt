package ru.mobileup.education_space.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.aartikov.replica.network.NetworkConnectivityProvider

class FakeNetworkConnectivityProvider : NetworkConnectivityProvider {

    private val _connectedFlow = MutableStateFlow(true)

    override val connectedFlow: StateFlow<Boolean> get() = _connectedFlow
}