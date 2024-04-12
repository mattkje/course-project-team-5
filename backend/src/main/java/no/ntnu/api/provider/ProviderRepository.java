package no.ntnu.api.provider;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the Provider entity.
 */
public interface ProviderRepository extends JpaRepository<Provider, Long> {
}