package org.asdtiang.carbrand.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A CarType.
 */
@Entity
@Table(name = "car_type")
public class CarType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "year_name", nullable = false)
    private String yearName;

    @NotNull
    @Column(name = "has_production", nullable = false)
    private Boolean hasProduction;

    @Column(name = "price_start", precision = 21, scale = 2)
    private BigDecimal priceStart;

    @Column(name = "price_end", precision = 21, scale = 2)
    private BigDecimal priceEnd;

    @NotNull
    @Column(name = "has_price", nullable = false)
    private Boolean hasPrice;

    @ManyToOne
    @JsonIgnoreProperties("carTypes")
    private Series series;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CarType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearName() {
        return yearName;
    }

    public CarType yearName(String yearName) {
        this.yearName = yearName;
        return this;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }

    public Boolean isHasProduction() {
        return hasProduction;
    }

    public CarType hasProduction(Boolean hasProduction) {
        this.hasProduction = hasProduction;
        return this;
    }

    public void setHasProduction(Boolean hasProduction) {
        this.hasProduction = hasProduction;
    }

    public BigDecimal getPriceStart() {
        return priceStart;
    }

    public CarType priceStart(BigDecimal priceStart) {
        this.priceStart = priceStart;
        return this;
    }

    public void setPriceStart(BigDecimal priceStart) {
        this.priceStart = priceStart;
    }

    public BigDecimal getPriceEnd() {
        return priceEnd;
    }

    public CarType priceEnd(BigDecimal priceEnd) {
        this.priceEnd = priceEnd;
        return this;
    }

    public void setPriceEnd(BigDecimal priceEnd) {
        this.priceEnd = priceEnd;
    }

    public Boolean isHasPrice() {
        return hasPrice;
    }

    public CarType hasPrice(Boolean hasPrice) {
        this.hasPrice = hasPrice;
        return this;
    }

    public void setHasPrice(Boolean hasPrice) {
        this.hasPrice = hasPrice;
    }

    public Series getSeries() {
        return series;
    }

    public CarType series(Series series) {
        this.series = series;
        return this;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarType)) {
            return false;
        }
        return id != null && id.equals(((CarType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CarType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", yearName='" + getYearName() + "'" +
            ", hasProduction='" + isHasProduction() + "'" +
            ", priceStart=" + getPriceStart() +
            ", priceEnd=" + getPriceEnd() +
            ", hasPrice='" + isHasPrice() + "'" +
            "}";
    }
}
