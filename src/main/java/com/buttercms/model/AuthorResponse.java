package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorResponse {
    private Author data;

    public Author getData() {
        return data;
    }

    public AuthorResponse setData(Author data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorResponse that = (AuthorResponse) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthorResponse.class.getSimpleName() + "[", "]")
                .add("data=" + data)
                .toString();
    }
}
