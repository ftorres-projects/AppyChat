package com.devchallenges.controller;

import com.devchallenges.model.Comment;
import com.devchallenges.model.Post;
import com.devchallenges.service.CommentService;
import com.devchallenges.service.PostService;
import com.mongodb.client.result.UpdateResult;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("posts")
public class CommentRequest {

    PostService postService = new PostService();
    CommentService commentService = new CommentService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}/comments")
    public Response add(@PathParam("postId") String postId, Comment comment){

        // Post id must be passed in
        if(postId == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Post id should reference an existing post
        Post post = postService.find(postId);
        if(post == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Set created date for comment
        comment.setCreated(new Date());

        // Send the request to add the comment
        UpdateResult updateResult = commentService.add(postId, comment);

        // Verify the request was successful in modifying one post
        if(updateResult.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Change the modified date for the post
        UpdateResult updateResult1 = postService.updateModifiedDate(comment.getCreated(), postId);

        // Verify the request was successful in modifying one post
        if(updateResult1.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Change the comment count for the post
        Integer updatedCommentCount = 1;
        if(post.getStats() != null){
            if(post.getStats().getCommentCount() != null){
                updatedCommentCount = post.getStats().getCommentCount() + 1;
            }
        }
        UpdateResult updateResult2 = postService.updateCommentCount(postId, updatedCommentCount);

        // Verify the request was successful in modifying one post
        if(updateResult2.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Get the new modified post
        Post modifiedPost = postService.find(postId);

        return Response.ok().entity(modifiedPost).build();

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}/comments")
    public Response getAll(@PathParam("postId") String postId){

        List<Comment> comments = new ArrayList<>();

        // Post id must be passed in
        if(postId == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Post id should reference an existing post
        Post post = postService.find(postId);
        if(post == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Get comments list
        comments = post.getComments();
        if (comments == null ){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        GenericEntity<List<Comment>> list = new GenericEntity<List<Comment>>(comments){};

        return Response.ok(list).build();
    }


    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}/comments/{commentId}")
    public Response updateText(@PathParam("postId") String postId, @PathParam("commentId") String commentId, Comment updatedComment){

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

        // Send the request to update the comment
        UpdateResult updateResult = commentService.updateText(postId, commentId, updatedComment);

        // Verify the request was successful in modifying one post
        if(updateResult.getModifiedCount() != 1){
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


    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}/comments/{commentId}")
    public Response delete(@PathParam("postId") String postId, @PathParam("commentId") String commentId){

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

        // Send the request to delete the comment
        UpdateResult updateResult = commentService.delete(postId, commentId);

        // Verify the request was successful in modifying one post
        if(updateResult.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Change the modified date for the post
        UpdateResult updateResult1 = postService.updateModifiedDate(new Date(), postId);

        // Verify the request was successful in modifying one post
        if(updateResult1.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Change the comment count for the post
        Integer updatedCommentCount = 0;
        if(post.getStats() != null){
            if(post.getStats().getCommentCount() != null){
                updatedCommentCount = post.getStats().getCommentCount() - 1;
            }
        }
        UpdateResult updateResult2 = postService.updateCommentCount(postId, updatedCommentCount);

        // Verify the request was successful in modifying one post
        if(updateResult2.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Get the new modified post
        Post modifiedPost = postService.find(postId);

        return Response.ok().entity(modifiedPost).build();
    }

}
