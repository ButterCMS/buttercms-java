package com.buttercms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationMeta {
    private int count;
    private int previousPage;
    private int nextPage;

    public int getCount() {
        return count;
    }

    public PaginationMeta setCount(int count) {
        this.count = count;
        return this;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public PaginationMeta setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
        return this;
    }

    public int getNextPage() {
        return nextPage;
    }

    public PaginationMeta setNextPage(int nextPage) {
        this.nextPage = nextPage;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaginationMeta that = (PaginationMeta) o;
        return count == that.count &&
                previousPage == that.previousPage &&
                nextPage == that.nextPage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, previousPage, nextPage);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PaginationMeta.class.getSimpleName() + "[", "]")
                .add("count=" + count)
                .add("previousPage=" + previousPage)
                .add("nextPage=" + nextPage)
                .toString();
    }
}
