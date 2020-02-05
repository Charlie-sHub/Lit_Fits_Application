package com.example.lit_fits_application.entities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "colors")
public class Colors {
    @ElementList(data = false, empty = true, inline = true, name = "color", required = false)
    private List<Color> color;

    public Colors() {
    }

    public Colors(List<Color> colors) {
        this.color = color;
    }

    public List<Color> getColors() {
        return color;
    }

    public void setColors(List<Color> colors) {
        this.color = color;
    }
}
