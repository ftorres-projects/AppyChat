package com.devchallenges.model;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class Reply {

    private ObjectId databaseId;
    private String id;
    private Date created;
    private User author;
    private String text;
    private String GIFLink;
    private ReactionCounts reactionCounts;
    private List<Reaction> reactions;

    public ObjectId getDatabaseId() {
        return databaseId;
    }

    public String getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public User getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public String getGIFLink() {
        return GIFLink;
    }

    public ReactionCounts getReactionCounts() {
        return reactionCounts;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setDatabaseId(ObjectId databaseId) {
        this.databaseId = databaseId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setGIFLink(String GIFLink) {
        this.GIFLink = GIFLink;
    }

    public void setReactionCounts(ReactionCounts reactionCounts) {
        this.reactionCounts = reactionCounts;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

}
