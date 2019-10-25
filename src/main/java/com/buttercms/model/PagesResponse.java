package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PagesResponse<T> {
    private List<Page<T>> data;
    private PaginationMeta meta;

    public List<Page<T>> getData() {
        return data;
    }

    public PagesResponse setData(List<Page<T>> data) {
        this.data = data;
        return this;
    }

    public PaginationMeta getMeta() {
        return meta;
    }

    public PagesResponse setMeta(PaginationMeta meta) {
        this.meta = meta;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagesResponse that = (PagesResponse) o;
        return Objects.equals(data, that.data) &&
                Objects.equals(meta, that.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, meta);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PagesResponse.class.getSimpleName() + "[", "]")
                .add("data=" + data)
                .add("meta=" + meta)
                .toString();
    }
}
