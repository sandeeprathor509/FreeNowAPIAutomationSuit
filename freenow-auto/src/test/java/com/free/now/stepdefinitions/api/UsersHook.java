package com.free.now.stepdefinitions.api;

import com.free.now.configs.CustomConfig;
import com.free.now.constants.AppConstants;
import com.free.now.models.*;
import com.free.now.services.UsersService;
import io.cucumber.java.en.And;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;

import java.util.List;

@RequiredArgsConstructor
@CucumberContextConfiguration
@ContextConfiguration(classes = {CustomConfig.class})
public class UsersHook {

    @Autowired
    UsersService usersService;

    @Autowired
    AppConstants appConstants;


    @And("Get the list of users id")
    public void getUserLists() throws Throwable {
        Response response = usersService.getUser();
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "No user is present");
        List<Users> userInfo = response.jsonPath().getList("", Users.class);
        appConstants.setUsers(userInfo);
        System.out.println(appConstants.getUsers());
    }

    @And("Get the {string} of the {string} user")
    public void getUserDataAccToField(String field, String userId) throws Throwable {
        Response response = usersService.getSpecificRequestForUser(userId, field);
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_CREATED, "No user is present");
    }

    @And("Search for the user {string} and save the details")
    public void searchForUsername(String username) throws Throwable {
        Response response = usersService.getUserByName(username);
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "No user is present");
        List<Users> userDetail = response.jsonPath().getList("", Users.class);
        appConstants.setUsers(userDetail);
        for(Users user: userDetail){
            appConstants.setUserEmail(user.getEmail());
            appConstants.setUserName(user.getUsername());
            appConstants.setUserId(user.getId());
        }
    }

}
