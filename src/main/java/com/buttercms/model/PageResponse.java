package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponse<T> {
    private Page<T> data;

    public Page<T> getData() {
        return data;
    }

    public PageResponse setData(Page<T> data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageResponse that = (PageResponse) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PageResponse.class.getSimpleName() + "[", "]")
                .add("data=" + data)
                .toString();
    }
}
