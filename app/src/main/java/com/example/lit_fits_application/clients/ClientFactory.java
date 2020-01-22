package com.example.lit_fits_application.clients;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Factory for the REST clients
 */
public class ClientFactory {
    /**
     * Receives the address of the server and returns a Retrofit object to which to create the clients
     * @param uri
     * @return Retrofit
     */
    private Retrofit getRetrofit(String uri) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(uri).addConverterFactory(SimpleXmlConverterFactory.create());
        return builder.client(httpClient.build()).build();
    }

    /**
     * Returns a new ColorClient object
     *
     * @return ColorClientInterface
     */
    public ColorClientInterface getColorClient(String uri) {
        Retrofit retrofit = getRetrofit(uri);
        ColorClientInterface colorClientInterface = retrofit.create(ColorClientInterface.class);
        return colorClientInterface;
    }

    /**
     * Returns a new MaterialClient object
     *
     * @return MaterialClient
     */
    public MaterialClientInterface getMaterialClient(String uri) {
        Retrofit retrofit = getRetrofit(uri);
        MaterialClientInterface materialClientInterface = retrofit.create(MaterialClientInterface.class);
        return materialClientInterface;
    }

    /**
     * Returns a new CompanyClient object
     *
     * @return CompanyClient
     */
    public CompanyClientInterface getCompanyClient(String uri) {
        Retrofit retrofit = getRetrofit(uri);
        CompanyClientInterface companyClientInterface = retrofit.create(CompanyClientInterface.class);
        return companyClientInterface;
    }

    /**
     * Returns a new GarmentClient object
     *
     * @return GarmentClient
     */
    public GarmentClientInterface getGarmentClient(String uri) {
        Retrofit retrofit = getRetrofit(uri);
        GarmentClientInterface garmentClientInterface = retrofit.create(GarmentClientInterface.class);
        return garmentClientInterface;
    }

    /**
     * Returns a new PublicKeyClient object
     *
     * @return PublicKeyClient
     */
    public PublicKeyClientInterface getPublicKeyClient(String uri) {
        Retrofit retrofit = getRetrofit(uri);
        PublicKeyClientInterface publicKeyClientInterface = retrofit.create(PublicKeyClientInterface.class);
        return publicKeyClientInterface;
    }
    /**
     * Returns a new UserClient object
     *
     * @return UserClient
     */
    public UserClientInterface getUserClient(String uri) {
        Retrofit retrofit = getRetrofit(uri);
        UserClientInterface userClientInterface = retrofit.create(UserClientInterface.class);
        return userClientInterface;
    }
    /**
     * Returns a new ExpertClient object
     *
     * @return ExpertClient
     */
    public ExpertClientInterface getExpertClient(String uri) {
        Retrofit retrofit = getRetrofit(uri);
        ExpertClientInterface expertClientFactory = retrofit.create(ExpertClientInterface.class);
        return expertClientFactory;
    }
}
