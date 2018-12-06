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
    /**
     * Для первого запроса к серверу для получения 
     * данных о пользователе
     * @param authHeader добавление заголовка для 
     * базовой авторизации
     * @return возвращает экземпляр класса User
     */
    @GET("/user")
    Call <User> getUser(@Header("Authorization") String authHeader);
    
    /**
     * Для второго запроса к серверу для получения
     * пользовательских репозиториев
     * @param username передаём пользовательский логин
     * получаем его из первого запроса
     * @param type передаём возможные параметры:
     * all - все репозитории, 
     * owner - собственные, 
     * member - в тех что пользователь участник;
     * @return возвращает список репозиториев
     */
    @GET("/users/{username}/repos")
    Call <List<Repo>> getRepo(@Path("username") String username, @Query("type") String type);
}
