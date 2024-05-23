package no.ntnu.api.currency;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for currency entities.
 */
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

  Currency findByCode(String code);
}
