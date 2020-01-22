package com.example.lit_fits_application.entities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "companies")
public class Companies {
    @ElementList(data = false, empty = true, inline = true, name = "company", required = false)
    private List<Company> companies;

    public Companies() {
    }

    public Companies(List<Company> companies) {
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
