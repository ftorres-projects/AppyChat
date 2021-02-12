package com.devchallenges.controller;

import com.devchallenges.model.Post;
import com.devchallenges.model.Reaction;
import com.devchallenges.service.PostService;
import com.devchallenges.service.ReactionService;
import com.mongodb.client.result.UpdateResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("posts")
public class ReactionRequest {

    private ReactionService reactionService = new ReactionService();
    private PostService postService = new PostService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}/reactions")
    public Response reactToPost(@PathParam("postId") String postId, Reaction reaction){

        // Post id must be passed in
        if(postId == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Post id should reference an existing post
        Post post = postService.find(postId);
        if(post == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Set created date for reaction
        reaction.setCreated(new Date());

        // Send the request to modify the reaction
        UpdateResult updateResultReaction = reactionService.addToPost(postId, reaction);

        // Verify the request was successful in modifying one post
        if(updateResultReaction.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Set updated values for post reaction count
        Integer reactionCount = 0;
        String reactionType = reaction.getReaction();
        if(post.getStats() !=null){
            if(post.getStats().getReactionCounts() != null){
                reactionCount = post.getStats().getReactionCounts().countReaction(reactionType);
            }
        }
        reactionCount++;

        // Send request to update post reaction count
        UpdateResult updateResultPost = postService.updateReactionCount(postId, reaction.getReaction(), reactionCount);

        // Verify the request was successful in modifying one post
        if(updateResultPost.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Change the modified date for the post
        UpdateResult updateResult1 = postService.updateModifiedDate(new Date(), postId);

        // Verify the request was successful in modifying one post
        if(updateResult1.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Get the new modified post
        Post modifiedPost = postService.find(postId);

        return Response.ok().entity(modifiedPost).build();

    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}/comments/{commentId}/reactions")
    public Response reactToComment(@PathParam("postId") String postId, @PathParam("commentId") String commentId, Reaction reaction){

        // Post id and comment id must be passed in
        if(postId == null || commentId == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Post id should reference an existing post
        Post post = postService.find(postId);
        if(post == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Comment id should reference an existing comment
        if(!post.containsComment(commentId)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Set created date for reaction
        reaction.setCreated(new Date());

        // Send the request to react to a comment
        UpdateResult updateResult = reactionService.addToComment(postId, commentId, reaction);

        // Verify the request was successful in modifying one post
        if(updateResult.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Get the new modified post
        Post modifiedPost = postService.find(postId);

        return Response.ok().entity(modifiedPost).build();

    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}/comments/{commentId}/replies/{replyId}/reactions")
    public Response reactToReply(@PathParam("postId") String postId, @PathParam("commentId") String commentId, @PathParam("replyId") String replyId, Reaction reaction){

        // Post id, comment id and reply id must be passed in
        if(postId == null || commentId == null || replyId == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Post id should reference an existing post
        Post post = postService.find(postId);
        if(post == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Comment id should reference an existing comment
        if(!post.containsComment(commentId)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Reply id should reference an existing reply
        if(!post.containsReply(commentId, replyId)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Set created date for reaction
        reaction.setCreated(new Date());

        // Send the request to react to a comment
        UpdateResult updateResult = reactionService.addToReply(postId, commentId, replyId, reaction);

        // Verify the request was successful in modifying one post
        if(updateResult.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Get the new modified post
        Post modifiedPost = postService.find(postId);

        return Response.ok().entity(modifiedPost).build();

    }

}
