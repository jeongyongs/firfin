package dev.jeongyongs.firfin.repository;

import dev.jeongyongs.firfin.domain.Payback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaybackRepository extends JpaRepository<Payback, Long> {

    boolean existsByPaymentId(Long paymentId);
}
