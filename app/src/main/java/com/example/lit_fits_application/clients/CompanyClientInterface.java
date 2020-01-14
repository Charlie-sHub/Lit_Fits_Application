package com.example.lit_fits_application.clients;

import com.example.lit_fits_application.entities.Company;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interface for the companyClient
 * Probably useless for the app
 * @author Carlos Mendez
 */
public interface CompanyClientInterface {
    /**
     * Closes the instance of the client
     */
    void close();

    /**
     * Counts the amount of companies in the database
     *
     * @return String
     */
    @GET("count")
    Call<Integer> countREST();

    /**
     * Sends a request to save a new company in the database
     *
     * @param company
     */
    @POST
    Call<Void> create(@Body Company company);

    /**
     * Updates the data of a given company
     *
     * @param company
     */
    @PUT
    Call<Void> edit(@Body Company company);

    /**
     * Finds and returns a company by a given id
     *
     * @param id
     * @return Company
     */
    @GET("{id}")
    Call<Company> find(@Path("id") String id);

    /**
     * Returns all the companies in the database
     *
     * @return List of Companies
     */
    @GET
    Call<List<Company>> findAll();

    /**
     * Finds and returns a company by a given nif
     *
     * @param nif
     * @return Company
     */
    @GET("company/{nif}")
    Call<Company> findCompanyByNif(@Path("nif") String nif);

    /**
     * Takes a nif and password (encapsulated in a Company object) and returns the full company
     *
     * @param company
     * @return Company
     */
    @POST("login/")
    Call<Company> login(@Body Company company);

    /**
     * Reestablishes the password of associated with the given nif, sending an email with the new password
     *
     * @param nif
     */
    @GET("passwordReestablishment/{nif}")
    Call<Void> reestablishPassword(@Path("nif")String nif);

    /**
     * Deletes the company with the given id
     *
     * @param id
     */
    @DELETE("{id}")
    Call<Void> remove(@Path("id")String id);

}
