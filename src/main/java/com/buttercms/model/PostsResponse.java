package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostsResponse {
    private List<Post> data;
    private PaginationMeta meta;

    public List<Post> getData() {
        return data;
    }

    public PostsResponse setData(List<Post> data) {
        this.data = data;
        return this;
    }

    public PaginationMeta getMeta() {
        return meta;
    }

    public PostsResponse setMeta(PaginationMeta meta) {
        this.meta = meta;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostsResponse that = (PostsResponse) o;
        return Objects.equals(data, that.data) &&
                Objects.equals(meta, that.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, meta);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PostsResponse.class.getSimpleName() + "[", "]")
                .add("data=" + data)
                .add("meta=" + meta)
                .toString();
    }
}
