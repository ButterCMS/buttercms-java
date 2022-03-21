package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    private Author author;
    private String body;
    private List<Category> categories;
    private Date created;
    private String metaDescription;
    private Date published;
    private String seoTitle;
    private String slug;
    private Status status;
    private String summary;
    private List<Tag> tags;
    private String title;
    private String url;
    private String featuredImage;
    private String featuredImageAlt;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFeaturedImage() { return featuredImage; }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFeaturedImageAlt() { return featuredImageAlt; }

    public void setFeaturedImageAlt(String featuredImageAlt) { this.featuredImageAlt = featuredImageAlt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(author, post.author) &&
                Objects.equals(body, post.body) &&
                Objects.equals(categories, post.categories) &&
                Objects.equals(created, post.created) &&
                Objects.equals(metaDescription, post.metaDescription) &&
                Objects.equals(published, post.published) &&
                Objects.equals(seoTitle, post.seoTitle) &&
                Objects.equals(slug, post.slug) &&
                status == post.status &&
                Objects.equals(summary, post.summary) &&
                Objects.equals(tags, post.tags) &&
                Objects.equals(title, post.title) &&
                Objects.equals(url, post.url) &&
                Objects.equals(featuredImage, post.featuredImage) &&
                Objects.equals(featuredImageAlt, post.featuredImageAlt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, body, categories, created, metaDescription, published, seoTitle, slug, status, summary, tags, title, url, featuredImage, featuredImageAlt);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Post.class.getSimpleName() + "[", "]")
                .add("author='" + author + "'")
                .add("body='" + body + "'")
                .add("metaDescription='" + metaDescription + "'")
                .add("seoTitle='" + seoTitle + "'")
                .add("slug='" + slug + "'")
                .add("summary='" + summary + "'")
                .add("title='" + title + "'")
                .add("url='" + url + "'")
                .add("featuredImage='" + featuredImage + "'")
                .add("featuredImageAlt='" + featuredImageAlt + "'")
                .toString();
    }

    public enum Status {
        @JsonProperty("draft")
        DRAFT,

        @JsonProperty("published")
        PUBLISHED
    }
}
