/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artos;

import java.util.List;

/**
 *
 * @author Ludovick XIV
 */
public interface OnDataPass {
    
    /**
     * Использовать при первом запросе для возврата 
     * данных о пользователе
     * @param user передавать тело ответа от сервера,
     * экземпляр класса пользователя
     */
    void onSuccses(User user);
    /**
     * Использовать при втором запросе для возврата 
     * пользовательских репозиториев
     * @param repo передавать тело ответа от сервера, 
     * список репозиториев
     */
    void onSuccses(List<Repo> repo);
    /**
     * При любой ошибке в запросе retrofit
     * @param code передавать код ошибки
     */
    void onFailure(int code);
}
