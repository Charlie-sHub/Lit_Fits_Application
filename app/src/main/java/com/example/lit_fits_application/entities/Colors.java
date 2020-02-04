package com.example.lit_fits_application.entities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "colors")
public class Colors {
    @ElementList(data = false, empty = true, inline = true, name = "color", required = false)
    private List<Color> colors = new ArrayList<>();

    public Colors() {
    }

    public Colors(List<Color> colors) {
        this.colors = colors;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }
}
