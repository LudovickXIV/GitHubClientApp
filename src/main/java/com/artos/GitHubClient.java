/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artos;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 * @author Ludovick XIV
 */
public interface GitHubClient {
    @GET("/user")
    Call <User> getUser(@Header("Authorization") String authHeader);
    
    @GET("/users/{username}/repos")
    Call <List<Repo>> getRepo(@Path("username") String username, @Query("type") String type);
}
