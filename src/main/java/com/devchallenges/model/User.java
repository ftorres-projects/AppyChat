package com.devchallenges.model;

import org.bson.types.ObjectId;

public class User {

    private ObjectId databaseId;
    private String id;
    private String name;
    private String profileImage;

    public ObjectId getDatabaseId() {
        return databaseId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setDatabaseId(ObjectId databaseId) {
        this.databaseId = databaseId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
