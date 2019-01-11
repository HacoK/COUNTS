/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.service;

import com.SEF4.Discovery.domain.Tag;
import com.SEF4.Discovery.domain.Task;
import com.SEF4.Discovery.domain.TaskFeedBack;
import com.SEF4.Discovery.domain.User;
import com.SEF4.Discovery.repository.FeedBackRepo;
import com.SEF4.Discovery.repository.TagRepo;
import com.SEF4.Discovery.repository.TaskRepo;
import com.SEF4.Discovery.repository.UserRepo;
import com.SEF4.Discovery.util.IncentiveHelper;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Kevin
 */
public class FeedBackHandler {
    public static String ResStr = "{\"type\":\"JSON\",\"mes\":\"successful!\"}";
        
    public static String pushImg(String taskID,Integer imageID,String userName) throws Exception{
        Tag tag=TagRepo.findTag(taskID, imageID, userName);
        if(tag==null)
            return "2";
        else if(tag.getGrobalTag().equals(""))
            return "2";
        else if(tag.getAreaTags().isEmpty()||tag.getLines().isEmpty())
            return "2";
        TaskFeedBack feedback=FeedBackRepo.findFeedBack(taskID, userName);
        if(feedback==null){
            FeedBackRepo.createFeedBack(taskID,userName);
            feedback=FeedBackRepo.findFeedBack(taskID, userName);
        }
        List<Integer> fin_imgs=feedback.getFin_imgs();
        Integer fin_img=feedback.getFin_img();
        if(!fin_imgs.contains(imageID)){
            fin_imgs.add(imageID);
            fin_img++;
            feedback.setFin_img(fin_img);
            feedback.setFin_imgs(fin_imgs);
        }
        if(Objects.equals(fin_img, feedback.getTotal_img())){
            feedback.setFin_date(new Date());
            feedback.setFin_state(0);
            User user=UserRepo.findUser(userName);
            List<String> fin_list=user.getFin_list();
            fin_list.add(taskID);
            Task task=TaskRepo.findTask(taskID);
            int task_lv=task.getTask_lv();
            switch (task_lv) {
                case 1:
                    int fin_low=user.getFin_low();
                    fin_low++;
                    user.setFin_low(fin_low);
                    break;
                case 2:
                    int fin_mid=user.getFin_intermediate();
                    fin_mid++;
                    user.setFin_intermediate(fin_mid);
                    break;
                case 3:
                    int fin_high=user.getFin_advanced();
                    fin_high++;
                    user.setFin_advanced(fin_high);
                    break;
                default:
                    break;
            }
            UserRepo.updateUser(user);
            IncentiveHelper.getReward(user, task_lv);
            IncentiveHelper.update_worker_lv(user);
            IncentiveHelper.update_worker_title(user);
            FeedBackRepo.updateFeedBack(feedback);
            List<String> involved = InfoHandler.getInvolved(taskID);
            Integer fin_num = 0;
            for (int i = 0; i < involved.size(); i++) {
                String userID = involved.get(i);
                TaskFeedBack feedbackR = FeedBackRepo.findFeedBack(taskID, userID);
                fin_num += feedbackR.getFin_img();
            }
            Integer sum = task.getSum();
            if(Objects.equals(fin_num, sum)){
                task.setTask_fin_flag(true);
                task.setTask_fin(new Date());
                TaskRepo.saveTask(task);
            }
            return "3";
        }
        boolean result=FeedBackRepo.updateFeedBack(feedback);
        if(result)
            return "1";
        else
            return "0";
    }
    
    public static String BP_Reward(String taskID,Integer BP,String workerID) throws Exception{
        if(BP<0||BP>10)
            return "Unvalid BP value...";
        TaskFeedBack feedback=FeedBackRepo.findFeedBack(taskID, workerID);
        if(feedback==null){
            FeedBackRepo.createFeedBack(taskID,workerID);
            feedback=FeedBackRepo.findFeedBack(taskID, workerID);
        }
        if(feedback.getFin_state()==-1){
            return "Task Unfinished...";
        }
        if(feedback.getBP()==0){
            feedback.setBP(BP);
            User user=UserRepo.findUser(workerID);
            user.setBonus_point(user.getBonus_point()+BP);
            UserRepo.updateUser(user);
            FeedBackRepo.updateFeedBack(feedback);
            return ResStr;
        }else{
            return "Have Given BP Before...";
        }
    }
    
    public static String setSatisfaction(String taskID,Integer Satisfaction,String workerID) throws Exception{
        if(Satisfaction<1||Satisfaction>5)
            return "Unvalid rank value...";
        TaskFeedBack feedback=FeedBackRepo.findFeedBack(taskID, workerID);
        if(feedback==null){
            FeedBackRepo.createFeedBack(taskID,workerID);
            feedback=FeedBackRepo.findFeedBack(taskID, workerID);
        }
        if(feedback.getFin_state()==-1){
            return "Task Unfinished...";
        }
        if(feedback.getSatisfaction()==0){
            feedback.setSatisfaction(Satisfaction);
            User user=UserRepo.findUser(workerID);
            switch(Satisfaction){
                case 1:
                    user.setCredit(user.getCredit()-10);
                    break;
                case 2:
                    user.setCredit(user.getCredit()-5);
                    break;
                case 3:
                    break;
                case 4:
                    user.setCredit(user.getCredit()+5);
                    break;
                case 5:
                    user.setCredit(user.getCredit()+10);
                    break;
                default:
                    break;
            }
            UserRepo.updateUser(user);
            FeedBackRepo.updateFeedBack(feedback);
            return ResStr;
        }else{
            return "Have Given Satisfaction Before...";
        }
    }
}
