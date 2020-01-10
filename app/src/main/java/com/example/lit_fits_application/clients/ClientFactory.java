package com.example.lit_fits_application.clients;

/**
 * Factory for the REST clients
 */
public class ClientFactory {
    /**
     * Returns a new ColorClient object
     *
     * @return ColorClient
     */
    public ColorClient getColorClient() {
        return new ColorClient();
    }

    /**
     * Returns a new MaterialClient object
     *
     * @return MaterialClient
     */
    public MaterialClient getMaterialClient() {
        return new MaterialClient();
    }

    /**
     * Returns a new CompanyClient object
     *
     * @return CompanyClient
     */
    public CompanyClient getCompanyClient() {
        return new CompanyClient();
    }

    /**
     * Returns a new GarmentClient object
     *
     * @return GarmentClient
     */
    public GarmentClient getGarmentClient() {
        return new GarmentClient();
    }

    /**
     * Returns a new PublicKeyClient object
     *
     * @return PublicKeyClient
     */
    public PublicKeyClient getPublicKeyClient() {
        return new PublicKeyClient();
    }
    /**
     * Returns a new UserClient object
     *
     * @return UserClient
     */
    public UserClient getUserClient() {
        return new UserClient();
    }
    /**
     * Returns a new ExpertClient object
     *
     * @return ExpertClient
     */
    /*
    public ExpertClient getExpertClient() {
        return new ExpertClient();
    }
     */
}
