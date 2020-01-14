package com.example.lit_fits_application.clients;

import com.example.lit_fits_application.entities.Color;
import com.example.lit_fits_application.entities.FashionExpert;
import com.example.lit_fits_application.entities.Material;

import java.util.List;

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
    @POST
    Call<Void> create(@Body FashionExpert fashionExpert);

    /**
     * Updates the data of a Fashion Expert
     *
     * @param fashionExpert
     */
    @PUT
    Call<Void> edit(@Body FashionExpert fashionExpert);


    /**
     * Finds and returns an expert by a given id
     *
     * @param id
     * @return FashionExpert
     */
    @GET
    Call<FashionExpert> find(@Path("id") String id);

    /**
     * Reestablishes the password of associated Expert with the given username, sending an email with the new password
     *
     * @param username
     */
    @GET
    Call<Void> reestablishPassword(@Path("username") String username);

    /**
     * Deletes the Expert with the given id
     *
     * @param id
     */
    @DELETE
    Call<Void> remove(@Path("id") String id);

    /**
     * Takes an username and password ,encapsulated in a FashionExpert object,
     * and returns the full expert
     *
     * @param fashionExpert
     * @return FashionExpert
     */
    @POST("login/")
    Call<FashionExpert> login(@Body FashionExpert fashionExpert);
    /* Yet to implement the get all recommended colors and materials */

    /**
     * Finds all the colors recommended by experts
     * @return List of Colors
     */
    @GET
    Call<List<Color>> recommendedColors();

    /**
     * Finds all the materials recommended by experts
     * @return List of Material
     */
    @GET
    Call<List<Material>> recommendedMaterials();
}
