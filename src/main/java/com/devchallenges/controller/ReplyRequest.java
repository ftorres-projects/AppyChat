package com.devchallenges.controller;

import com.devchallenges.model.Post;
import com.devchallenges.model.Reply;
import com.devchallenges.service.PostService;
import com.devchallenges.service.ReplyService;
import com.mongodb.client.result.UpdateResult;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("posts")
public class ReplyRequest {

    PostService postService = new PostService();
    ReplyService replyService = new ReplyService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}/comments/{commentId}/replies")
    public Response add(@PathParam("postId") String postId, @PathParam("commentId") String commentId, Reply reply){

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

        // Set created date for reply
        reply.setCreated(new Date());

        // Send the request to add the reply
        UpdateResult updateResult = replyService.add(postId, commentId, reply);

        // Verify the request was successful in modifying one post
        if(updateResult.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Get the new modified post
        Post modifiedPost = postService.find(postId);

        return Response.ok().entity(modifiedPost).build();

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}/comments/{commentId}/replies")
    public Response getAll(@PathParam("postId") String postId, @PathParam("commentId") String commentId){

        List<Reply> replies = new ArrayList<>();

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

        // Get replies list
        replies = replyService.findAll(postId, commentId);
        if(replies == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        GenericEntity<List<Reply>> list = new GenericEntity<List<Reply>>(replies){};

        return Response.ok(list).build();

    }


    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}/comments/{commentId}/replies/{replyId}")
    public Response updateText(@PathParam("postId") String postId, @PathParam("commentId") String commentId, @PathParam("replyId") String replyId, Reply partialReply){

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

        // Send the request to modify the reply
        UpdateResult updateResult = replyService.updateText(postId, commentId, replyId, partialReply);

        // Verify the request was successful in modifying one post
        if(updateResult.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Get the new modified post
        Post modifiedPost = postService.find(postId);

        return Response.ok().entity(modifiedPost).build();

    }


    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}/comments/{commentId}/replies/{replyId}")
    public Response delete(@PathParam("postId") String postId, @PathParam("commentId") String commentId, @PathParam("replyId") String replyId){

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

        // Send the request to delete the reply
        UpdateResult updateResult = replyService.delete(postId, commentId, replyId);

        // Verify the request was successful in modifying one post
        if(updateResult.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Get the new modified post
        Post modifiedPost = postService.find(postId);

        return Response.ok().entity(modifiedPost).build();

    }

}
