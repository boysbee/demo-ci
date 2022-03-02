package me.wallet

import me.wallet.entities.WalletBalance
import me.wallet.entities.WalletTransactions
import me.wallet.repositories.WalletBalanceRepository
import me.wallet.repositories.WalletTransactionRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*


@SpringBootApplication
class Application {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    lateinit var walletTransactionRepository: WalletTransactionRepository
    @Autowired
    lateinit var walletBalanceRepository: WalletBalanceRepository

    @EventListener(ApplicationReadyEvent::class)
    fun runAfterStartup() {


        val list = mutableListOf<WalletTransactions>(
            WalletTransactions(
                id = UUID.randomUUID(), 1.0.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusHours(-2))
            ),
            WalletTransactions(
                id = UUID.randomUUID(), 1.0.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusMinutes(-20))
            ),
            WalletTransactions(
                id = UUID.randomUUID(), 1.0.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusHours(-1))
            ),
            WalletTransactions(
                id = UUID.randomUUID(), 2.1.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
            ),
            WalletTransactions(
                id = UUID.randomUUID(), 10.0.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusHours(1))
            ),
            WalletTransactions(
                id = UUID.randomUUID(), 0.2.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusHours(2))
            ),
            WalletTransactions(
                id = UUID.randomUUID(), 0.1.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusMinutes(20))
            )
        )

        val listBalances = mutableListOf<WalletBalance>(
            WalletBalance(
                id = UUID.randomUUID(), 1000.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusHours(-2))
            ),
            WalletBalance(
                id = UUID.randomUUID(), 1001.0.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusMinutes(-20))
            ),
            WalletBalance(
                id = UUID.randomUUID(), 1002.0.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusHours(-1))
            ),
            WalletBalance(
                id = UUID.randomUUID(), 1004.1.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
            ),
            WalletBalance(
                id = UUID.randomUUID(), 1014.1.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusHours(1))
            ),
            WalletBalance(
                id = UUID.randomUUID(), 1014.3.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusHours(2))
            ),
            WalletBalance(
                id = UUID.randomUUID(), 1014.4.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusMinutes(20))
            )
        )
        logger.info("Saving new transactions...")
        this.walletTransactionRepository.saveAll(list)
        this.walletBalanceRepository.saveAll(listBalances)

    }

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)


}