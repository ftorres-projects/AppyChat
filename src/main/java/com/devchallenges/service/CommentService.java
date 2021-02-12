package com.devchallenges.service;

import com.devchallenges.model.Comment;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.pull;
import static com.mongodb.client.model.Updates.set;

public class CommentService {

    ConvertPojoToBson convertPojoToBson = new ConvertPojoToBson();
    ConvertBsonToPojo convertBsonToPojo = new ConvertBsonToPojo();

    private String connectionString = "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false";

    public UpdateResult add(String postId, Comment comment){

        UpdateResult updateResult = null;

        // Create new Object id for comment
        ObjectId newId = new ObjectId();
        comment.setDatabaseId(newId);
        comment.setId(newId.toString());

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("appyChat");
            MongoCollection<Document> collection = database.getCollection("postsComplete");

            // Convert comment to a Bson document
            Document commentDoc = convertPojoToBson.convertComment(comment);

            // Build the filter
            Bson filter = eq("_id", new ObjectId(postId));

            // Add comment to post
            updateResult = collection.updateOne(filter, Updates.addToSet("comments", commentDoc));

        }

        return updateResult;

    }


    public List<Comment> findAll(String postId) {

        List<Comment> allCommentObjects = new ArrayList<>();

        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("appyChat");
            MongoCollection<Document> collection = database.getCollection("postsComplete");

            // Build filter for post
            Document post = collection.find(eq("_id", new ObjectId(postId))).first();
            if (post != null){

                // Get all comments for post
                List<Document> comments = post.getList("comments", Document.class);

                // Convert comments list to comment object format
                allCommentObjects = convertBsonToPojo.convertCommentsList(comments);
            }

        }

        return allCommentObjects;

    }


    public UpdateResult updateText(String postId, String commentId, Comment comment) {

        UpdateResult updateResult;

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("appyChat");
            MongoCollection<Document> collection = database.getCollection("postsComplete");

            // Build filters
            Bson postFilter = eq("_id", new ObjectId(postId));
            Bson commentFilter = eq("comments._id", new ObjectId(commentId));
            Bson filter = and(postFilter, commentFilter);

            // Build the update operation
            Bson updateOperation = set("comments.$.text", comment.getText());

            // Perform the comment update
            updateResult = collection.updateOne(filter, updateOperation);

        }

        return updateResult;
    }


    public UpdateResult delete(String postId, String commentId) {

        UpdateResult updateResult;

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("appyChat");
            MongoCollection<Document> collection = database.getCollection("postsComplete");

            // Build filters
            Bson postFilter = eq("_id", new ObjectId(postId));
            Bson commentFilter = eq("_id", new ObjectId(commentId));

            // Build update operation
            Bson updateOperation = pull("comments", commentFilter);

            // Perform the comment delete
            updateResult = collection.updateOne(postFilter, updateOperation);

        }

        return updateResult;

    }

}
