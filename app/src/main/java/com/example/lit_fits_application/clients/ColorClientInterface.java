package com.example.lit_fits_application.clients;

import com.example.lit_fits_application.entities.Color;

import java.util.List;

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
    @GET("count")
    int count();

    /**
     * Creates a new color
     *
     * @param color
     */
    @POST
    void create(@Body Color color);

    /**
     * Edits a given color
     *
     * @param color
     */
    @PUT
    void edit(@Body Color color);

    /**
     * Finds all the colors
     *
     * @param name
     * @return Color
     */
    @GET("{name}")
    Color find(@Path("name") String name);

    /**
     * Finds all the colors
     *
     * @return List of Colors
     */
    @GET
    List<Color> findAll();

    /**
     * Deletes a color given its name
     *
     * @param name
     */
    @DELETE("{name}")
    void remove(@Path("name")String name);
}
