package me.wallet.repositories

import me.wallet.entities.WalletBalance
import me.wallet.entities.WalletTransactions
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.ZonedDateTime

@Component
interface WalletBalanceRepository : CrudRepository<WalletBalance, String> {
    @Query(
        value = """select w.id, date_trunc('hour',w.transaction_date) as  transaction_date , balance as balance 
from   wallet_balance w 
where w.transaction_date >= to_timestamp(:startDateTime, 'YYYY-MM-DD HH24:MI:SS') 
and w.transaction_date <= to_timestamp(:endDateTime, 'YYYY-MM-DD HH24:MI:SS') 
group by 1 order by transaction_date asc""",
        nativeQuery = true
    )
    fun findBalanceByTransactionDateAndGroupByHours(@Param("startDateTime") startDateTime: Timestamp, @Param("endDateTime") endDateTime: Timestamp): List<WalletBalance>
}