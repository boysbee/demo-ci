package me.wallet.utils

import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime

object DateTimeUtils {
    
    fun toCurrentTimestamp(currentZonedDateTime: ZonedDateTime): Timestamp {
        return Timestamp.valueOf(currentZonedDateTime.toLocalDateTime())
    }

    fun fromTimeStamp(currentTimestamp: Timestamp, zone: ZoneId): ZonedDateTime {
        return ZonedDateTime.of(currentTimestamp.toLocalDateTime(), zone)
    }
}