package com.devchallenges.model;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class Comment {

    private ObjectId databaseId;
    private String id;
    private Date created;
    private User author;
    private String text;
    private String GIFLink;
    private ReactionCounts reactionCounts;
    private List<Reaction> reactions;
    private List<Reply> replies;

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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getGIFLink() {
        return GIFLink;
    }

    public void setGIFLink(String GIFLink) {
        this.GIFLink = GIFLink;
    }

    public ReactionCounts getReactionCounts() {
        return reactionCounts;
    }

    public void setReactionCounts(ReactionCounts reactionCounts) {
        this.reactionCounts = reactionCounts;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

}
