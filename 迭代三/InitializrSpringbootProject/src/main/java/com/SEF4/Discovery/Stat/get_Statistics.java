/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.Stat;

import com.SEF4.Discovery.domain.Task;
import com.SEF4.Discovery.domain.TaskFeedBack;
import com.SEF4.Discovery.domain.User;
import com.SEF4.Discovery.repository.FeedBackRepo;
import com.SEF4.Discovery.repository.TaskRepo;
import com.SEF4.Discovery.repository.UserRepo;
import com.SEF4.Discovery.service.InfoHandler;
import com.SEF4.Discovery.util.DateHandler;
import com.SEF4.Discovery.util.FileOperation;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author Kevin
 */
public class get_Statistics {
    public static List<Integer> get_Views() throws FileNotFoundException{
        List<Integer> times_stat=new ArrayList<>();
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        File dir = new File(path.getPath(), "dataFile/Statistics/views");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        for(int i=0;i<7;i++){
            String filename = weekDays[i];
            File f = new File(dir, filename);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                    FileOperation.writeTxtFile(String.valueOf(0), f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Integer times = Integer.parseInt(FileOperation.readTxtFile(f).substring(0,FileOperation.readTxtFile(f).length()-1));
                times_stat.add(times);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return times_stat;       
    }
    
    public static List<Integer> get_requests() throws FileNotFoundException{
        List<Integer> active_time=new ArrayList<Integer>();
        for(int i=0;i<24;i++)
            active_time.add(0);
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        File dir = new File(path.getPath(), "dataFile/Statistics/active_time");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename="total_time";
        File f=new File(dir,filename);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            String[] requests=FileOperation.readTxtFile(f).split("\n");
            for(int i=0;i<requests.length;i++){
                int time=Integer.parseInt(requests[i].substring(0,requests[i].indexOf(":")));
                active_time.set(time, active_time.get(time)+1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return active_time;
    }
    
    public static List<Integer> get_user_active_time(String userID) throws FileNotFoundException{
        List<Integer> user_active_time=new ArrayList<Integer>();
        for(int i=0;i<24;i++)
            user_active_time.add(0);
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        File dir = new File(path.getPath(), "dataFile/Statistics/active_time/"+userID);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename="user_time";
        File f=new File(dir,filename);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            String[] requests=FileOperation.readTxtFile(f).split("\n");
            for(int i=0;i<requests.length;i++){
                int time=Integer.parseInt(requests[i].substring(0,requests[i].indexOf(":")));
                user_active_time.set(time, user_active_time.get(time)+1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_active_time;
    }
    
    //总用户数，活跃用户数,普通用户数
    public static List<Integer> get_userStat() throws Exception{
        List<Integer> userStat=new ArrayList<Integer>();
        List<User> users=UserRepo.getUsers();
        userStat.add(users.size());
        int active_user=0;
        int normal_user=0;
        for(User user:users){
            if(user.getContinuous_check_in()>=7)
                active_user++;
            else if(DateHandler.differentDays(user.getCur_date(),new Date())==0){
                normal_user++;
            }
        }
        userStat.add(active_user);
        userStat.add(normal_user);
        userStat.add(users.size()-active_user-normal_user);
        return userStat;
    }
    
    public static List<Integer> get_workerStat() throws Exception{
        List<Integer> workerStat=new ArrayList<Integer>();
        List<User> users=UserRepo.getUsers();
        workerStat.add(users.size());
        int elite_user=0;
        for(User user:users){
            if(user.getWorker_lv()>9)
                elite_user++;
        }
        workerStat.add(elite_user);
        workerStat.add(users.size()-elite_user);
        return workerStat;
    }
    
    public static List<Integer> get_sponsorStat() throws Exception{
        List<Integer> sponsorSStat=new ArrayList<Integer>();
        List<User> users=UserRepo.getUsers();
        sponsorSStat.add(users.size());
        int elite_user=0;
        for(User user:users){
            if(user.getSponsor_lv()>9)
                elite_user++;
        }
        sponsorSStat.add(elite_user);
        sponsorSStat.add(users.size()-elite_user);
        return sponsorSStat;
    }
    //总任务数，已完成任务数,进行中任务数
    public static List<Integer> get_taskStat() throws FileNotFoundException, Exception{
        List<Integer> taskStat=new ArrayList<>();
        List<String> tasks=TaskRepo.getTasks();
        taskStat.add(tasks.size());
//        List<String> fin_tasks=new ArrayList<>();
//        Date cur_date=new Date();
//        for(String taskID:tasks){
//            Task task=TaskRepo.findTask(taskID);
//            if(cur_date.after(task.getDeadline())){
//                fin_tasks.add(taskID);
//                continue;
//            }
//            List<String> involved=InfoHandler.getInvolved(taskID);
//            Integer fin_num=0;
//        
//            for (int i = 0; i < involved.size(); i++) {
//                String userID = involved.get(i);
//                TaskFeedBack feedback = FeedBackRepo.findFeedBack(taskID, userID);
//                fin_num += feedback.getFin_img();
//            }
//
//            Integer sum=task.getSum();
//            if(Objects.equals(fin_num, sum)){
//                fin_tasks.add(taskID);
//            }
//        }
//        taskStat.add(fin_tasks.size());
//        taskStat.add(tasks.size()-fin_tasks.size());
        return taskStat;
    }
    
    public static List<String> getUserFavLabel(String userName) throws Exception{
        User user=UserRepo.findUser(userName);
        List<String> tasks=user.getTask_list();
        List<String> labels=new ArrayList<>();
        List<Integer> counts=new ArrayList<>();
        for(String taskID:tasks){
            if(TaskRepo.findTask(taskID)==null)
                continue;
            String label=TaskRepo.findTask(taskID).getLabel();
            if(!labels.contains(label)){
                labels.add(label);
                counts.add(1);
            }else{
                int index=labels.indexOf(label);
                counts.set(index, counts.get(index)+1);
            }
        }
        for(int i=1;i<counts.size();i++){
            for(int j=0;j<counts.size()-i;j++){
                if(counts.get(j)<counts.get(j+1)){
                    int tmpInt=counts.get(j);
                    String tmpStr=labels.get(j);
                    counts.set(j, counts.get(j+1));
                    labels.set(j, labels.get(j+1));
                    counts.set(j+1, tmpInt);
                    labels.set(j+1, tmpStr);
                }
            }
        }
        return labels;
    }
    
    public static List<String> getFavLabel() throws Exception{
        List<User> users=UserRepo.getUsers();
        List<String> labels=new ArrayList<>();
        List<Integer> counts=new ArrayList<>();
        for(User user:users){
            List<String> tasks = user.getTask_list();
            for (String taskID : tasks) {
                if(TaskRepo.findTask(taskID)==null)
                    continue;
                String label = TaskRepo.findTask(taskID).getLabel();
                if (!labels.contains(label)) {
                    labels.add(label);
                    counts.add(1);
                } else {
                    int index = labels.indexOf(label);
                    counts.set(index, counts.get(index) + 1);
                }
            }
        }
        for(int i=1;i<counts.size();i++){
            for(int j=0;j<counts.size()-i;j++){
                if(counts.get(j)<counts.get(j+1)){
                    int tmpInt=counts.get(j);
                    String tmpStr=labels.get(j);
                    counts.set(j, counts.get(j+1));
                    labels.set(j, labels.get(j+1));
                    counts.set(j+1, tmpInt);
                    labels.set(j+1, tmpStr);
                }
            }
        }
        return labels;
    }
    
    public static List<Integer> getLabelCount() throws Exception{
        List<User> users=UserRepo.getUsers();
        List<String> labels=new ArrayList<>();
        List<Integer> counts=new ArrayList<>();
        for(User user:users){
            List<String> tasks = user.getTask_list();
            for (String taskID : tasks) {
                if(TaskRepo.findTask(taskID)==null)
                    continue;
                String label = TaskRepo.findTask(taskID).getLabel();
                if(label==null)
                    continue;
                if (!labels.contains(label)) {
                    labels.add(label);
                    counts.add(1);
                } else {
                    int index = labels.indexOf(label);
                    counts.set(index, counts.get(index) + 1);
                }
            }
        }
        for(int i=1;i<counts.size();i++){
            for(int j=0;j<counts.size()-i;j++){
                if(counts.get(j)<counts.get(j+1)){
                    int tmpInt=counts.get(j);
                    String tmpStr=labels.get(j);
                    counts.set(j, counts.get(j+1));
                    labels.set(j, labels.get(j+1));
                    counts.set(j+1, tmpInt);
                    labels.set(j+1, tmpStr);
                }
            }
        }
        return counts;
    }
    
    public static List<Integer> get_PastViews() throws FileNotFoundException{
        List<Integer> times_stat=new ArrayList<>();
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        File dir = new File(path.getPath(), "dataFile/Statistics/date_views");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String[] dates = dir.list();
        for(int i=0;i<dates.length;i++){
            String filename = dates[i];
            File f = new File(dir, filename);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                    FileOperation.writeTxtFile(String.valueOf(0), f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Integer times = Integer.parseInt(FileOperation.readTxtFile(f).substring(0,FileOperation.readTxtFile(f).length()-1));
                times_stat.add(times);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return times_stat;       
    }
    
    public static List<String> get_PastDates() throws FileNotFoundException{
        List<String> past_dates=new ArrayList<>();
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        File dir = new File(path.getPath(), "dataFile/Statistics/date_views");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String[] dates = dir.list();
        for(int i=0;i<dates.length;i++){
            past_dates.add(dates[i]);
        }
        return past_dates;       
    }
    
    public static List<Double> get_UserFreeDegree() throws Exception{
        List<User> users=UserRepo.getUsers();
        List<Double> userFreeDegree=new ArrayList<Double>();
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        for(User user:users){
            if(DateHandler.differentDays(user.getCur_date(),new Date())!=0)
                continue;
            File dir = new File(path.getPath(), "dataFile/Statistics/active_time/" + user.getUserID());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filename = "user_time";
            File f = new File(dir, filename);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                String[] requests = FileOperation.readTxtFile(f).split("\n");
                Double daily_calls=(double)requests.length/DateHandler.differentDays(user.getRegist_date(), new Date());
                userFreeDegree.add(daily_calls);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userFreeDegree;
    }
    
    public static List<String> get_UserFavLabel() throws Exception{
        List<User> users=UserRepo.getUsers();
        List<String> UserFavLabel=new ArrayList<String>();
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        for(User user:users){
            if(DateHandler.differentDays(user.getCur_date(),new Date())!=0)
                continue;
            if(getUserFavLabel(user.getUserID()).size()==0)
                continue;
            UserFavLabel.add(getUserFavLabel(user.getUserID()).get(0));
        }
        return UserFavLabel;
    }
    
    //满意度指标：（1/接完-发布）*0.5+(ddl-发布)/（完成-发布）*0.5*0.5         【两项最大均为0.5】
    public static List<Double> get_TaskSt() throws FileNotFoundException, Exception{
        List<String> taskIDs=TaskRepo.getTasks();
        List<Double> TaskSt=new ArrayList<Double>();
        for(String taskID:taskIDs){
            Task task=TaskRepo.findTask(taskID);
            if(task.isAcp_fin_flag()&&task.isTask_fin_flag()){
                Double factor1=1.0/DateHandler.differentDays(task.getAcp_fin(), task.getRelease_time())*0.5;
                if(factor1>0.5)
                    factor1=0.5;
                Double factor2=(DateHandler.differentDays(task.getDeadline(), task.getRelease_time())*0.5*0.5)/(DateHandler.differentDays(task.getTask_fin(), task.getRelease_time()));
                if(factor2>0.5)
                    factor2=0.5;
                TaskSt.add(factor1+factor2);
            }
        }
        return TaskSt;
    }
    
    public static List<String> get_TaskLabel() throws FileNotFoundException, Exception{
        List<String> taskIDs=TaskRepo.getTasks();
        List<String> TaskLabel=new ArrayList<String>();
        for(String taskID:taskIDs){
            Task task=TaskRepo.findTask(taskID);
            if(task.isAcp_fin_flag()&&task.isTask_fin_flag()){
                TaskLabel.add(task.getLabel());
            }
        }
        return TaskLabel;
    }
    
    public static List<Integer> get_PicNum() throws FileNotFoundException, Exception{
        List<String> taskIDs=TaskRepo.getTasks();
        List<Integer> PicNum=new ArrayList<Integer>();
        for(String taskID:taskIDs){
            Task task=TaskRepo.findTask(taskID);
            if(task.isAcp_fin_flag()&&task.isTask_fin_flag()){
                PicNum.add(task.getSum()/task.getWorker_num());
            }
        }
        return PicNum;
    }
    
    public static List<Integer> get_TaskBounty() throws FileNotFoundException, Exception{
        List<String> taskIDs=TaskRepo.getTasks();
        List<Integer> TaskBounty=new ArrayList<Integer>();
        for(String taskID:taskIDs){
            Task task=TaskRepo.findTask(taskID);
            if(task.isAcp_fin_flag()&&task.isTask_fin_flag()){
                TaskBounty.add(task.getBounty());
            }
        }
        return TaskBounty;
    }
}
