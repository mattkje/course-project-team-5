package no.ntnu.courses.coursesapi.api.model;

import java.util.Currency;

public class CourseProviders {
    private String name;
    private int price;
    private Currency valuta;

    public CourseProviders(String name, int price, Currency valuta) {
        this.name = name;
        this.price = price;
        this.valuta = valuta;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public String getValuta() {
        return this.valuta.getDisplayName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setValuta(Currency valuta) {
        this.valuta = valuta;
    }
}
