package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Page<T> {
    private String slug;
    private T fields;

    public String getSlug() {
        return slug;
    }

    public Page setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public T getFields() {
        return fields;
    }

    public Page setFields(T fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(slug, page.slug) &&
                Objects.equals(fields, page.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, fields);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Page.class.getSimpleName() + "[", "]")
                .add("slug='" + slug + "'")
                .add("fields=" + fields)
                .toString();
    }
}
