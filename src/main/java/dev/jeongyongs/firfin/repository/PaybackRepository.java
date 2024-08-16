package dev.jeongyongs.firfin.repository;

import dev.jeongyongs.firfin.domain.Payback;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaybackRepository extends JpaRepository<Payback, Long> {

    boolean existsByPaymentId(Long paymentId);
}
