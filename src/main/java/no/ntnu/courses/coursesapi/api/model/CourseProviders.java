package no.ntnu.courses.coursesapi.api.model;

import java.util.Currency;

public class CourseProviders {
    private String name;
    private int price;
    private Currency currency;

    public CourseProviders(String name, int price, Currency currency) {
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public String getCurrency() {
        return this.currency.getDisplayName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCurrency(Currency valuta) {
        this.currency = valuta;
    }
}
