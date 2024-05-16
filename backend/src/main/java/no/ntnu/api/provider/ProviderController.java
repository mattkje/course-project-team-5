package no.ntnu.api.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

  private final ProviderService providerService;

  @Autowired
  public ProviderController(ProviderService providerService) {
    this.providerService = providerService;
  }

  @GetMapping
  public List<Provider> getAllProviders() {
    return providerService.getAllProviders();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Provider> getProviderById(@PathVariable Integer id) {
    return providerService.getProviderById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}