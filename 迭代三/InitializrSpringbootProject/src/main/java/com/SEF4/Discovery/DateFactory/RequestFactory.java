/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.DateFactory;

import com.SEF4.Discovery.Stat.Statistics;
import com.SEF4.Discovery.domain.User;
import com.SEF4.Discovery.repository.UserRepo;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Kevin
 */
public class RequestFactory {
    public static void main(String[] args) throws FileNotFoundException, Exception{
        Random rand = new Random(); 
        double random=(90+rand.nextInt(21))/100.0;
        int ran;
        List<User> users=UserRepo.getUsers();
        for(int i=0;i<3000*random;i++){
            //Statistics.save_request_time("23:"+String.valueOf(rand.nextInt(60)));
            ran=rand.nextInt(406);
            //Statistics.save_user_active_time(users.get(ran).getUserID(), "23:"+String.valueOf(rand.nextInt(60)));
        }
    }
}
