package dev.jeongyongs.firfin.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Setter
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "transaction_id")
    private UUID transactionId;

    @Column(name = "merchant_id")
    private long merchantId;

    private long amount;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Setter
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Builder
    public Payment(User user, UUID transactionId, long merchantId, long amount) {
        paymentStatus = PaymentStatus.APPROVE;
        createAt = LocalDateTime.now();
        this.user = user;
        this.transactionId = transactionId;
        this.merchantId = merchantId;
        this.amount = amount;
    }
}
