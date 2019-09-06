package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorsResponse {
    private List<Author> data;

    public List<Author> getData() {
        return data;
    }

    public AuthorsResponse setData(List<Author> data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorsResponse that = (AuthorsResponse) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthorsResponse.class.getSimpleName() + "[", "]")
                .add("data=" + data)
                .toString();
    }
}
