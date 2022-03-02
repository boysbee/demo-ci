package me.wallet.features.history.models

import java.time.ZonedDateTime
import javax.validation.constraints.NotNull


data class HistoriesCriteria(
    @field:NotNull
    val startDatetime: ZonedDateTime,
    @field:NotNull
    val endDatetime: ZonedDateTime
)
