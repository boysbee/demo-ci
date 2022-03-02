package me.wallet.services

import io.mockk.*
import me.wallet.entities.WalletBalance
import me.wallet.features.history.models.TransactionHistory
import me.wallet.repositories.WalletBalanceRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertThrows
import java.sql.SQLDataException
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*

internal class WalletServiceTest {
    private lateinit var walletService: WalletService
    private val walletBalanceRepository: WalletBalanceRepository = mockk()

    @BeforeEach
    fun setUp() {
        walletService = WalletService(walletBalanceRepository)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should return list of history when find balance history successfully`() {

        val startDatetime = LocalDateTime.now().plusDays(-1)
        val endDateTime = LocalDateTime.now().plusDays(1)
        val firstRecordTime = Timestamp.valueOf(LocalDateTime.now().plusDays(-1))

        every { walletBalanceRepository.findBalanceByTransactionDateAndGroupByHours(any(), any()) } returns listOf(
            WalletBalance(
                id = UUID.randomUUID(),
                balance = 1000.toBigDecimal(),
                transactionDate = firstRecordTime
            ),
            WalletBalance(
                id = UUID.randomUUID(),
                balance = 1020.1.toBigDecimal(),
                transactionDate = Timestamp.valueOf(LocalDateTime.now().plusDays(1))
            )
        )
        val list = walletService.findBalanceHistory(
            startDatetime, endDateTime
        )
        assertEquals(list[0].amount, 1000.toBigDecimal())
        assertEquals(list[1].amount, 1020.1.toBigDecimal())
    }


    @Test
    fun `should return empty list when not found exist data`() {

        val startDatetime = LocalDateTime.now().plusDays(-1)
        val endDateTime = LocalDateTime.now().plusDays(1)


        every { walletBalanceRepository.findBalanceByTransactionDateAndGroupByHours(any(), any()) } returns listOf()
        val list = walletService.findBalanceHistory(
            startDatetime, endDateTime
        )
        assertTrue(list.isEmpty())
    }

    @Test
    fun `should throw exception when find balance history fail`() {

        val startDatetime = LocalDateTime.now().plusDays(-1)
        val endDateTime = LocalDateTime.now().plusDays(1)
        every {
            walletBalanceRepository.findBalanceByTransactionDateAndGroupByHours(
                any(),
                any()
            )
        } throws SQLDataException()
        walletService.findBalanceHistory(
            startDatetime, endDateTime
        ).run {
            assertTrue(this.isEmpty())
        }
    }

}