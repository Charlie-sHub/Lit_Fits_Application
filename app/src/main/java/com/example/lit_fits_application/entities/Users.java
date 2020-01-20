package com.example.lit_fits_application.entities;

import java.io.Serializable;
import java.util.List;

public class Users implements Serializable {
    @ElementList
    private List<User> users;
}
