package com.buttercms.model;

import java.util.Objects;
import java.util.StringJoiner;

public class PostMeta {
    private PostSummary previousPost;
    private PostSummary nextPost;

    public PostSummary getPreviousPost() {
        return previousPost;
    }

    public void setPreviousPost(PostSummary previousPost) {
        this.previousPost = previousPost;
    }

    public PostSummary getNextPost() {
        return nextPost;
    }

    public void setNextPost(PostSummary nextPost) {
        this.nextPost = nextPost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostMeta postMeta = (PostMeta) o;
        return Objects.equals(previousPost, postMeta.previousPost) &&
                Objects.equals(nextPost, postMeta.nextPost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previousPost, nextPost);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PostMeta.class.getSimpleName() + "[", "]")
                .add("previousPost=" + previousPost)
                .add("nextPost=" + nextPost)
                .toString();
    }
}
