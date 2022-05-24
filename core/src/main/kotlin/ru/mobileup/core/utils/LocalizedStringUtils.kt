package ru.mobileup.core.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import me.aartikov.sesame.localizedstring.LocalizedString
import ru.mobileup.core.R
import ru.mobileup.core.utils.Patterns.DAY_MONTH_YY
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

object Patterns {
    const val DAY_MONTH_YY = "d MMMM, yy"
}

@Composable
fun LocalizedString.resolve(): String {
    LocalConfiguration.current
    return resolve(LocalContext.current).toString()
}

/**
 * [LocalizedString] для отображение даты и времени на основе kotlinx.[Instant]
 */
data class DateTimeLocalizedString(
    private val instant: Instant,
    private val pattern: String? = null
) : LocalizedString {

    constructor(
        date: LocalDate,
        timeZone: TimeZone = TimeZone.currentSystemDefault(),
        pattern: String? = null
    ) : this(date.atStartOfDayIn(timeZone), pattern)

    constructor(
        dateTime: LocalDateTime,
        timeZone: TimeZone = TimeZone.currentSystemDefault(),
        pattern: String? = null
    ) : this(dateTime.toInstant(timeZone), pattern)

    override fun resolve(context: Context): CharSequence {
        val dateFormatSymbols = DateFormatSymbols(Locale.getDefault()).apply {
            months = context.resources.getStringArray(R.array.dates_month_relative)
        }
        return if (pattern != null) {
            val date = Date(instant.toEpochMilliseconds())

            val formatter = SimpleDateFormat(pattern, Locale.getDefault())
            formatter.dateFormatSymbols = dateFormatSymbols

            formatter.format(date)
        } else {
            instant.getPrettyString(dateFormatSymbols)
        }
    }
}

fun Instant.getPrettyString(
    dateFormatSymbols: DateFormatSymbols?,
    locale: Locale = Locale.getDefault()
): String {
    val dateFormatter = SimpleDateFormat(DAY_MONTH_YY, locale)
    dateFormatter.dateFormatSymbols = dateFormatSymbols

    val date = Date(this.toEpochMilliseconds())
    return dateFormatter.format(date)
}