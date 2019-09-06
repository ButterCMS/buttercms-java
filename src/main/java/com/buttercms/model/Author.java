package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    private String slug;
    private String firstName;
    private String lastName;
    private String email;
    private String bio;
    private String title;
    private String linkedinUrl;
    private String facebookUrl;
    private String pinterestUrl;
    private String instagramUrl;
    private String twitterHandle;
    private String profileImage;
    // With query include=recent_posts:
    private List<Post> recentPosts;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getPinterestUrl() {
        return pinterestUrl;
    }

    public void setPinterestUrl(String pinterestUrl) {
        this.pinterestUrl = pinterestUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<Post> getRecentPosts() {
        return recentPosts;
    }

    public Author setRecentPosts(List<Post> recentPosts) {
        this.recentPosts = recentPosts;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(slug, author.slug) &&
                Objects.equals(firstName, author.firstName) &&
                Objects.equals(lastName, author.lastName) &&
                Objects.equals(email, author.email) &&
                Objects.equals(bio, author.bio) &&
                Objects.equals(title, author.title) &&
                Objects.equals(linkedinUrl, author.linkedinUrl) &&
                Objects.equals(facebookUrl, author.facebookUrl) &&
                Objects.equals(pinterestUrl, author.pinterestUrl) &&
                Objects.equals(instagramUrl, author.instagramUrl) &&
                Objects.equals(twitterHandle, author.twitterHandle) &&
                Objects.equals(profileImage, author.profileImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, firstName, lastName, email, bio, title, linkedinUrl, facebookUrl, pinterestUrl, instagramUrl, twitterHandle, profileImage);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Author.class.getSimpleName() + "[", "]")
                .add("slug='" + slug + "'")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("email='" + email + "'")
                .add("bio='" + bio + "'")
                .add("title='" + title + "'")
                .add("linkedinUrl='" + linkedinUrl + "'")
                .add("facebookUrl='" + facebookUrl + "'")
                .add("pinterestUrl='" + pinterestUrl + "'")
                .add("instagramUrl='" + instagramUrl + "'")
                .add("twitterHandle='" + twitterHandle + "'")
                .add("profileImage='" + profileImage + "'")
                .add("recentPosts=" + recentPosts)
                .toString();
    }
}
