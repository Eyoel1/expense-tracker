package com.ethiofintech.expense_tracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount; // Using BigDecimal for money is Industry Standard

    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionType type; // INCOME or EXPENSE

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // TELEBIRR, CASH, etc.

    private LocalDateTime date;

    private String categoryIcon; // e.g. "ic_transport"

    private String receiptImageUrl;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDateTime.now();
    }

    // Relationship: Many Transactions -> One User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}