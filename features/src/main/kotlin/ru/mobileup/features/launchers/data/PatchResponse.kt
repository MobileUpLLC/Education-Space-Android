package ru.mobileup.features.launchers.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.mobileup.features.launchers.domain.Patch

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