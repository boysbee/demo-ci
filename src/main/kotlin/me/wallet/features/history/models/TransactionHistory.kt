package me.wallet.features.history.models

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.time.ZonedDateTime

@JsonIgnoreProperties
data class TransactionHistory(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssxxx", timezone = "UTC")
    val datetime: ZonedDateTime,
    val amount: BigDecimal
)