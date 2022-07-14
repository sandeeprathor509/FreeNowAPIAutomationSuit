package com.free.now.stepdefinitions.api;

import com.free.now.constants.AppConstants;
import com.free.now.models.Comment;
import com.free.now.services.CommentService;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CommentsHook {

    @Autowired
    AppConstants appConstants;

    @Autowired
    CommentService commentService;

    @And("Search for the saved user email using postId")
    public void getEmailUsingPostId() throws Throwable {
        ArrayList<String> userEmailIds = new ArrayList<>();
        for(int i=0; i<appConstants.getPostId().size(); i++){
            Response response = commentService.getCommentByPostId(appConstants.getPostId().get(i));
            List<Comment> commentList = response.jsonPath().getList("", Comment.class);
            for(Comment comment: commentList){
                userEmailIds.add(comment.getEmail());
            }
            appConstants.setEmail(userEmailIds);
            for(String search: appConstants.getEmail()){
                if(search.equalsIgnoreCase(appConstants.getUserEmail())){
                    System.out.println("Email found "+search);
                }
            }
        }
    }
}
