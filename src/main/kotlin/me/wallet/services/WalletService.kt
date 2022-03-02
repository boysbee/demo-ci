package me.wallet.services

import me.wallet.features.history.models.TransactionHistory
import me.wallet.repositories.WalletBalanceRepository
import me.wallet.repositories.WalletTransactionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Service
class WalletService(
    private val walletBalanceRepository: WalletBalanceRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    fun findBalanceHistory(startDate: LocalDateTime, endDateTime: LocalDateTime): List<TransactionHistory> {
        return try {
            walletBalanceRepository.findBalanceByTransactionDateAndGroupByHours(
                Timestamp.valueOf(startDate), Timestamp.valueOf(endDateTime)
            ).groupBy { it.transactionDate }
                .map {
                    TransactionHistory(
                        datetime = ZonedDateTime.of(it.key.toLocalDateTime(), ZoneId.of("UTC")),
                        amount = it.value.last().balance
                    )
                }.toList()
        } catch (ex: Exception) {
            logger.warn("Error : %s".format(ex.message), ex)
            listOf()
        }
    }
}