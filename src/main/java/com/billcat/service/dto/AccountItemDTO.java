package com.billcat.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import com.billcat.domain.enumeration.Category;

/**
 * A DTO for the {@link com.billcat.domain.AccountItem} entity.
 */
public class AccountItemDTO implements Serializable {

    private Long id;

    private Instant date;

    private String transactionType;

    private String description;

    private Double amount;

    private String currency;

    private Category category;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountItemDTO accountItemDTO = (AccountItemDTO) o;
        if (accountItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountItemDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", description='" + getDescription() + "'" +
            ", amount=" + getAmount() +
            ", currency='" + getCurrency() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
