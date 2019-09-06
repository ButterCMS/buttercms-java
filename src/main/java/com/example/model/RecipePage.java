package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipePage {
    private String testContent;
    private Date dateField;
    private Double number;

    public String getTestContent() {
        return testContent;
    }

    public RecipePage setTestContent(String testContent) {
        this.testContent = testContent;
        return this;
    }

    public Date getDateField() {
        return dateField;
    }

    public RecipePage setDateField(Date dateField) {
        this.dateField = dateField;
        return this;
    }

    public Double getNumber() {
        return number;
    }

    public RecipePage setNumber(Double number) {
        this.number = number;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipePage that = (RecipePage) o;
        return Objects.equals(testContent, that.testContent) &&
                Objects.equals(dateField, that.dateField) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testContent, dateField, number);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RecipePage.class.getSimpleName() + "[", "]")
                .add("testContent='" + testContent + "'")
                .add("dateField=" + dateField)
                .add("number=" + number)
                .toString();
    }
}
