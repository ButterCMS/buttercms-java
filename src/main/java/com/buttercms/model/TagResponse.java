package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TagResponse {
    private Tag data;

    public Tag getData() {
        return data;
    }

    public TagResponse setData(Tag data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagResponse that = (TagResponse) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TagResponse.class.getSimpleName() + "[", "]")
                .add("data=" + data)
                .toString();
    }
}
