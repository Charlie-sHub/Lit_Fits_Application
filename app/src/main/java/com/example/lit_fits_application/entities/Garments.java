package com.example.lit_fits_application.entities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "garments")
public class Garments {
    @ElementList(data = false, empty = true, inline = true, name = "garment", required = false)
    private List<Garment> experts = new ArrayList<>();

    public Garments() {
    }

    public Garments(List<Garment> experts) {
        this.experts = experts;
    }

    public List<Garment> getExperts() {
        return experts;
    }

    public void setExperts(List<Garment> experts) {
        this.experts = experts;
    }
}
