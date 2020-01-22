package com.example.lit_fits_application.clients;

import com.example.lit_fits_application.entities.Garment;
import com.example.lit_fits_application.entities.Garments;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interface for the garmentClient
 * @author Carlos Mendez
 */
public interface GarmentClientInterface {
    /**
     * Closes the client
     */
    void close();

    /**
     * Counts the total amount of garments
     */
    @GET("count")
    Call<Integer> countREST();

    /**
     * Creates a new garment
     */
    @POST
    Call<Void> createGarment(@Body Garment garment);

    /**
     * Updates a garment with the data send
     */
    @PUT
    Call<Void> editGarment(@Body Garment garment);

    /**
     * Finds a garment by id
     *
     * @param id
     * @return Garment
     */
    @GET("{id}")
    Call<Garment> findGarment(@Path("id") String id);

    /**
     * Finds all garments
     *
     * @return List of Garments
     */
    @GET
    Call<Garments> findGarmentAll();

    /**
     * Finds the garment with the given barcode
     *
     * @param barcode
     * @return Garment
     */
    @GET("barcode/{barcode}")
    Call<Garment> findGarmentGarmentByBarcode(@Path("barcode") String barcode);

    /**
     * Finds all the garments of a given company
     *
     * @param nif
     * @return List of Garments
     */
    @GET("company/{nif}")
    Call<Garments> findGarmentGarmentsByCompany(@Path("nif") String nif);

    /**
     * Finds all the garments with or without requested promotions
     *
     * @param requested
     * @return List of Garments
     */
    @GET("request/{requested}")
    Call<Garments> findGarmentGarmentsByRequest(@Path("requested") String requested);

    /**
     * Finds all promoted garments
     *
     * @param promoted
     * @return List of Garments
     */
    @GET("promotion/{promoted}")
    Call<Garments> findGarmentGarmentsPromoted(@Path("promoted") String promoted);

    /**
     * Gets the picture of the garment
     *
     * @param id
     * @return FIle
     */
    @GET("picture/{id}")
    Call<File> getImage(@Path("id")String id);

    /**
     * Deletes the garment associated with the given id
     *
     * @param id
     */
    @DELETE("{id}")
    Call<Void> remove(@Path("id") String id);
}
