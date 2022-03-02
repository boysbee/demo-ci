package me.wallet.entities

import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "wallet_balance")
class WalletBalance(
    @Id
    @GeneratedValue
    val id: UUID,

    @Column(nullable = false, name = "balance", precision = 20, scale = 5)
    val balance: BigDecimal,

    @Column(nullable = false, name = "transaction_date")
    val transactionDate: Timestamp
): java.io.Serializable