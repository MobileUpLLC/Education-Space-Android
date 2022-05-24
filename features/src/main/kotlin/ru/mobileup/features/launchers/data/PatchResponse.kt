package ru.mobileup.features.launchers.data

import ru.mobileup.features.launchers.domain.Patch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PatchResponse(
    @SerialName("small") val small: String?,
    @SerialName("large") val large: String?
)

fun PatchResponse.toDomain(): Patch {
    return Patch(
        small = small,
        large = large
    )
}