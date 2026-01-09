package com.ethio.et_tracker_api.model;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., "Telebirr Main"

    @Enumerated(EnumType.STRING)
    private WalletType type; // CASH, TELEBIRR, CBE_BIRR, etc.

    private BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public enum WalletType {
        CASH, TELEBIRR, CBE_BIRR, BANK_TRANSFER, AMOLE
    }
}
