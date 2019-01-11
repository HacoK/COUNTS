/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.service;

import com.SEF4.Discovery.domain.Task;
import com.SEF4.Discovery.domain.User;
import com.SEF4.Discovery.repository.TaskRepo;
import com.SEF4.Discovery.repository.UserRepo;
import com.SEF4.Discovery.util.EnDe_Code;
import com.SEF4.Discovery.util.RandomNumStr;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author Kevin
 */
public class UserManage {
    
    public static boolean UserConfirm(String userID,String password) throws FileNotFoundException, Exception{
        User user=UserRepo.findUser(userID);
        if(user==null)
            return false;
        else{
            String pswd=user.getPassword();
            return pswd.equals(password);
        }
    }
    
    public static boolean UserRegister(String userID,String password,String email) throws FileNotFoundException, Exception{
        User user=UserRepo.findUser(userID);
        if(user!=null)
            return false;
        else {
            user = new User();
            user.setAuthority("user");
            user.setBalance(0.00);
            user.setPassword(password);
            user.setTask_fin(0);
            user.setTask_list(new ArrayList<String>());
            user.setFin_list(new ArrayList<String>());
            user.setOutdated_list(new ArrayList<String>());
            user.setUserID(userID);  
            String username="众包用户"+RandomNumStr.get5Rand();
            user.setUsername(username);
            user.setMailbox(email);
            user.setRegist_date(new Date());
            user.setWorker_lv(1);
            user.setSponsor_lv(1);
            user.setContinuous_check_in(1);
            user.setWorker_exp(0);
            user.setSponsor_exp(0);
            user.setCur_date(new Date());
            user.setRemain_task_num(5);
            user.setCredit(0);
            user.setWorker_title_lv(0);
            user.setSponsor_title_lv(0);
            user.setBonus_point(0);
            user.setFin_low(0);
            user.setFin_intermediate(0);
            user.setFin_advanced(0);
            user.setSign_in_list(new ArrayList<String>());
            UserRepo.saveUser(user);
            return true;
        }
    }
    
    public static boolean acceptTask(String userID,String taskID) throws FileNotFoundException, Exception{
        User user=UserRepo.findUser(userID);
        if(user==null)
            return false;
        else{
            if(user.getRemain_task_num()>0){
                List<String> taskList = user.getTask_list();
                taskList.add(taskID);
                user.setTask_list(taskList);
                user.setRemain_task_num((user.getRemain_task_num()-1));
                boolean result = UserRepo.updateUser(user);
                Task task=TaskRepo.findTask(taskID);
                if(InfoHandler.getInvolved(taskID).equals(task.getWorker_num())){
                    task.setAcp_fin_flag(true);
                    task.setAcp_fin(new Date());
                    TaskRepo.saveTask(task);
                }
                return result;
            }
            else
                return false;
        }
    }
}
