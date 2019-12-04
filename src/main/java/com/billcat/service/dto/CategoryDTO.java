package com.billcat.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.billcat.domain.Category} entity.
 */
public class CategoryDTO implements Serializable {

    private Long id;

    private String name;


    private Long parentId;

    private String parentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long categoryId) {
        this.parentId = categoryId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String categoryName) {
        this.parentName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;
        if (categoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", parent=" + getParentId() +
            ", parent='" + getParentName() + "'" +
            "}";
    }
}
