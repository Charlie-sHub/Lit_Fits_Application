package com.example.lit_fits_application.entities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Root(name = "experts")
public class Experts implements Serializable {
    @ElementList(data = false, empty = true, inline = true, name = "expert", required = false)
    private List<FashionExpert> experts = new ArrayList<>();

    public Experts() {
    }

    public Experts(List<FashionExpert> experts) {
        this.experts = experts;
    }

    public List<FashionExpert> getExperts() {
        return experts;
    }

    public void setExperts(List<FashionExpert> experts) {
        this.experts = experts;
    }
}
