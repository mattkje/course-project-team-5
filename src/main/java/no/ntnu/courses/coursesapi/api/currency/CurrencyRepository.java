package no.ntnu.courses.coursesapi.api.currency;

import no.ntnu.courses.coursesapi.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findByCode(String code);
}
