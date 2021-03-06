package com.example.lit_fits_application.clients;

import com.example.lit_fits_application.entities.Colors;
import com.example.lit_fits_application.entities.FashionExpert;
import com.example.lit_fits_application.entities.Materials;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interface for the expertClient
 * @author Carlos Mendez
 */
public interface ExpertClientInterface {
    /**
     * Closes the instance of the client
     */
    void close();

    /**
     * Sends a request to save a new fashion expert in the database
     *
     * @param fashionExpert
     */
    @POST("litfitsserver.entities.fashionexpert")
    Call<Void> create(@Body FashionExpert fashionExpert);

    /**
     * Updates the data of a Fashion Expert
     *
     * @param fashionExpert
     */
    @PUT("litfitsserver.entities.fashionexpert")
    Call<Void> edit(@Body FashionExpert fashionExpert);


    /**
     * Finds and returns an expert by a given id
     *
     * @param id
     * @return FashionExpert
     */
    @GET("litfitsserver.entities.fashionexpert/{id}")
    Call<FashionExpert> find(@Path("id") String id);

    /**
     * Reestablishes the password of associated Expert with the given username, sending an email with the new password
     *
     * @param username
     */
    @GET("litfitsserver.entities.fashionexpert/passwordReestablishment/{id}")
    Call<Void> reestablishPassword(@Path("username") String username);

    /**
     * Deletes the Expert with the given id
     *
     * @param id
     */
    @DELETE("litfitsserver.entities.fashionexpert/{id}")
    Call<Void> remove(@Path("id") String id);

    /**
     * Takes an username and password ,encapsulated in a FashionExpert object,
     * and returns the full expert
     *
     * @param fashionExpert
     * @return FashionExpert
     */
    @POST("litfitsserver.entities.fashionexpert/login")
    Call<FashionExpert> login(@Body FashionExpert fashionExpert);
    /* Yet to implement the get all recommended colors and materials */

    /**
     * Finds all the colors recommended by experts
     * @return List of Colors
     */
    @GET("litfitsserver.entities.fashionexpert/colors")
    Call<Colors> recommendedColors();

    /**
     * Finds all the materials recommended by experts
     * @return List of Material
     */
    @GET("litfitsserver.entities.fashionexpert/materials")
    Call<Materials> recommendedMaterials();
}
