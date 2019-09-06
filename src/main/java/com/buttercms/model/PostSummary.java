package com.buttercms.model;

import java.util.Objects;
import java.util.StringJoiner;

public class PostSummary {
    private String slug;
    private String title;
    private String featuredImage;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostSummary that = (PostSummary) o;
        return Objects.equals(slug, that.slug) &&
                Objects.equals(title, that.title) &&
                Objects.equals(featuredImage, that.featuredImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, title, featuredImage);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PostSummary.class.getSimpleName() + "[", "]")
                .add("slug='" + slug + "'")
                .add("title='" + title + "'")
                .add("featuredImage='" + featuredImage + "'")
                .toString();
    }
}
