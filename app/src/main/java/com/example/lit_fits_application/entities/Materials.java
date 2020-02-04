package com.example.lit_fits_application.entities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "materials")
public class Materials {
    @ElementList(data = false, empty = true, inline = true, name = "material", required = false)
    private List<Material> materials = new ArrayList<>();

    public Materials() {
    }

    public Materials(List<Material> materials) {
        this.materials = materials;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }
}
