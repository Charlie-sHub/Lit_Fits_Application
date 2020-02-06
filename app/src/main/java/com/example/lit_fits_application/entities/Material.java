package com.example.lit_fits_application.entities;

import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Objects;

/**
 * Defines the different materials a garment can be made out of
 *
 * @author Charlie
 */
@Root (name="materials")
public class Material implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Unique name for the material
     */
    private String name;

    public Material() {
    }

    public Material(String name) {
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
        return "Material{" + "name=" + name + '}';
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
        final Material other = (Material) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
