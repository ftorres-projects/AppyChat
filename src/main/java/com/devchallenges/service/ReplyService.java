package com.devchallenges.service;

import com.devchallenges.model.Reply;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;

public class ReplyService {

    ConvertPojoToBson convertPojoToBson = new ConvertPojoToBson();
    ConvertBsonToPojo convertBsonToPojo = new ConvertBsonToPojo();

    private String connectionString = "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false";

    public UpdateResult add(String postId, String commentId, Reply reply) {

        UpdateResult updateResult;

        // Create new Object id for reply
        ObjectId newId = new ObjectId();
        reply.setDatabaseId(newId);
        reply.setId(newId.toString());

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("appyChat");
            MongoCollection<Document> collection = database.getCollection("postsComplete");

            // Convert reply to a Bson document
            Document replyDoc = convertPojoToBson.convertReply(reply);

            // Build filters
            Bson postFilter = eq("_id", new ObjectId(postId));
            Bson commentFilter = eq("comments._id", new ObjectId(commentId));

            // Build update operation
            Bson updateOperation = addToSet("comments.$.replies", replyDoc);

            // Perform the add operation
            updateResult = collection.updateOne(commentFilter, updateOperation);

        }

        return updateResult;

    }


    public List<Reply> findAll(String postId, String commentId) {

        List<Reply> allReplyObjects = new ArrayList<>();

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("appyChat");
            MongoCollection<Document> collection = database.getCollection("postsComplete");

            // Build filters
            Bson postFilter = eq("_id", new ObjectId(postId));
            Bson commentFilter = eq("comments._id", new ObjectId(commentId));

            // Build aggregation pipeline
            List<Bson> pipeline = Arrays.asList( match(postFilter),
                    unwind("$comments"),
                    match(commentFilter),
                    project(fields(include("comments.replies"), excludeId())),
                    unwind("$comments.replies")  );

            // Get the reply documents
            List<Document> replies = new ArrayList<>();
            collection.aggregate(pipeline).into(replies);

            if (replies != null){
                for(Document replyDoc : replies){

                    // Take the "replies" element from the "comments" wrapper
                    Document repliesWrapper = (Document) replyDoc.get("comments");
                    // Take the reply document from the "replies" wrapper
                    Document reply = (Document) repliesWrapper.get("replies");

                    // Convert reply from Bson to Reply object
                    Reply replyResponse = convertBsonToPojo.convertReply(reply);

                    allReplyObjects.add(replyResponse);
                }

            }

        }

        return allReplyObjects;
    }


    public UpdateResult updateText(String postId, String commentId, String replyId, Reply reply) {

        UpdateResult updateResult;

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("appyChat");
            MongoCollection<Document> collection = database.getCollection("postsComplete");

            // Build filter
            Bson postFilter = eq("_id", new ObjectId(postId));

            // Set the update operation options
            UpdateOptions options = new UpdateOptions().arrayFilters(Arrays.asList( eq("comment._id", new ObjectId(commentId)),
                    eq("reply._id", new ObjectId(replyId))));

            // Build the update operation
            Bson updateOperation = set("comments.$[comment].replies.$[reply].text", reply.getText());

            // Perform the reply update
            updateResult = collection.updateOne(postFilter, updateOperation, options);

        }

        return updateResult;

    }


    public UpdateResult delete(String postId, String commentId, String replyId) {

        UpdateResult updateResult;

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("appyChat");
            MongoCollection<Document> collection = database.getCollection("postsComplete");

            // Build filter
            Bson postFilter = eq("_id", new ObjectId(postId));

            // Set update options
            UpdateOptions options = new UpdateOptions().arrayFilters(Arrays.asList( eq("comment._id", new ObjectId(commentId))));

            // Build update operation
            Bson updateOperation = pull("comments.$[comment].replies", eq("_id", new ObjectId(replyId)));

            // Perform the reply delete
            updateResult = collection.updateOne(postFilter, updateOperation, options);

        }

        return updateResult;

    }

}
