package com.iasmar.rome.data.modules;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import com.iasmar.rome.util.GeneralUtils;

import static com.iasmar.rome.util.GeneralUtils.isNullOrEmpty;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Immutable model class for a engineer.
 * <p>
 * The main purpose of this Module is to hold the engineer data.
 *
 * @author Asmar
 * @version 1
 * @see BaseModule
 * @since 0.1.0
 */
public class Engineer implements BaseModule<String> {

    //The unique id of the engineer.
    @SerializedName("id")
    private final String engineerId;

    //The name of the engineer.
    @SerializedName("name")
    private final String name;

    //The profile picture of the engineer.
    @SerializedName("profilePic")
    private final String profilePic;


    /**
     * Use this constructor to create a new engineer.
     *
     * @param engineerId     The unique id of the engineer.
     * @param name          The name of the engineer.
     * @param profilePic The profile picture of the engineer.
     */
    public Engineer(@NonNull String engineerId, @NonNull String name,  @NonNull String profilePic ) {
        this.engineerId = engineerId;
        this.name = name;
        this.profilePic = profilePic;
    }

    /**
     * Get the unique id of the engineer.
     *
     * @return The unique id of the engineer.
     */
    @Override
    public String getId() {
        return engineerId;
    }

    /**
     * Get the name of the engineer.
     *
     * @return The name of the engineer.
     */
    public String getName() {
        return name;
    }


    /**
     * Get the profile picture of the engineer.
     *
     * @return The profile picture  of the engineer.
     */
    public String getProfilePic() {
        return profilePic;
    }


    /**
     * Get hash of the engineer.
     *
     * @return The hash of the engineer.
     */
    @Override
    public String getHash() {
        return GeneralUtils.getHash(toString());
    }

    /**
     * Is the passed object equals to this object.
     *
     * @param obj The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        String obj1Hash = ((BaseModule) obj).getHash();
        String obj2Hash = this.getHash();

        return obj1Hash != null && obj1Hash.equals(obj2Hash);

    }

    /**
     * Is this object empty (has no data).
     *
     * @return {@code true} if this object is empty
     * {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return isNullOrEmpty(getId())
                && isNullOrEmpty(getName());
    }

    /**
     * Get The default sort by.
     *
     * @return The default sort by.
     */
    @Override
    public String getSortBy() {
        return getName();
    }

    /**
     * Get the string representation of the object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "engineerId: " + getId() +
                "name: " + getName() +
                "profilePic: " + getProfilePic();
    }

}
