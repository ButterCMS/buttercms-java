package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Page<T> {
    private String slug;
    private String pageType;
    private T fields;
    private Status status;
    private Date scheduled;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public T getFields() {
        return fields;
    }

    public void setFields(T fields) {
        this.fields = fields;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getScheduled() {
        return scheduled;
    }

    public void setScheduled(Date scheduled) {
        this.scheduled = scheduled;
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

    public enum Status {
        @JsonProperty("draft")
        DRAFT,
        @JsonProperty("published")
        PUBLISHED,
        @JsonProperty("scheduled")
        SCHEDULED
    }
}

