package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Collection {

    private String first_field;
    private String second_filed;

    public String getFirst_field() {
        return first_field;
    }

    public void setFirst_field(String first_field) {
        this.first_field = first_field;
    }

    public String getSecond_filed() {
        return second_filed;
    }

    public void setSecond_filed(String second_filed) {
        this.second_filed = second_filed;
    }
}
