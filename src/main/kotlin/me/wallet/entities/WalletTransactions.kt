package me.wallet.entities

import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "wallet_transactions")
class WalletTransactions(
    @Id
    @GeneratedValue
    val id: UUID,

    @Column(nullable = false, name = "amount", precision = 20, scale = 5)
    val amount: BigDecimal,

    @Column(nullable = false, name = "transaction_date")
    val transactionDate: Timestamp
): java.io.Serializable