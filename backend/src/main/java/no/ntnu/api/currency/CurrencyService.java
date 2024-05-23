package no.ntnu.api.currency;

import java.util.*;
import org.springframework.stereotype.Service;


/**
 * Service for currency operations.
 */
@Service
public class CurrencyService {
  private final CurrencyRepository currencyRepository;

  /**
   * Create a new currency service.
   *
   * @param currencyRepository The repository to use
   */
  public CurrencyService(CurrencyRepository currencyRepository) {
    this.currencyRepository = currencyRepository;
  }

  /**
   * Find a currency by its code.
   *
   * @param code The code to search for (e.g. "USD")
   * @return The currency with the given code, or null if not found
   */
  public Currency findByCode(String code) {
    return currencyRepository.findByCode(code);
  }

  /**
   * Get all currencies.
   *
   * @return A list of all currencies
   */
  public List<Currency> getCurrencies() {
    return currencyRepository.findAll();
  }
}
