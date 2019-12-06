package org.asdtiang.carbrand.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Series.
 */
@Entity
@Table(name = "series")
public class Series implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price_start", precision = 21, scale = 2)
    private BigDecimal priceStart;

    @Column(name = "price_end", precision = 21, scale = 2)
    private BigDecimal priceEnd;

    @NotNull
    @Column(name = "has_price", nullable = false)
    private Boolean hasPrice;

    @ManyToOne
    @JsonIgnoreProperties("series")
    private Brand brand;

    @ManyToOne
    @JsonIgnoreProperties("series")
    private BrandType brandType;

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

    public Series name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPriceStart() {
        return priceStart;
    }

    public Series priceStart(BigDecimal priceStart) {
        this.priceStart = priceStart;
        return this;
    }

    public void setPriceStart(BigDecimal priceStart) {
        this.priceStart = priceStart;
    }

    public BigDecimal getPriceEnd() {
        return priceEnd;
    }

    public Series priceEnd(BigDecimal priceEnd) {
        this.priceEnd = priceEnd;
        return this;
    }

    public void setPriceEnd(BigDecimal priceEnd) {
        this.priceEnd = priceEnd;
    }

    public Boolean isHasPrice() {
        return hasPrice;
    }

    public Series hasPrice(Boolean hasPrice) {
        this.hasPrice = hasPrice;
        return this;
    }

    public void setHasPrice(Boolean hasPrice) {
        this.hasPrice = hasPrice;
    }

    public Brand getBrand() {
        return brand;
    }

    public Series brand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public BrandType getBrandType() {
        return brandType;
    }

    public Series brandType(BrandType brandType) {
        this.brandType = brandType;
        return this;
    }

    public void setBrandType(BrandType brandType) {
        this.brandType = brandType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Series)) {
            return false;
        }
        return id != null && id.equals(((Series) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Series{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priceStart=" + getPriceStart() +
            ", priceEnd=" + getPriceEnd() +
            ", hasPrice='" + isHasPrice() + "'" +
            "}";
    }
}
