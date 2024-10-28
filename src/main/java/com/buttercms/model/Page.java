package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Page<T> {
    private String slug;
    private String name;
    private T fields;
    private Status status;
    private Date updated;
    private Optional<String> pageType;
    private Optional<Date> published;
    private Optional<Date> scheduled;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public Optional<Date> getScheduled() {
        return scheduled;
    }

    public void setScheduled(Date scheduled) {
        this.scheduled = Optional.ofNullable(scheduled);
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Optional<Date> getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = Optional.ofNullable(published);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = Optional.ofNullable(pageType);
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

