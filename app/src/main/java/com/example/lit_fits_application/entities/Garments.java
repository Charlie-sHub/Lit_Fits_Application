package com.example.lit_fits_application.entities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "garments")
public class Garments {
    @ElementList(data = false, empty = true, name = "garment", inline = true,  required = false)
    private List<Garment> garments;

    public Garments() {
    }

    public Garments(List<Garment> garments) {
        this.garments = garments;
    }

    public List<Garment> getGarments() {
        return garments;
    }

    public void setGarments(List<Garment> garments) {
        this.garments = garments;
    }
}
