/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artos;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Ludovick XIV
 */
public class User {
    
    @SerializedName("login")
    private String login;
    @SerializedName("name")
    private String name;
    @SerializedName("avatar_url")
    private String photo;
    @SerializedName("repos_url")
    private String repos;
    
    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }
    
    public String getRepos(){
        return repos;
    }
    
}
