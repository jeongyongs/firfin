package dev.jeongyongs.firfin.repository;

import dev.jeongyongs.firfin.domain.Payment;
import dev.jeongyongs.firfin.domain.PaymentStatus;
import dev.jeongyongs.firfin.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByTransactionId(UUID id);

    List<Payment> findAllByUserAndPaymentStatusAndCreateAtGreaterThanEqual(
        User user, PaymentStatus status, LocalDateTime createAt);

    Optional<Payment> findByIdAndPaymentStatus(long id, PaymentStatus paymentStatus);
}
