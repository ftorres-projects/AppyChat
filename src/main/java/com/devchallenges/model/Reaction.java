package com.devchallenges.model;

import org.bson.types.ObjectId;

import java.util.Date;

public class Reaction {

    private ObjectId databaseId;
    private String id;
    private Date created;
    private User reactedBy;
    private String reaction;

    public ObjectId getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(ObjectId databaseId) {
        this.databaseId = databaseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getReactedBy() {
        return reactedBy;
    }

    public void setReactedBy(User reactedBy) {
        this.reactedBy = reactedBy;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

}
