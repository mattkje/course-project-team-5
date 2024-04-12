package no.ntnu.api.currency;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Represents a currency in the database
 */
@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    private String code;
    @Column()
    private String name;
    @Column()
    private String symbol;
    @Column(precision = 10, scale = 2)
    private BigDecimal rate;

    // Getters and setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
