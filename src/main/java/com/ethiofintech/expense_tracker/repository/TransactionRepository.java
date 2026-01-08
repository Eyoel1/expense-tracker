package com.ethio.et_tracker_api.repository;


import com.ethio.et_tracker_api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Finds all transactions for a specific user.
     * Sorted by Date Descending so the newest transaction appears first.
     */
    List<Transaction> findByUserIdOrderByTransactionDateDesc(Long userId);

    /**
     * Optional: Advanced query for charts.
     * Groups expenses by description/category to show in the Pie Chart.
     */
    @Query("SELECT t.description, SUM(t.amount) FROM Transaction t " +
            "WHERE t.user.id = :userId AND t.type = 'EXPENSE' " +
            "GROUP BY t.description")
    List<Object[]> getExpenseSummaryByCategory(@Param("userId") Long userId);
}
