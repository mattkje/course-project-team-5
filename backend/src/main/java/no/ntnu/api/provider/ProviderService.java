package no.ntnu.api.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

  private final ProviderRepository providerRepository;

  @Autowired
  public ProviderService(ProviderRepository providerRepository) {
    this.providerRepository = providerRepository;
  }

  public List<Provider> getAllProviders() {
    return providerRepository.findAll();
  }

  public Optional<Provider> getProviderById(Integer id) {
    return providerRepository.findById(Long.valueOf(id));
  }
}