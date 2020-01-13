package com.example.lit_fits_application.clients;

import com.example.lit_fits_application.entities.Material;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interface for the materialClient
 * @author Carlos Mendez
 */
public interface materialClientInterface {
    /**
     * Closes the client
     */
    void close();

    /**
     * Counts the amount of Materials
     *
     * @return int
     */
    @GET("count")
    int count();

    /**
     * Creates a new Material
     *
     * @param Material
     */
    @POST
    void create(@Body Material Material);

    /**
     * Edits a given Material
     *
     * @param Material
     */
    @PUT
    void edit(@Body Material Material);

    /**
     * Finds all the Materials
     *
     * @param name
     * @return Material
     */
    @GET("{name}")
    Material find(@Path("name") String name);

    /**
     * Finds all the Materials
     *
     * @return List of Materials
     */
    @GET
    List<Material> findAll();

    /**
     * Deletes a Material given its name
     *
     * @param name
     */
    @DELETE("{name}")
    void remove(@Path("name")String name);
}
