package me.wallet.features.history.controller

import io.mockk.every
import io.mockk.mockk
import me.wallet.features.history.models.TransactionHistory
import me.wallet.services.WalletService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime

internal class HistoryControllerTest {

    private lateinit var mockMvc: MockMvc
    private lateinit var historyController: HistoryController
    private val walletService: WalletService = mockk()

    @BeforeEach
    fun setUp() {
        historyController = HistoryController(walletService)
        mockMvc = MockMvcBuilders.standaloneSetup(historyController).build()
    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    fun `history with valid request should be successfully`() {
        every { walletService.findBalanceHistory(any(),any()) } returns listOf(
            TransactionHistory(
                datetime = ZonedDateTime.now(ZoneId.of("UTC")),
                amount = BigDecimal.valueOf(1000)
            ),
            TransactionHistory(
                datetime = ZonedDateTime.now(ZoneId.of("UTC")),
                amount = BigDecimal.valueOf(1001.1)
            )
        )
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/wallet?startDatetime=2011-10-05T10:48:01+00:00&endDatetime=2011-10-05T18:48:02+00:00"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `should be bad request when parameter "startDatetime" is empty`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/wallet?startDatetime=&endDatetime=2011-10-05T18:48:02+00:00"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should be bad request when parameter "startDatetime" is invalid format date`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/wallet?startDatetime=2011-10-05&endDatetime=2011-10-05T18:48:02+00:00"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should be bad request when parameter "endDatetime" is empty`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/wallet?startDatetime=2011-10-05T10:48:01+00:00&endDatetime="))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should be bad request when parameter "endDatetime" is invalid format date`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/wallet?startDatetime=2011-10-05T10:48:01+00:00&endDatetime=2011-10-05"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}