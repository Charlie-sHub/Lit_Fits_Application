package com.example.lit_fits_application.clients;

import com.example.lit_fits_application.entities.Color;
import com.example.lit_fits_application.entities.Colors;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interface for the colorClient
 * @author Carlos Mendez
 */
public interface ColorClientInterface {
    /**
     * Closes the client
     */
    void close();

    /**
     * Counts the amount of colors
     *
     * @return int
     */
    @GET("litfitsserver.entities.color/count")
    Call<Integer> count();

    /**
     * Creates a new color
     *
     * @param color
     */
    @POST("litfitsserver.entities.color")
    Call<Void> create(@Body Color color);

    /**
     * Edits a given color
     *
     * @param color
     */
    @PUT("litfitsserver.entities.color")
    Call<Void> edit(@Body Color color);

    /**
     * Finds all the colors
     *
     * @param name
     * @return Color
     */
    @GET("litfitsserver.entities.color/{name}")
    Call<Color> find(@Path("name") String name);

    /**
     * Finds all the colors
     *
     * @return List of Colors
     */
    @GET("litfitsserver.entities.color")
    Call<Colors> findAll();

    /**
     * Deletes a color given its name
     *
     * @param name
     */
    @DELETE("litfitsserver.entities.color/{name}")
    Call<Void> remove(@Path("name")String name);
}
