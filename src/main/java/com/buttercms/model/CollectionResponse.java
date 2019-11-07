package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionResponse<T> {

    private PaginationMeta meta;
    private Collection<T> data;

    public PaginationMeta getMeta() {
        return meta;
    }

    public void setMeta(PaginationMeta meta) {
        this.meta = meta;
    }

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollectionResponse<?> response = (CollectionResponse<?>) o;

        if (meta != null ? !meta.equals(response.meta) : response.meta != null) return false;
        return data != null ? data.equals(response.data) : response.data == null;
    }

    @Override
    public int hashCode() {
        int result = meta != null ? meta.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CollectionResponse{" +
                "meta=" + meta +
                ", data=" + data +
                '}';
    }
}
