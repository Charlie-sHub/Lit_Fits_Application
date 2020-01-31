package com.example.lit_fits_application.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * The class that will be used to manage all the users data.
 *
 * @author Asier
 */

public class User implements Serializable {

    protected String username;
    protected String fullName;
    protected String password;
    protected String phoneNumber;
    protected String email;
    protected String lastAccess;
    protected String lastPasswordChange;
    protected UserType type;
    private Set<Color> likedColors;
    private Set<Material> likedMaterials;
    private Set<Garment> garments;

    /**
     * Empty constructor
     */
    public User() {

    }

    /**
     * Simple user constructor, used for classes that extend user
     *
     * @param username           The username that will be set.
     * @param fullName           The full name that will be set.
     * @param password           The password that that will be set.
     * @param phoneNumber        The phone number that will be set.
     * @param email              The email that will be set.
     * @param lastAccess         The last access date that will be set.
     * @param lastPasswordChange The last password change date that will be set.
     * @param type               The type of the user that will be set.
     */
    public User(String username, String fullName, String password, String phoneNumber,
                String email, String lastAccess, String lastPasswordChange, UserType type) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.lastAccess = lastAccess;
        this.lastPasswordChange = lastPasswordChange;
        this.type = type;
    }

    /**
     * Full user constructor
     *
     * @param username           The username that will be set.
     * @param fullName           The full name that will be set.
     * @param password           The password that that will be set.
     * @param phoneNumber        The phone number that will be set.
     * @param email              The email that will be set.
     * @param lastAccess         The last access date that will be set.
     * @param lastPasswordChange The last password change date that will be set.
     * @param type               The type of the user that will be set.
     * @param likedColors        The user liked colors that will be set.
     * @param likedMaterials     The user liked materials that will be set.
     * @param garments           All the garments of the user that will be set.
     */
    public User(String username, String fullName, String password, String phoneNumber,
                String email, String lastAccess, String lastPasswordChange, UserType type,
                Set<Color> likedColors, Set<Material> likedMaterials, Set<Garment> garments) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.lastAccess = lastAccess;
        this.lastPasswordChange = lastPasswordChange;
        this.type = type;
        this.likedColors = likedColors;
        this.likedMaterials = likedMaterials;
        this.garments = garments;
    }

    /**
     * Gets the users username.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the users username.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the users fullName.
     *
     * @return The users fullName.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Changes the users fullName to the received one
     *
     * @param fullName The fullName to set.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets the password of the current user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the users password to the received one.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the phoneNumber of the user.
     *
     * @return The users phoneNumber.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the users phoneNumber to the received one.
     *
     * @param phoneNumber The phoneNumber to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Changes the users email for the received one.
     *
     * @param email The email that will be saved.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the lastAccess date of the user.
     *
     * @return The date for the lastAccess of the user.
     */
    public Date getLastAccess() {
        Date lastAccessDate;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
            lastAccessDate = formatter.parse(lastPasswordChange);
        } catch (Exception e) {
            lastAccessDate = new Date();
        }
        return lastAccessDate;
    }

    /**
     * Changes the lastAccess date for the current user with the received one.
     *
     * @param lastAccess The lastAccess date that will be set
     */
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess.toString();
    }

    /**
     * Gets the users lastPasswordChange date.
     *
     * @return The lastPasswordChange date of the user.
     */
    public Date getLastPasswordChange() {
        Date lastPasswordChangeDate;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
            lastPasswordChangeDate = formatter.parse(lastPasswordChange);
        } catch (Exception e) {
            lastPasswordChangeDate = new Date();
        }
        return lastPasswordChangeDate;
    }

    /**
     * Changes the lastPasswordChange date for the user with the received one.
     *
     * @param lastPasswordChange The new date that will be set.
     */
    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange.toString();
    }

    /**
     * Gets the type of the user.
     *
     * @return The UserType of the user.
     */
    public UserType getType() {
        return type;
    }

    /**
     * Receives the type
     *
     * @param type
     */
    public void setType(UserType type) {
        this.type = type;
    }

    /**
     * Gets the Set of colors that the user likes.
     *
     * @return The Set with all the liked colors.
     */
    public Set<Color> getLikedColors() {
        return likedColors;
    }

    /**
     * Replaces all the colors that the user likes with the received ones.
     *
     * @param likedColors The colors Set that will be saved for the user.
     */
    public void setLikedColors(Set<Color> likedColors) {
        this.likedColors = likedColors;
    }

    /**
     * This method is created <u>to avoid duplicates</u> on likedColors.
     * Instead of <i>user.getLikedColors().add(color)</i>, use this
     * method. This will avoid errors on the server.
     *
     * @param color
     */
    public void addLikedColor(Color color) {

        if (!this.likedColors.contains(color)) {
            this.likedColors.add(color);
        }
    }

    /**
     * Checks if the user likes the specified color.
     * If it does, the color is removed from its liked colors.
     *
     * @param color The color that should be removed.
     */
    public void removeLikedColor(Color color) {

        if (this.likedColors.contains(color)) {
            this.likedColors.remove(color);
        }
    }

    /**
     * Gets the Set of materials that the user likes.
     *
     * @return The Set with all the liked materials.
     */
    public Set<Material> getLikedMaterials() {
        return likedMaterials;
    }

    /**
     * Replaces all the materials that the user likes with the received ones.
     *
     * @param likedMaterials The materials Set that will be saved for the user.
     */
    public void setLikedMaterials(Set<Material> likedMaterials) {
        this.likedMaterials = likedMaterials;
    }

    /**
     * This method is created <u>to avoid duplicates</u> on likedMaterials.
     * Instead of <i>user.getLikedMaterials().add(material)</i>, use this
     * method. This will avoid errors on the server.
     *
     * @param material The material that sould be added to the Set.
     */
    public void addLikedMaterial(Material material) {

        if (!this.likedMaterials.contains(material)) {
            this.likedMaterials.add(material);
        }
    }

    /**
     * Checks if the user likes the specified material.
     * If it does, the material is removed from its liked materials.
     *
     * @param material The material that should be removed.
     */
    public void removeLikedMaterial(Material material) {

        if (this.likedMaterials.contains(material)) {
            this.likedMaterials.remove(material);
        }
    }

    /**
     * Gets the garments that the user has saved.
     *
     * @return A Set with all the garments.
     */
    public Set<Garment> getGarments() {
        return garments;
    }

    /**
     * Replaces the users garments with the received set.
     *
     * @param garments The garments that will be saved for this user.
     */
    public void setGarments(Set<Garment> garments) {
        this.garments = garments;
    }

    /**
     * Creates the hash for the User.
     *
     * @return An int with all the attributes as hash
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.username);
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + Objects.hashCode(this.likedColors);
        hash = 29 * hash + Objects.hashCode(this.likedMaterials);
        hash = 29 * hash + Objects.hashCode(this.garments);
        return hash;
    }

    /**
     * Compares the received object to the current User.
     *
     * @param obj The object to compare.
     * @return True if they are equal. False if not.
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        User casted = (User) obj;

        if (!casted.getUsername().equals(this.username)) {
            return false;
        }

        if (!casted.getFullName().equals(this.fullName)) {
            return false;
        }

        if (!casted.getEmail().equals(this.email)) {
            return false;
        }

        if (!casted.getPhoneNumber().equals(this.phoneNumber)) {
            return false;
        }

        return true;
    }

    /**
     * Converts the data of the current User into a String.
     *
     * @return The user as a String.
     */
    @Override
    public String toString() {

        return this.username + ", " + this.fullName;
    }
}