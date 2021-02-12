package com.devchallenges.model;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class Post {

    private ObjectId databaseId;
    private String id;
    private User author;
    private Date created;
    private Date modified;
    private String text;
    private List<String> multimedia;
    private List<User> distribution;
    private PostStats stats;
    private List<Comment> comments;
    private List<Reaction> reactions;

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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<String> multimedia) {
        this.multimedia = multimedia;
    }

    public List<User> getDistribution() {
        return distribution;
    }

    public void setDistribution(List<User> distribution) {
        this.distribution = distribution;
    }

    public PostStats getStats() {
        return stats;
    }

    public void setStats(PostStats stats) {
        this.stats = stats;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public boolean containsComment(String commentId){
        boolean containsComment = false;

        for(Comment comment : comments){
            if (comment.getId().equals(commentId)) {
                containsComment = true;
                break;
            }
        }

        return containsComment;
    }

    public boolean containsReply(String commentId, String replyId){
        boolean containsReply = false;

        for(Comment comment : comments){
            if(comment.getId().equals(commentId)){
                for(Reply reply : comment.getReplies()){
                    if(reply.getId().equals(replyId)){
                        containsReply = true;
                        break;
                    }
                }
            }
        }

        return containsReply;
    }

}
