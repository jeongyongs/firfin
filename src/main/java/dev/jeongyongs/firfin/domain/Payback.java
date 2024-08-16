package dev.jeongyongs.firfin.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "paybacks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payback_id")
    private Long id;

    @JoinColumn(name = "payment_id")
    @ManyToOne
    private Payment payment;

    @Setter
    @Column(name = "payback_status")
    private PaybackStatus paybackStatus;

    private long amount;

    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    public Payback(Payment payment, long amount) {
        paybackStatus = PaybackStatus.PAID;
        createdAt = LocalDateTime.now();
        this.payment = payment;
        this.amount = amount;
    }
}
