package no.ntnu.api.currency;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Represents a currency in the database. Each currency has a code, name, symbol and rate.
 * The code is the unique identifier for the currency, e.g. "USD" for US Dollar.
 * The name is the full name of the currency, e.g. "United States Dollar".
 * The symbol is the currency symbol, e.g. "$" for US Dollar.
 */
@Entity
@Table(name = "currencies")
public class Currency {
    @Schema(description = "The currency code / abbreviation")
    @Id
    private String code;
    @Schema(description = "The currency name")
    @Column()
    private String name;
    @Schema(description = "The currency symbol")
    @Column()
    private String symbol;
    @Schema(description = "The currency exchange rate")
    @Column(precision = 10, scale = 2)
    private BigDecimal rate;

    /**
     * Get the code of the currency.
     *
     * @return The code of the currency
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the code of the currency.
     *
     * @param code The code of the currency
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get the name of the currency.
     *
     * @return The name of the currency
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the currency.
     *
     * @param name The name of the currency
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the symbol of the currency.
     *
     * @return The symbol of the currency
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Set the symbol of the currency.
     *
     * @param symbol The symbol of the currency
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Get the rate of the currency.
     *
     * @return The rate of the currency
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * Set the rate of the currency.
     *
     * @param rate The rate of the currency
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
