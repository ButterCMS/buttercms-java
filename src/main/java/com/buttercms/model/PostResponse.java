package com.buttercms.model;

import java.util.Objects;
import java.util.StringJoiner;

public class PostResponse {
    private Post data;

    private PostMeta meta;

    public Post getData() {
        return data;
    }

    public void setData(Post data) {
        this.data = data;
    }

    public PostMeta getMeta() {
        return meta;
    }

    public void setMeta(PostMeta meta) {
        this.meta = meta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostResponse that = (PostResponse) o;
        return Objects.equals(data, that.data) &&
                Objects.equals(meta, that.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, meta);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PostResponse.class.getSimpleName() + "[", "]")
                .add("data=" + data)
                .add("meta=" + meta)
                .toString();
    }
}
