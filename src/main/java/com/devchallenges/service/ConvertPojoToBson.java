package com.devchallenges.service;

import com.devchallenges.model.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ConvertPojoToBson {

    public Document convertUser(User user){

        Document responseUser = new Document();

        if(user.getDatabaseId() == null && user.getId() != null){
            String id = user.getId();
            responseUser = new Document("_id", new ObjectId(id)).append("name", user.getName());
        } else {
            responseUser = new Document("_id", user.getDatabaseId()).append("name", user.getName());
        }

        if(user.getProfileImage() != null){
            responseUser.append("profileImage", user.getProfileImage());
        }

        return responseUser;

    }

    public List<Document> convertUsersList(List<User> users){

        List<Document> responseUsersList = new ArrayList<Document>();

        for(User user : users) {
            Document userDoc = convertUser(user);
            responseUsersList.add(userDoc);
        }

        return responseUsersList;

    }

    public Document convertReactionCounts(ReactionCounts reactionCounts){

        Document responseReactionCounts = new Document();

        if(reactionCounts.getAngry() != null) {
            responseReactionCounts.append("angry", reactionCounts.getAngry());
        }

        if(reactionCounts.getAwesome() != null) {
            responseReactionCounts.append("awesome", reactionCounts.getAwesome());
        }

        if(reactionCounts.getBoring() != null) {
            responseReactionCounts.append("boring", reactionCounts.getBoring());
        }

        if(reactionCounts.getCare() != null) {
            responseReactionCounts.append("care", reactionCounts.getCare());
        }

        if(reactionCounts.getCrazy() != null) {
            responseReactionCounts.append("crazy", reactionCounts.getCrazy());
        }

        if(reactionCounts.getFakeNews() != null) {
            responseReactionCounts.append("fakeNews", reactionCounts.getFakeNews());
        }

        if(reactionCounts.getHaha() != null) {
            responseReactionCounts.append("haha", reactionCounts.getHaha());
        }

        if(reactionCounts.getLame() != null) {
            responseReactionCounts.append("lame", reactionCounts.getLame());
        }

        if(reactionCounts.getLegal() != null) {
            responseReactionCounts.append("legal", reactionCounts.getLegal());
        }

        if(reactionCounts.getLike() != null) {
            responseReactionCounts.append("like", reactionCounts.getLike());
        }

        if(reactionCounts.getLove() != null) {
            responseReactionCounts.append("love", reactionCounts.getLove());
        }

        if(reactionCounts.getMeal() != null) {
            responseReactionCounts.append("meal", reactionCounts.getMeal());
        }

        if(reactionCounts.getSad() != null) {
            responseReactionCounts.append("sad", reactionCounts.getSad());
        }

        if(reactionCounts.getScary() != null) {
            responseReactionCounts.append("scary", reactionCounts.getScary());
        }

        if(reactionCounts.getWow() != null) {
            responseReactionCounts.append("wow", reactionCounts.getWow());
        }

        return responseReactionCounts;

    }

    public Document convertReaction(Reaction reaction){

        Document responseReaction = new Document("_id", reaction.getDatabaseId()).append("created", reaction.getCreated())
                .append("reaction", reaction.getReaction())
                .append("reactedBy", convertUser(reaction.getReactedBy()));

        return responseReaction;

    }

    public List<Document> convertReactionsList(List<Reaction> reactions){

        List<Document> responseReactionsList = new ArrayList<Document>();

        for(Reaction reaction : reactions) {
            Document reactionDoc = convertReaction(reaction);
            responseReactionsList.add(reactionDoc);
        }

        return responseReactionsList;

    }

    public Document convertReply(Reply reply){

        Document responseReply = new Document("_id", reply.getDatabaseId()).append("created", reply.getCreated())
                .append("author", convertUser(reply.getAuthor()));

        if(reply.getText() != null){
            responseReply.append("text", reply.getText());
        }

        if(reply.getGIFLink() != null){
            responseReply.append("GIFLink", reply.getGIFLink());
        }

        if(reply.getReactionCounts() != null){
            responseReply.append("reactionCounts", convertReactionCounts(reply.getReactionCounts()));
        }

        if(reply.getReactions() != null){
            responseReply.append("reactions", convertReactionsList(reply.getReactions()));
        }

        return responseReply;

    }

    public List<Document> convertRepliesList(List<Reply> replies){

        List<Document> responseRepliesList = new ArrayList<Document>();

        for(Reply reply : replies) {
            Document replyDoc = convertReply(reply);
            responseRepliesList.add(replyDoc);
        }

        return responseRepliesList;

    }

    public List<Document> convertStringList(List<String> stringList, String key){

        List<Document> responseStringList = new ArrayList<Document>();

        for(String listElement : stringList) {
            Document listElementDoc = new Document(key, listElement);
            responseStringList.add(listElementDoc);
        }

        return responseStringList;

    }

    public Document convertComment(Comment comment){

        Document responseComment = new Document("_id", comment.getDatabaseId()).append("created", comment.getCreated())
                .append("author", convertUser(comment.getAuthor()));

        if(comment.getText() != null){
            responseComment.append("text", comment.getText());
        }

        if(comment.getGIFLink() != null){
            responseComment.append("GIFLink", comment.getGIFLink());
        }

        if(comment.getReactionCounts() != null){
            responseComment.append("reactionCounts", convertReactionCounts(comment.getReactionCounts()));
        }

        if(comment.getReplies() != null){
            responseComment.append("replies", convertRepliesList(comment.getReplies()));
        }

        if(comment.getReactions() != null){
            responseComment.append("reactions", convertReactionsList(comment.getReactions()));
        }

        return responseComment;

    }

    public List<Document> convertCommentsList(List<Comment> comments){

        List<Document> responseCommentsList = new ArrayList<Document>();

        for(Comment comment : comments) {
            Document commentDoc = convertComment(comment);
            responseCommentsList.add(commentDoc);
        }

        return responseCommentsList;

    }

    public Document convertPostStats(PostStats postStats){

        Document responsePostStats = new Document();

        if(postStats.getShares() != null) {
            responsePostStats.append("shares", postStats.getShares());
        }

        if(postStats.getReactionCounts() != null) {
            responsePostStats.append("reactionCounts", convertReactionCounts(postStats.getReactionCounts()));
        }

        if(postStats.getCommentCount() != null) {
            responsePostStats.append("commentCount", postStats.getCommentCount());
        }

        return responsePostStats;

    }


    public Document convertPost(Post post){

        Document responsePost = new Document("_id", post.getDatabaseId()).append("author", convertUser(post.getAuthor()))
                .append("created", post.getCreated())
                .append("modified", post.getModified())
                .append("text", post.getText());

        if(post.getMultimedia() != null){
            responsePost.append("multimedia", convertStringList(post.getMultimedia(), "multimedia"));
        }

        if(post.getDistribution() != null){
            responsePost.append("distribution", convertUsersList(post.getDistribution()));
        }

        if(post.getStats() != null){
            responsePost.append("stats", convertPostStats(post.getStats()));
        }

        if(post.getComments() != null){
            responsePost.append("comments", convertCommentsList(post.getComments()));
        }

        if(post.getReactions() != null){
            responsePost.append("reactions", convertReactionsList(post.getReactions()));
        }

        return responsePost;

    }

    public List<Document> convertPostsList(List<Post> posts){

        List<Document> responsePostsList = new ArrayList<Document>();

        for(Post post : posts) {
            Document postDoc = convertPost(post);
            responsePostsList.add(postDoc);
        }

        return responsePostsList;

    }

}
