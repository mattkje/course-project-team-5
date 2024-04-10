package no.ntnu.courses.coursesapi.api.currency;

import no.ntnu.courses.coursesapi.api.course.CourseWithProvidersAndKeywords;
import no.ntnu.courses.coursesapi.api.keywords.CourseKeywords;
import no.ntnu.courses.coursesapi.api.keywords.CourseKeywordsRepository;
import no.ntnu.courses.coursesapi.api.provider.CourseProvider;
import no.ntnu.courses.coursesapi.api.provider.CourseProviderRepository;
import no.ntnu.courses.coursesapi.api.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;


  public CurrencyService(CurrencyRepository currencyRepository) {
    this.currencyRepository = currencyRepository;
  }

    public Currency findByCode(String code) {
        return currencyRepository.findByCode(code);
    }

  public List<Currency> getCurrencies() {
    return currencyRepository.findAll();
  }
}
