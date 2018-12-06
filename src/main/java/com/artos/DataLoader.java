/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artos;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Класс для работы с запросами на сервер
 * @author Ludovick XIV
 */
public class DataLoader {

    private OnDataPass listener;

    /**
     * Setter для слушателя
     *
     * @param listener OnDataPass
     */
    public void setUserListener(OnDataPass listener) {
        this.listener = listener;
    }

    /**
     * Использовать для первого запроса, для окна лониг
     * @param userName принимает пользовательский лониг
     * @param userPassword принимает пользовательский пароль
     */
    public void loadData(String userName, String userPassword) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit builder = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("https://api.github.com/")
                            .build();
                    
                    String base = userName + ":" + userPassword;
                    String authBasic = "Basic " + Base64.getEncoder().encodeToString(base.getBytes("utf-8")); // кодируем строку в base64
                    
                    GitHubClient client = builder.create(GitHubClient.class);
                    Call<User> call = client.getUser(authBasic);
                    
                    Response<User> response = call.execute();
                    if (response.isSuccessful()) {
                        listener.onSuccses(response.body());
                    } else {
                        listener.onFailure(response.code());
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ioe) {
                }
            }
        });
        thread.start();

    }

    /**
     * Использовать для второго запроса,
     * для получения списка репозиториев
     * @param user передаём объект пользователь
     * его мы получили ранее
     */
    public void getRepo(User user) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit builder = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("https://api.github.com/")
                            .build();

                    GitHubClient client = builder.create(GitHubClient.class);

                    Call<List<Repo>> callRepo = client.getRepo(user.getLogin(), "all");
                    Response<List<Repo>> response = callRepo.execute();

                    if (response.isSuccessful()) {
                        listener.onSuccses(response.body());
                    } else {
                        listener.onFailure(response.code());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        thread.start();
    }
}
