/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.DateFactory;

import com.SEF4.Discovery.domain.User;
import com.SEF4.Discovery.repository.UserRepo;
import com.SEF4.Discovery.util.JsonDateConvert;
import com.SEF4.Discovery.util.RandomNumStr;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Kevin
 */
public class UserFactory {
    public static void main(String[] args) throws FileNotFoundException{
        Random rand = new Random(); 
        for(int i=0;i<200;i++){
            User user = new User();
        user.setAuthority("user");
        user.setBalance(0.00);
        user.setPassword(RandomNumStr.get5Rand());
        user.setTask_fin(0);
        user.setTask_list(new ArrayList<String>());
        user.setFin_list(new ArrayList<String>());
        user.setOutdated_list(new ArrayList<String>());
        user.setUserID(RandomNumStr.get5Rand());
        String username = "众包用户" + RandomNumStr.get5Rand();
        user.setUsername(username);
        user.setMailbox("......@email.com");
        user.setRegist_date(JsonDateConvert.StringToDate("2018-06-01"));
        user.setWorker_lv(rand.nextInt(5));
        user.setSponsor_lv(rand.nextInt(5));
        user.setContinuous_check_in(1);
        user.setWorker_exp(0);
        user.setSponsor_exp(0);
        user.setCur_date(JsonDateConvert.StringToDate("2018-06-18"));
        user.setRemain_task_num(5);
        user.setCredit(0);
        user.setWorker_title_lv(rand.nextInt(1));
        user.setSponsor_title_lv(0);
        user.setBonus_point(rand.nextInt(100));
        user.setFin_low(rand.nextInt(5));
        user.setFin_intermediate(rand.nextInt(2));
        user.setFin_advanced(0);
        user.setSign_in_list(new ArrayList<String>());
        UserRepo.saveUser(user);
        }
    }
}
