package com.devchallenges.service;

import com.devchallenges.model.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ConvertBsonToPojo {

    public Reply convertReply(Document replyInBson){

        Reply responseReply = new Reply();

        responseReply.setId(replyInBson.get("_id").toString());

        responseReply.setText(replyInBson.getString("text"));
        responseReply.setCreated(replyInBson.getDate("created"));
        responseReply.setGIFLink(replyInBson.getString("GIFLink"));

        Document authorDoc = (Document) replyInBson.get("author");
        if(authorDoc != null){
            User author = convertUser(authorDoc);
            responseReply.setAuthor(author);
        }

        Document reactionsCountsDoc = (Document) replyInBson.get("reactionsCounts");
        if(reactionsCountsDoc != null){
            ReactionCounts reactionCounts = convertReactionCounts(reactionsCountsDoc);
            responseReply.setReactionCounts(reactionCounts);
        }

        List<Document> reactionsDoc = replyInBson.getList("reactions", Document.class);
        if(reactionsDoc != null){
            List<Reaction> reactions = convertReactionsList(reactionsDoc);
            responseReply.setReactions(reactions);
        }

        return responseReply;

    }

    public List<Reply> convertRepliesList(List<Document> repliesListInBson){

        List<Reply> responseRepliesList = new ArrayList<Reply>();

        for(Document replyDoc : repliesListInBson) {
            Reply reply = convertReply(replyDoc);
            responseRepliesList.add(reply);
        }

        return responseRepliesList;

    }

    public User convertUser(Document userInBson){

        User responseUser = new User();

        if(userInBson.get("_id") != null){
            responseUser.setId(userInBson.get("_id").toString());
        }

        if(userInBson.get("name") != null){
            responseUser.setName(userInBson.getString("name"));
        }

        if(userInBson.get("profileImage") != null){
            responseUser.setProfileImage(userInBson.getString("profileImage"));
        }

        return responseUser;

    }

    public List<User> convertUsersList(List<Document> usersListInBson){

        List<User> responseUsersList = new ArrayList<User>();

        for(Document userDoc : usersListInBson) {
            User user = convertUser(userDoc);
            responseUsersList.add(user);
        }

        return responseUsersList;

    }

    public ReactionCounts convertReactionCounts(Document reactionCountsInBson){

        ReactionCounts responseReactionCounts = new ReactionCounts();

        responseReactionCounts.setAngry(reactionCountsInBson.getInteger("angry"));
        responseReactionCounts.setAwesome(reactionCountsInBson.getInteger("awesome"));
        responseReactionCounts.setBoring(reactionCountsInBson.getInteger("boring"));
        responseReactionCounts.setCare(reactionCountsInBson.getInteger("care"));
        responseReactionCounts.setCrazy(reactionCountsInBson.getInteger("crazy"));
        responseReactionCounts.setFakeNews(reactionCountsInBson.getInteger("fakeNews"));
        responseReactionCounts.setHaha(reactionCountsInBson.getInteger("haha"));
        responseReactionCounts.setLame(reactionCountsInBson.getInteger("lame"));
        responseReactionCounts.setLegal(reactionCountsInBson.getInteger("legal"));
        responseReactionCounts.setLike(reactionCountsInBson.getInteger("like"));
        responseReactionCounts.setLame(reactionCountsInBson.getInteger("love"));
        responseReactionCounts.setMeal(reactionCountsInBson.getInteger("meal"));
        responseReactionCounts.setSad(reactionCountsInBson.getInteger("sad"));
        responseReactionCounts.setScary(reactionCountsInBson.getInteger("scary"));
        responseReactionCounts.setWow(reactionCountsInBson.getInteger("wow"));

        return responseReactionCounts;

    }

    public Reaction convertReaction(Document reactionInBson){

        Reaction responseReaction = new Reaction();

        responseReaction.setId(reactionInBson.get("_id").toString());
        responseReaction.setCreated(reactionInBson.getDate("created"));

        Document reactedByDoc = (Document) reactionInBson.get("reactedBy");
        if(reactedByDoc != null){
            User reactedBy = convertUser(reactedByDoc);
            responseReaction.setReactedBy(reactedBy);
        }

        responseReaction.setReaction(reactionInBson.getString("reaction"));

        return responseReaction;

    }

    public List<Reaction> convertReactionsList(List<Document> reactionsListInBson){

        List<Reaction> responseReactionsList = new ArrayList<Reaction>();

        for(Document reactionDoc : reactionsListInBson) {
            Reaction reaction = convertReaction(reactionDoc);
            responseReactionsList.add(reaction);
        }

        return responseReactionsList;

    }


    public Comment convertComment(Document commentInBson){

        Comment responseComment = new Comment();

        if(commentInBson.get("_id") != null){
            responseComment.setId(commentInBson.get("_id").toString());
        }

        responseComment.setCreated(commentInBson.getDate("created"));

        Document authorDoc = (Document) commentInBson.get("author");
        if(authorDoc != null){
            User author = convertUser(authorDoc);
            responseComment.setAuthor(author);
        }

        responseComment.setText(commentInBson.getString("text"));
        responseComment.setGIFLink(commentInBson.getString("GIFLink"));

        List<Document> reactionsDoc = commentInBson.getList("reactions", Document.class);
        if(reactionsDoc != null){
            List<Reaction> reactions = convertReactionsList(reactionsDoc);
            responseComment.setReactions(reactions);
        }

        List<Document> repliesDoc = commentInBson.getList("replies", Document.class);
        if(repliesDoc != null){
            List<Reply> replies = convertRepliesList(repliesDoc);
            responseComment.setReplies(replies);
        }

        return responseComment;

    }

    public List<Comment> convertCommentsList(List<Document> commentsListInBson){

        List<Comment> responseCommentsList = new ArrayList<Comment>();

        for(Document commentDoc : commentsListInBson) {
            Comment comment = convertComment(commentDoc);
            responseCommentsList.add(comment);
        }

        return responseCommentsList;

    }


    public PostStats convertPostStats(Document postStatsInBson){

        PostStats responsePostStats = new PostStats();

        responsePostStats.setShares(postStatsInBson.getInteger("shares"));

        Document reactionCountsDoc = (Document) postStatsInBson.get("reactionCounts");
        if(reactionCountsDoc != null){
            ReactionCounts reactionCounts = convertReactionCounts(reactionCountsDoc);
            responsePostStats.setReactionCounts(reactionCounts);
        }

        responsePostStats.setCommentCount(postStatsInBson.getInteger("commentCount"));

        return responsePostStats;

    }


    public Post convertPost(Document postInBson){

        Post responsePost = new Post();

        if(postInBson.get("_id") != null){
            responsePost.setId(postInBson.get("_id").toString());
        }
        responsePost.setCreated(postInBson.getDate("created"));

        Document authorDoc = (Document) postInBson.get("author");
        if(authorDoc != null){
            User author = convertUser(authorDoc);
            responsePost.setAuthor(author);
        }

        responsePost.setModified(postInBson.getDate("modified"));
        responsePost.setText(postInBson.getString("text"));

        List<Document> multimediaDoc = postInBson.getList("multimedia", Document.class);
        if(multimediaDoc != null){
            List<String> multimedia = convertStringList(multimediaDoc, "multimedia");
            responsePost.setMultimedia(multimedia);
        }

        List<Document> distributionDoc = postInBson.getList("distribution", Document.class);
        if(distributionDoc != null){
            List<User> distribution = convertUsersList(distributionDoc);
            responsePost.setDistribution(distribution);
        }

        Document statsDoc = (Document) postInBson.get("stats");
        if(statsDoc != null){
            PostStats stats = convertPostStats(statsDoc);
            responsePost.setStats(stats);
        }

        List<Document> commentsDoc = postInBson.getList("comments", Document.class);
        if(commentsDoc != null){
            List<Comment> comments = convertCommentsList(commentsDoc);
            responsePost.setComments(comments);
        }

        List<Document> reactionsDoc = postInBson.getList("reactions", Document.class);
        if(reactionsDoc != null){
            List<Reaction> reactions = convertReactionsList(reactionsDoc);
            responsePost.setReactions(reactions);
        }

        return responsePost;

    }

    public List<Post> convertPostsList(List<Document> postsListInBson){

        List<Post> responsePostsList = new ArrayList<Post>();

        for(Document postDoc : postsListInBson) {
            Post post = convertPost(postDoc);
            responsePostsList.add(post);
        }

        return responsePostsList;

    }

    public List<String> convertStringList(List<Document> stringListInBson, String key){

        List<String> responseStringList = new ArrayList<String>();

        for(Document listDoc : stringListInBson) {
            String listElement = listDoc.getString(key);
            responseStringList.add(listElement);
        }

        return responseStringList;

    }

}
