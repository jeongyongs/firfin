package dev.jeongyongs.firfin.repository;

import dev.jeongyongs.firfin.domain.Payment;
import dev.jeongyongs.firfin.domain.PaymentStatus;
import dev.jeongyongs.firfin.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByTransactionId(UUID id);

    List<Payment> findAllByUserAndPaymentStatusAndCreateAtGreaterThanEqual(
        User user, PaymentStatus status, LocalDateTime createAt);

    Optional<Payment> findByIdAndPaymentStatus(long id, PaymentStatus paymentStatus);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.user = :user AND p.paymentStatus = 0 AND p.createAt >= :baseDate")
    long getUsage(User user, LocalDateTime baseDate);
}
