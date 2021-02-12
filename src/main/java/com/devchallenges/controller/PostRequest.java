package com.devchallenges.controller;

import com.devchallenges.model.Paging;
import com.devchallenges.model.Post;
import com.devchallenges.service.PostService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("posts")
public class PostRequest {

    private PostService postService = new PostService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Post post){

        post.setCreated(new Date());
        post.setModified(new Date());

        Post createdPost = postService.create(post);
        if(createdPost == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok().entity(createdPost).build();

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}")
    public Response get(@PathParam("postId") String postId){

        // Post id must be passed in
        if(postId == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Post post = postService.find(postId);
        if(post == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(post).build();

    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(Paging paging){

        // Current user id must be passed in
        if(paging.getCurrentUserId() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // If starting row is not passed in, set default
        if(paging.getStartingRow() == null){
            paging.setStartingRow(1);
        }

        // If number of rows is not passed in, set default
        if(paging.getNumberOfRows() == null){
            paging.setNumberOfRows(20);
        }

        List<Post> posts;

        posts = postService.findAll(paging);
        if(posts == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        GenericEntity<List<Post>> list = new GenericEntity<List<Post>>(posts){};

        return Response.ok(list).build();

    }


    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}")
    public Response updateText(@PathParam("postId") String postId, Post updatedPost){

        // Post id must be passed in
        if(postId == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Post id should reference an existing post
        Post post = postService.find(postId);
        if(post == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Update text value on current post ready to be passed to the database
        if(updatedPost.getText() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            post.setText(updatedPost.getText());
            post.setModified(new Date());
        }

        // Send the update request
        UpdateResult updateResult = postService.updateText(post, postId);

        // Verify the request was successful in modifying one post
        if(updateResult.getModifiedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // Get the new updated post
        Post modifiedPost = postService.find(postId);

        return Response.ok().entity(modifiedPost).build();

    }


    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postId}")
    public Response delete(@PathParam("postId") String postId){

        // Post id must be passed in
        if(postId == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Post id should reference an existing post
        Post post = postService.find(postId);
        if(post == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Send the delete request
        DeleteResult deleteResult = postService.delete(postId);

        // Verify the request was successful in deleting one post
        if(deleteResult.getDeletedCount() != 1){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok().build();

    }

}
