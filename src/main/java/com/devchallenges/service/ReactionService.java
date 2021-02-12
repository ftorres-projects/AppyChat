package com.devchallenges.service;

import com.devchallenges.model.Reaction;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.addToSet;

public class ReactionService {

    ConvertPojoToBson convertPojoToBson = new ConvertPojoToBson();

    private String connectionString = "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false";

    public UpdateResult addToPost(String postId, Reaction reaction) {

        UpdateResult updateResult;

        // Create new Object id for reaction
        ObjectId newId = new ObjectId();
        reaction.setDatabaseId(newId);
        reaction.setId(newId.toString());

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("appyChat");
            MongoCollection<Document> collection = database.getCollection("postsComplete");

            // Convert reaction to Bson document format
            Document document = convertPojoToBson.convertReaction(reaction);

            // Build filter
            Bson filter = eq("_id", new ObjectId(postId));

            // Add the new reaction to the post list of reactions
            updateResult = collection.updateOne(filter, Updates.addToSet("reactions", document));

        }

        return updateResult;

    }


    public UpdateResult addToComment(String postId, String commentId, Reaction reaction) {

        UpdateResult updateResult;

        // Create new Object id for reaction
        ObjectId newId = new ObjectId();
        reaction.setDatabaseId(newId);
        reaction.setId(newId.toString());

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("appyChat");
            MongoCollection<Document> collection = database.getCollection("postsComplete");

            // Convert reaction to Bson document format
            Document document = convertPojoToBson.convertReaction(reaction);

            // Build filters
            Bson postFilter = eq("_id", new ObjectId(postId));
            Bson commentFilter = eq("comments._id", new ObjectId(commentId));
            Bson filter = and(postFilter, commentFilter);

            // Add reaction to comment
            updateResult = collection.updateOne(filter, Updates.addToSet("comments.$.reactions", document));

        }

        return updateResult;

    }


    public UpdateResult addToReply(String postId, String commentId, String replyId, Reaction reaction) {

        UpdateResult updateResult;

        // Create new Object id for reaction
        ObjectId newId = new ObjectId();
        reaction.setDatabaseId(newId);
        reaction.setId(newId.toString());

        // Connect to Appy chat database and get the posts collection
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("appyChat");
            MongoCollection<Document> collection = database.getCollection("postsComplete");

            // Convert reaction to Bson document format
            Document document = convertPojoToBson.convertReaction(reaction);

            // Build filter
            Bson postFilter = eq("_id", new ObjectId(postId));

            // Set update operation options
            UpdateOptions options = new UpdateOptions().arrayFilters(Arrays.asList( eq("comment._id", new ObjectId(commentId)),
                    eq("reply._id", new ObjectId(replyId))));

            // Build update operation
            Bson updateOperation = addToSet("comments.$[comment].replies.$[reply].reactions", document);

            // Add reaction to reply
            updateResult = collection.updateOne(postFilter, updateOperation, options);

        }

        return updateResult;
    }

}
