package com.billcat.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.billcat.domain.Indicator} entity.
 */
public class IndicatorDTO implements Serializable {

    private Long id;

    private String text;


    private Long categoryId;

    private String categoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IndicatorDTO indicatorDTO = (IndicatorDTO) o;
        if (indicatorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), indicatorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IndicatorDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", category=" + getCategoryId() +
            ", category='" + getCategoryName() + "'" +
            "}";
    }
}
