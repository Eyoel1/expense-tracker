package com.ethio.et_tracker_api.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionType type; // INCOME or EXPENSE

    private Date transactionDate = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet; // This tracks if it was Telebirr, Cash, etc.

    public enum TransactionType {
        INCOME, EXPENSE
    }
}
