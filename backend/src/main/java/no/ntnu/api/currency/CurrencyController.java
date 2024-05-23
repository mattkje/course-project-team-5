package no.ntnu.api.currency;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for currency. Endpoints for currency are defined here.
 */
@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

  private final CurrencyService currencyService;

  /**
   * Create controller.
   *
   * @param currencyService The service to use
   */
  @Autowired
  public CurrencyController(CurrencyService currencyService) {
    this.currencyService = currencyService;
  }

  /**
   * Get all currencies.
   *
   * @return List of currencies
   */
  @GetMapping
  public List<Currency> getCurrencies() {
    return currencyService.getCurrencies();
  }
}
