package com.example.lit_fits_application.clients;

import retrofit2.http.GET;

public interface PublicKeyClientInterface {
    /**
     * Closes the client
     */
    void close();

    /**
     * Gets the public key
     *
     * @return byte[]
     */
    @GET
    byte[] getPublicKey();
}
