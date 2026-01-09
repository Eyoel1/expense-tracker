package com.ethio.et_tracker_api.model;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "budgets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amountLimit;

    @Column(nullable = false)
    private String categoryName; // e.g., "Food", "Transport"

    @Column(nullable = false)
    private String monthYear; // Format: "MM-YYYY" to track monthly budgets

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}