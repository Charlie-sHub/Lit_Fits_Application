package com.example.lit_fits_application.entities;

import java.io.Serializable;
import java.util.List;

public class Experts implements Serializable {
    @ElementList
    private List<FashionExpert> experts;
}
