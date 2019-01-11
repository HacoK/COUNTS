/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.util;

import com.SEF4.Discovery.domain.Task;
import com.SEF4.Discovery.domain.TaskFeedBack;
import com.SEF4.Discovery.domain.User;
import com.SEF4.Discovery.domain.actor_item;
import com.SEF4.Discovery.domain.feedback_items;
import com.SEF4.Discovery.repository.FeedBackRepo;
import com.SEF4.Discovery.repository.TaskRepo;
import com.SEF4.Discovery.repository.UserRepo;
import com.SEF4.Discovery.service.InfoHandler;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class IncentiveHelper {
    public static String ResStr = "{\"type\":\"JSON\",\"mes\":\"successful!\"}";
    public static boolean update_worker_lv(User user) throws FileNotFoundException{
        if(user.getWorker_exp()>=InfoHandler.getMaxExp(user.getWorker_lv())){
            user.setWorker_exp((user.getWorker_exp()-InfoHandler.getMaxExp(user.getWorker_lv())));
            user.setWorker_lv((user.getWorker_lv()+1));
            switch(user.getWorker_lv()){
                case 5:
                    user.setBonus_point((user.getBonus_point()+10));
                    break;
                case 10:
                    user.setBonus_point((user.getBonus_point()+20));
                    break;
                case 15:
                    user.setBonus_point((user.getBonus_point()+35));
                    break;
                case 20:
                    user.setBonus_point((user.getBonus_point()+50));
                    break;
                default:
                    break;
            }
            boolean result=UserRepo.updateUser(user);
            return result;
        }
        else
            return true;
    }
    public static boolean update_worker_title(User user) throws FileNotFoundException{
//10个低级任务： 初出茅庐
//10个中级任务： 小有所成
//10个高级任务： 登堂入室
//共计50个任务： 职业选手
//共计100个任务：炉火纯青
//共计200个任务：标注大师
        if(user.getFin_low()>=10)
            user.setWorker_title_lv(1);
        if(user.getFin_intermediate()>=10)
            user.setWorker_title_lv(2);
        if(user.getFin_advanced()>=10)
            user.setWorker_title_lv(3);
        Integer fin_num=user.getFin_list().size();
        if(fin_num>=50)
            user.setWorker_title_lv(4);
        if(fin_num>=100)
            user.setWorker_title_lv(5);
        if(fin_num>=200)
            user.setWorker_title_lv(6);
        boolean result=UserRepo.updateUser(user);
            return result;
    }
    public static boolean getReward(User user,Integer task_lv) throws FileNotFoundException{
        switch(task_lv){
            case 1:
                user.setWorker_exp((user.getWorker_exp()+3));
                user.setBonus_point((user.getBonus_point()+2));
                break;
            case 2:
                user.setWorker_exp((user.getWorker_exp()+8));
                user.setBonus_point((user.getBonus_point()+5));
                break;
            case 3:
                user.setWorker_exp((user.getWorker_exp()+15));
                user.setBonus_point((user.getBonus_point()+10));
                break;
            default:
                break;
        }
        boolean result=UserRepo.updateUser(user);
            return result;
    }
    
    public static boolean update_sponsor_lv(User user) throws FileNotFoundException{
        if(user.getSponsor_exp()>=InfoHandler.getMaxExp(user.getSponsor_lv())){
            user.setSponsor_exp((user.getSponsor_exp()-InfoHandler.getMaxExp(user.getSponsor_lv())));
            user.setSponsor_lv((user.getSponsor_lv()+1));
            switch(user.getSponsor_lv()){
                case 5:
                    user.setBonus_point((user.getBonus_point()+10));
                    break;
                case 10:
                    user.setBonus_point((user.getBonus_point()+20));
                    break;
                case 15:
                    user.setBonus_point((user.getBonus_point()+35));
                    break;
                case 20:
                    user.setBonus_point((user.getBonus_point()+50));
                    break;
                default:
                    break;
            }
            boolean result=UserRepo.updateUser(user);
            return result;
        }
        else
            return true;
    }
    
    public static boolean update_sponsor_title(User user) throws FileNotFoundException{
//共计10个任务： 初入行当
//共计50个任务： 小有名气
//共计100个任务：有求必应
//共计200个任务：金牌发布
        Integer released_num=InfoHandler.getTaskRelease(user.getUserID()).size();
        if(released_num>=10)
            user.setSponsor_title_lv(1);
        if(released_num>=50)
            user.setSponsor_title_lv(2);
        if(released_num>=100)
            user.setSponsor_title_lv(3);
        if(released_num>=200)
            user.setSponsor_title_lv(4);
        boolean result=UserRepo.updateUser(user);
            return result;
    }
    
    public static boolean realsedReward(User user,Integer task_lv) throws FileNotFoundException{
        switch(task_lv){
            case 1:
                user.setSponsor_exp((user.getSponsor_exp()+3));
                user.setBonus_point((user.getBonus_point()+2));
                break;
            case 2:
                user.setSponsor_exp((user.getSponsor_exp()+8));
                user.setBonus_point((user.getBonus_point()+5));
                break;
            case 3:
                user.setSponsor_exp((user.getSponsor_exp()+15));
                user.setBonus_point((user.getBonus_point()+10));
                break;
            default:
                break;
        }
        boolean result=UserRepo.updateUser(user);
            return result;
    }
    
    public static boolean sign_in(User user) throws FileNotFoundException{
        Date date=new Date();
        Date cur_date=user.getCur_date();
        Integer diff=DateHandler.differentDays(cur_date, date);
        if(diff==0)
            return false;
        else if(diff==1){
            Integer continuous=user.getContinuous_check_in();
            continuous++;
            if(continuous>6){
                user.setWorker_exp(user.getWorker_exp()+5);
                user.setSponsor_exp(user.getSponsor_exp()+5);
                IncentiveHelper.update_worker_lv(user);
                IncentiveHelper.update_worker_lv(user);
            }else{
                user.setWorker_exp(user.getWorker_exp()+2);
                user.setSponsor_exp(user.getSponsor_exp()+2);
                IncentiveHelper.update_worker_lv(user);
                IncentiveHelper.update_worker_lv(user);
            }
            user.setContinuous_check_in(continuous);
            user.setCur_date(date);
            user.setRemain_task_num(5);
            UserRepo.updateUser(user);
            return true;
        }
        else{
            user.setWorker_exp(user.getWorker_exp() + 2);
            user.setSponsor_exp(user.getSponsor_exp() + 2);
            IncentiveHelper.update_worker_lv(user);
            IncentiveHelper.update_worker_lv(user);
            user.setContinuous_check_in(1);
            user.setCur_date(date);
            user.setRemain_task_num(5);
            UserRepo.updateUser(user);
            return true;
        }
    }
    

    public static String withdraw(String userName,Integer cash) throws Exception{
        User user=UserRepo.findUser(userName);
        boolean result=false;
        if(user.getBalance()>=cash+10){
             user.setBalance(user.getBalance()-cash-10);
             result=UserRepo.updateUser(user);
             if (result) {
                 //withdraw(userName,cash);
                return ResStr;
            } else {
                return "Unexpected Error!";
            }
        }
        else{
            return "Balance Not Enough!";
        }
    }
    
    public static String BP_exchange(String userName,Integer BP,Integer giftID) throws Exception{
        User user=UserRepo.findUser(userName);
        boolean result=false;
        if(user.getBonus_point()>=BP){
             user.setBonus_point(user.getBonus_point()-BP);
             result=UserRepo.updateUser(user);
             if (result) {
                //sendGift(userName,giftID);
                return ResStr;
            } else {
                return "Unexpected Error!";
            }
        }
        else{
            return "Bonus_point Not Enough!";
        }
    }
    
    public static feedback_items getFeedBacks(String userName,String taskID) throws Exception{
        feedback_items feedbacks=new feedback_items();
        Task task=TaskRepo.findTask(taskID);
        if(task==null)
            return feedbacks;
        feedbacks.setCash(task.getBounty());
        feedbacks.setDate(task.getDeadline());
        feedbacks.setNums(task.getWorker_num());
        List<actor_item> actors=new ArrayList<actor_item>();
        List<String> involved=InfoHandler.getInvolved(taskID);
        for(int i=0;i<involved.size();i++){
            actor_item actor=new actor_item();
            String userID=involved.get(i);
            actor.setId(userID);
            TaskFeedBack feedback=FeedBackRepo.findFeedBack(taskID, userID);
            Integer progress=100*feedback.getFin_img()/feedback.getTotal_img();
            actor.setProcess(String.valueOf(progress));
            actors.add(actor);
        }
        feedbacks.setActors(actors);
        return feedbacks;
    }
}
