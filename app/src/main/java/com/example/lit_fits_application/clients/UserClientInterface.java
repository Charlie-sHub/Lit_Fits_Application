package com.example.lit_fits_application.clients;

import com.example.lit_fits_application.entities.User;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interface for the userClient
 * @author Carlos Mendez
 */
public interface UserClientInterface {
    /**
     * Gets the user with the received username.
     *
     * @param username The username for the user that will be selected.
     * @return The user with all the data.
     */
    @GET("{id}")
    User findUser(@Path("id") String username);

    /**
     * Removes a user from the database.
     *
     * @param username The username for the user that will be removed.
     */
    @DELETE("{id}")
    void removeUser (@Path("id") String username);

    /**
     * Returns the user that contains the received email.
     *
     * @param email The email that will be used to filter.
     * @return The user with all the data.
     */
    @GET("user/{email}")
    User findUserByEmail (@Path("email") String email);

    /**
     * Returns all the users on the database.
     *
     * @return All the users with their data.
     */
    @GET
    List<User> findAllUser ();

    /**
     * Updates the data for the received user.
     *
     * @param user
     */
    @PUT
    void editUser (@Body User user);

    /**
     * Inserts a new user on the database.
     * @param user
     */
    @POST
    void createUser (@Body User user);

    /**
     * Counts the number of users in the database.
     *
     * @return
     */
    @GET("count")
    int countRESTUser ();

    /**
     * This method closes the client.
     */
    void close ();
}
