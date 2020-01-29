package com.example.lit_fits_application.clients;

import com.example.lit_fits_application.entities.Companies;
import com.example.lit_fits_application.entities.Company;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interface for the companyClient
 * Probably useless for the app but i made it just in case
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
    @GET("litfitsserver.entities.company/count")
    Call<Integer> countREST();

    /**
     * Sends a request to save a new company in the database
     *
     * @param company
     */
    @POST("litfitsserver.entities.company")
    Call<Void> create(@Body Company company);

    /**
     * Updates the data of a given company
     *
     * @param company
     */
    @PUT("litfitsserver.entities.company")
    Call<Void> edit(@Body Company company);

    /**
     * Finds and returns a company by a given id
     *
     * @param id
     * @return Company
     */
    @GET("litfitsserver.entities.company/{id}")
    Call<Company> find(@Path("id") String id);

    /**
     * Returns all the companies in the database
     *
     * @return List of Companies
     */
    @GET("litfitsserver.entities.company")
    Call<Companies> findAll();

    /**
     * Finds and returns a company by a given nif
     *
     * @param nif
     * @return Company
     */
    @GET("litfitsserver.entities.company/company/{nif}")
    Call<Company> findCompanyByNif(@Path("nif") String nif);

    /**
     * Takes a nif and password (encapsulated in a Company object) and returns the full company
     *
     * @param company
     * @return Company
     */
    @POST("litfitsserver.entities.company/login")
    Call<Company> login(@Body Company company);

    /**
     * Reestablishes the password of associated with the given nif, sending an email with the new password
     *
     * @param nif
     */
    @GET("litfitsserver.entities.company/passwordReestablishment/{nif}")
    Call<Void> reestablishPassword(@Path("nif")String nif);

    /**
     * Deletes the company with the given id
     *
     * @param id
     */
    @DELETE("litfitsserver.entities.company/{id}")
    Call<Void> remove(@Path("id")String id);

}
