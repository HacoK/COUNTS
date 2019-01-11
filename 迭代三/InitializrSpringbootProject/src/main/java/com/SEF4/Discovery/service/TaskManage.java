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
import com.SEF4.Discovery.util.IncentiveHelper;
import com.SEF4.Discovery.util.JsonDateConvert;
import com.SEF4.Discovery.util.RandomNumStr;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author Kevin
 */
public class TaskManage {
    public static String TaskRelease(String title,String cash,String deadline,String groups,String urgent,String remarks,String userName,String label) throws FileNotFoundException, Exception{
        User user=UserRepo.findUser(userName);
        Double cost=Double.parseDouble(cash)*Integer.parseInt(groups);
        if(user.getBalance()<cost)
            return "Balance not enough!";
        Integer task_lv=0;
        if(cost>=1&&cost<150)
            task_lv=1;
        else if(cost>=150&&cost<500)
            task_lv=2;
        else if(cost>=500)
            task_lv=3;
        if(!InfoHandler.checkReleaseVaild(user.getSponsor_lv(), task_lv))
            return "Sponsor level too low!";
        Task task = new Task();
        task.setTask_lv(task_lv);
        task.setName(title);
        task.setTaskID(userName + ":" + title);
        task.setBounty(Integer.parseInt(cash));
        task.setRelease_time(new Date());
        task.setDeadline(JsonDateConvert.StringToDate(deadline));
        task.setWorker_num(Integer.parseInt(groups));
        task.setDescription(remarks);
        task.setFin(0);
        if (urgent==null) {
            task.setUrgent(false);
        } else {
            task.setUrgent(true);
        }
        task.setSum(0);
        task.setLabel(label);
        task.setAcp_fin(new Date());
        task.setAcp_fin_flag(false);
        task.setTask_fin(new Date());
        task.setTask_fin_flag(false);
        boolean result=TaskRepo.saveTask(task);
        if(result){
            user.setBalance(user.getBalance()-cost);
            UserRepo.updateUser(user);
            IncentiveHelper.realsedReward(user, task_lv);
            IncentiveHelper.update_sponsor_lv(user);
            IncentiveHelper.update_sponsor_title(user);
        }
        return "1";
    }
    
    public static boolean sumUpdate(String taskID){
        Task task;
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            task = TaskRepo.findTask(taskID);
            File dir=new File(path.getPath(),task.getImagesUrl());
            int sum=dir.list().length*task.getWorker_num();
            task.setSum(sum);
            task.setRandList(RandomNumStr.randomSort(dir.list().length));
            TaskRepo.saveTask(task);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(TaskManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static List<String> discoverTask(String userID) throws Exception{
        List<String> discovery=new ArrayList<>();
        User user=UserRepo.findUser(userID);
        if(user.getCredit()<-20)
            return discovery;
        List<String> relatedTasks=user.getTask_list();
        List<String> taskSet=TaskRepo.getTasks();
        List<String> releasedTask=InfoHandler.getTaskRelease(userID);
        for(String taskID:taskSet){
            Task task=TaskRepo.findTask(taskID);
            Integer task_lv=task.getTask_lv();
            boolean result=InfoHandler.checkReleaseVaild(user.getWorker_lv(), task_lv);
            if(!result)
                continue;
            if(isDuration(taskID)&&isShort(taskID))
                if(!relatedTasks.contains(taskID))
                    if(!releasedTask.contains(taskID))
                        discovery.add(taskID);
        }
        return discovery;
    }
    
    public static boolean isShort(String taskID) throws Exception{
        int needed=TaskRepo.findTask(taskID).getWorker_num();
        return InfoHandler.getInvolved(taskID).size() < needed;
    }
    
    public static double getProcRate(String taskID) throws Exception{
        double processRate;
        Task task=TaskRepo.findTask(taskID);
        processRate=task.getFin()/task.getSum();
        return processRate;
    }
    
    public static boolean isDuration(String taskID) throws Exception{
        Task task=TaskRepo.findTask(taskID);
        Date current=new Date();
        return (!(current.before(task.getRelease_time()))&&!(current.after(task.getDeadline())));
    }
}
