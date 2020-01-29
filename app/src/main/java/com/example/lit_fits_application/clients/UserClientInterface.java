package com.example.lit_fits_application.clients;

import com.example.lit_fits_application.entities.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interface for the userClient
 *
 * @author Carlos Mendez
 */
public interface UserClientInterface {
    /**
     * Gets the user with the received username.
     *
     * @param username The username for the user that will be selected.
     * @return The user with all the data.
     */
    @GET("litfitsserver.entities.user/{id}")
    Call<User> findUser(@Path("id") String username);

    /**
     * Removes a user from the database.
     *
     * @param username The username for the user that will be removed.
     */
    @DELETE("litfitsserver.entities.user/{id}")
    Call<ResponseBody> removeUser(@Path("id") String username);

    /**
     * Returns the user that contains the received email.
     *
     * @param email The email that will be used to filter.
     * @return The user with all the data.
     */
    @GET("litfitsserver.entities.user/user/{email}")
    Call<User> findUserByEmail(@Path("email") String email);

    /**
     * Returns all the users on the database.
     *
     * @return All the users with their data.
     */
    @GET("litfitsserver.entities.user")
    Call<List<User>> findAllUser();

    /**
     * Updates the data for the received user.
     *
     * @param user
     */
    @PUT("litfitsserver.entities.user")
    Call<Void> editUser(@Body User user);

    /**
     * Inserts a new user on the database.
     *
     * @param user
     */
    @POST("litfitsserver.entities.user")
    Call<Void> createUser(@Body User user);

    /**
     * Counts the number of users in the database.
     *
     * @return
     */
    @GET("litfitsserver.entities.user/count")
    Call<Integer> countRESTUser();

    /**
     * Logs in a given User
     *
     * @param user
     * @return User
     */
    @POST("litfitsserver.entities.user/login")
    Call<User> login(@Body User user);

    /**
     * This method closes the client.
     */
    void close();
}
