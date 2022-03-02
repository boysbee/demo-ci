package me.wallet.repositories

import me.wallet.entities.WalletTransactions
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.ZonedDateTime

@Component
interface WalletTransactionRepository : CrudRepository<WalletTransactions, String>{}