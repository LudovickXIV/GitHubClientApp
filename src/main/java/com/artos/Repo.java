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
public class Repo {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("forks_count")
    private int forksCount;
    @SerializedName("stargazers_count")
    private int stargazersCount;
    @SerializedName("watchers_count")
    private int watchersCount;
    @SerializedName("clone_url")
    private String cloneUrl;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getForksCount() {
        return forksCount;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public int getWatchersCount() {
        return watchersCount;
    }
    
    public String getCloneUrl() {
        return cloneUrl;
    }
}
