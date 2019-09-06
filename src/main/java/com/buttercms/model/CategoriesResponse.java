package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriesResponse {
    private List<Category> data;

    public List<Category> getData() {
        return data;
    }

    public CategoriesResponse setData(List<Category> data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriesResponse that = (CategoriesResponse) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CategoriesResponse.class.getSimpleName() + "[", "]")
                .add("data=" + data)
                .toString();
    }
}
