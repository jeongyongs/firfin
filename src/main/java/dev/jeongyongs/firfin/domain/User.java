package dev.jeongyongs.firfin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_status")
    private UserStatus userStatus;

    @Setter
    private long money;

    @Setter
    private long point;

    @Column(name = "money_limit")
    private long moneyLimit;

    @Column(name = "per_payment_limit")
    private long perPaymentLimit;

    @Column(name = "daily_payment_limit")
    private long dailyPaymentLimit;

    @Column(name = "monthly_payment_limit")
    private long monthlyPaymentLimit;

    @Builder
    public User(long money, long point, long moneyLimit, long perPaymentLimit, long dailyPaymentLimit,
        long monthlyPaymentLimit) {
        userStatus = UserStatus.ACTIVE;
        this.money = money;
        this.point = point;
        this.moneyLimit = moneyLimit;
        this.perPaymentLimit = perPaymentLimit;
        this.dailyPaymentLimit = dailyPaymentLimit;
        this.monthlyPaymentLimit = monthlyPaymentLimit;
    }
}
