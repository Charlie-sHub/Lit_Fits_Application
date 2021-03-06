package com.example.lit_fits_application.clients;

import com.example.lit_fits_application.entities.Material;
import com.example.lit_fits_application.entities.Materials;

import retrofit2.Call;
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
public interface MaterialClientInterface {
    /**
     * Closes the client
     */
    void close();

    /**
     * Counts the amount of Materials
     *
     * @return int
     */
    @GET("litfitsserver.entities.material/count")
    Call<Integer> count();

    /**
     * Creates a new Material
     *
     * @param Material
     */
    @POST("litfitsserver.entities.material")
    Call<Void> create(@Body Material Material);

    /**
     * Edits a given Material
     *
     * @param Material
     */
    @PUT("litfitsserver.entities.material")
    Call<Void> edit(@Body Material Material);

    /**
     * Finds all the Materials
     *
     * @param name
     * @return Material
     */
    @GET("litfitsserver.entities.material/{name}")
    Call<Material> find(@Path("name") String name);

    /**
     * Finds all the Materials
     *
     * @return List of Materials
     */
    @GET("litfitsserver.entities.material")
    Call<Materials> findAll();

    /**
     * Deletes a Material given its name
     *
     * @param name
     */
    @DELETE("litfitsserver.entities.material/{name}")
    Call<Void> remove(@Path("name")String name);
}
