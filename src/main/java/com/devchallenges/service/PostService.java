package com.devchallenges.service;

import com.devchallenges.model.Paging;
import com.devchallenges.model.Post;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.set;

public class PostService {

    ConvertBsonToPojo convertBsonToPOJO = new ConvertBsonToPojo();
    ConvertPojoToBson convertPojoToBson = new ConvertPojoToBson();

    private String connectionString = "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false";
    private String databaseName = "appyChat";
    private String collectionName = "postsComplete";

    public Post create(Post post){

        ObjectId newId = new ObjectId();
        post.setDatabaseId(newId);
        post.setId(newId.toString());

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){

            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Create new post as a BSON document
            Document postDoc = convertPojoToBson.convertPost(post);

            collection.insertOne(postDoc);

        }

        return post;

    }

    public Post find(String postId){

        Post post = new Post();

        try(MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);


            // Build/apply filter and retrieve first result
            Document postDoc = collection.find(eq("_id", new ObjectId(postId))).first();

            // Convert result from Bson document to post object
            if (postDoc != null){
                post = convertBsonToPOJO.convertPost(postDoc);
            }

        }

        return post;

    }

    public List<Post> findAll(Paging paging) {

        List<Post> allPosts = new ArrayList<>();

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Build filters
            Bson userIsAuthorFilter = eq("author._id", new ObjectId(paging.getCurrentUserId()));
            Bson userInDistributionFilter = eq("distribution._id", new ObjectId(paging.getCurrentUserId()));
            Bson filter = or(userIsAuthorFilter, userInDistributionFilter);

            // Build projection
            Bson projectionOperation = fields(include("created","modified","text","author.name","author.profileImage","stats"),slice("comments",1));

            // Build order by
            Bson orderBy = new Document("modified", -1);

            // Build/apply filter and retrieve results
            List<Document> posts = collection.find(filter).projection(projectionOperation).skip(paging.getStartingRow() - 1).limit(paging.getNumberOfRows()).sort(orderBy).into(new ArrayList<Document>());

            allPosts = convertBsonToPOJO.convertPostsList(posts);

        }

        return allPosts;

    }

    public UpdateResult updateText(Post post, String postId){


        UpdateResult updateResult = null;

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Build the filter and update operation
            Bson filter = eq("_id", new ObjectId(postId));
            Bson updateOperation = set("text", post.getText());
            Bson updateModifiedDate = set("modified", post.getModified());

            // Perform update
            updateResult = collection.updateOne(filter, updateOperation);
            updateResult = collection.updateOne(filter,updateModifiedDate);

        }

        return updateResult;

    }


    public UpdateResult updateModifiedDate(Date modifiedDate, String postId){

        UpdateResult updateResult = null;

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Build the filter and update operation
            Bson filter = eq("_id", new ObjectId(postId));
            Bson updateOperation = set("modified", modifiedDate);

            // Perform update
            updateResult = collection.updateOne(filter,updateOperation);

        }

        return updateResult;

    }


    /**
     * Delete the post
     *
     * @param postId   the id for the post to be delete
     * @return   the database delete result
     */
    public DeleteResult delete(String postId){

        DeleteResult deleteResult = null;

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Build the filter
            Bson filter = eq("_id", new ObjectId(postId));

            // Perform delete
            deleteResult = collection.deleteOne(filter);

        }

        return deleteResult;

    }


    /**
     * Update the comment count for the post
     *
     * @param postId   the id for the post to be updated
     * @param updatedNumber   new number of comments for this post
     * @return   the database update result
     */
    public UpdateResult updateCommentCount(String postId, Integer updatedNumber){

        UpdateResult updateResult = null;

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Build the filter
            Bson filter = eq("_id", new ObjectId(postId));

            // Build the update operation
            Bson updateOperation = set("stats.commentCount", updatedNumber);

            // Perform update
            updateResult = collection.updateOne(filter, updateOperation);

        }

        return updateResult;

    }


    /**
     * Update the reaction count for the post
     *
     * @param postId   the id for the post to be updated
     * @param reaction   the reaction type to be updated
     * @param reactionCount   what is the new count for this reaction
     * @return   the database update result
     */
    public UpdateResult updateReactionCount(String postId, String reaction, Integer reactionCount){


        UpdateResult updateResult = null;

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Build the filter
            Bson filter = eq("_id", new ObjectId(postId));

            // Build the update operation
            Bson updateOperation = null;
            switch (reaction) {
                case "ANGRY":
                    updateOperation = set("stats.reactionCounts.angry", reactionCount);
                    break;
                case "AWESOME":
                    updateOperation = set("stats.reactionCounts.awesome", reactionCount);
                    break;
                case "BORING":
                    updateOperation = set("stats.reactionCounts.boring", reactionCount);
                    break;
                case "CARE":
                    updateOperation = set("stats.reactionCounts.care", reactionCount);
                    break;
                case "CRAZY":
                    updateOperation = set("stats.reactionCounts.crazy", reactionCount);
                    break;
                case "FAKENEWS":
                    updateOperation = set("stats.reactionCounts.fakeNews", reactionCount);
                    break;
                case "HAHA":
                    updateOperation = set("stats.reactionCounts.haha", reactionCount);
                    break;
                case "LAME":
                    updateOperation = set("stats.reactionCounts.lame", reactionCount);
                    break;
                case "LEGAL":
                    updateOperation = set("stats.reactionCounts.legal", reactionCount);
                    break;
                case "LIKE":
                    updateOperation = set("stats.reactionCounts.like", reactionCount);
                    break;
                case "LOVE":
                    updateOperation = set("stats.reactionCounts.love", reactionCount);
                    break;
                case "MEAL":
                    updateOperation = set("stats.reactionCounts.meal", reactionCount);
                    break;
                case "SAD":
                    updateOperation = set("stats.reactionCounts.sad", reactionCount);
                    break;
                case "SCARY":
                    updateOperation = set("stats.reactionCounts.scary", reactionCount);
                    break;
                case "WOW":
                    updateOperation = set("stats.reactionCounts.wow", reactionCount);
                    break;
                default:
                    updateOperation = null;
                    break;
            }
            Bson updateModifiedDate = set("modified", new Date());

            // Perform update
            if(updateOperation != null){
                updateResult = collection.updateOne(filter, updateOperation);
                updateResult = collection.updateOne(filter,updateModifiedDate);
            }

        }

        return updateResult;

    }

}
