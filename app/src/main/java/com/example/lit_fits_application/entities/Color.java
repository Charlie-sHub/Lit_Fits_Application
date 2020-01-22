package com.example.lit_fits_application.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 * Color Class
 *
 * @author Charlie Mendez
 */
public class Color implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Unique name for the color
     */
    private String name;

    public Color() {
    }

    public Color(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Color{" + "name=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Color other = (Color) obj;
        return Objects.equals(this.name, other.name);
    }
}
