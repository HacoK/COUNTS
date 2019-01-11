/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.service;

import com.SEF4.Discovery.domain.User;
import com.SEF4.Discovery.repository.TaskRepo;
import com.SEF4.Discovery.repository.UserRepo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ResourceUtils;



/**
 *
 * @author Kevin
 */
public class InfoHandler {
    public static List<String> getTaskRelease(String userID) throws FileNotFoundException{
        List<String> taskIDs=new ArrayList<String>();
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        String subPath="dataFile/Task";
        File dir = new File(path.getPath(),subPath);
        String sponsor=userID;
        String project;
        File taskSet=new File(dir,sponsor);
        if(!taskSet.exists())
            return taskIDs;
        String[] projects=taskSet.list();
        for(int i=0;i<projects.length;i++){
            project=projects[i];
            String taskID=sponsor+":"+project;
            taskIDs.add(taskID);
        }
        return taskIDs;
    }
    
    public static List<String> getInvolved(String taskID) throws FileNotFoundException, Exception{
        List<User> users=UserRepo.getUsers();
        List<String> involved=new ArrayList<>();
        for(int i=0;i<users.size();i++){
            List<String> task_total=users.get(i).getTask_list();
            if(task_total.contains(taskID))
                involved.add(users.get(i).getUserID());
        }
        return involved;
    }
    
    public static List<String> getProc(String userID) throws Exception{
        User user=UserRepo.findUser(userID);
        List<String> proc=new ArrayList<String>();
        List<String> task_total=user.getTask_list();
        List<String> task_fin=user.getFin_list();
        List<String> task_outdated=user.getOutdated_list();
        for(String taskID:task_total){
            if(!task_fin.contains(taskID)&&!TaskManage.isDuration(taskID)){
                if(!task_outdated.contains(taskID))
                    task_outdated.add(taskID);
            }
            if(!task_fin.contains(taskID)&&!task_outdated.contains(taskID)){
                proc.add(taskID);
            }
        }
        UserRepo.updateUser(user);
        return proc;
    }
    
    public static List<Integer> getRankList() throws Exception{
        List<Integer> rankList=new ArrayList<Integer>();
        List<User> users=UserRepo.getUsers();
        for(User user:users){
            rankList.add(user.getFin_list().size());
        }
        for(int i=0;i<rankList.size()-1;i++)
            for(int j=1;j<rankList.size()-i;j++){
                if(rankList.get(j-1)<rankList.get(j)){
                    int temp=rankList.get(j);
                    rankList.set(j, rankList.get(j-1));
                    rankList.set(j-1, temp);
                }
            }
        return rankList;
    }
    
    public static int getRank(String userID)throws Exception{
        List<Integer> rankList= getRankList();
        int fin=UserRepo.findUser(userID).getFin_list().size();
        return (rankList.indexOf(fin)+1);
    }
    
    public static boolean checkReleaseVaild(Integer sponsor_lv,Integer task_lv){
        if(task_lv==2&&sponsor_lv<4)
            return false;
        else if(task_lv==3&&sponsor_lv<8)
            return false;
        else
            return true;
    }
    
    public static Integer getMaxExp(Integer lv){
        switch(lv){
            case 1:
                return 10;
            case 2:
                return 15;
            case 3:
                return 20;
            case 4:
                return 30;
            case 5:
                return 40;
            case 6:
                return 60;
            case 7:
                return 80;
            case 8:
                return 100;
            case 9:
                return 120;
            case 10:
                return 140;
            case 11:
                return 160;
            case 12:
                return 180;
            case 13:
                return 200;
            case 14:
                return 220;
            case 15:
                return 240;
            case 16:
                return 260;
            case 17:
                return 280;
            case 18:
                return 300;
            case 19:
                return 320;
            case 20:
                return 999;
            default:
                return 999;
        }
    }
    
    public static String getWorkerTitle(Integer num){
        switch(num){
            case 0:
                return "暂无称号";
            case 1:
                return "初出茅庐";
            case 2:
                return "小有所成";
            case 3:
                return "登堂入室";
            case 4:
                return "职业选手";
            case 5:
                return "炉火纯青";
            case 6:
                return "标注大师";
            default:
                return "绝版称号";
        }
    }
    public static String getSponsorTitle(Integer num){
        switch(num){
            case 0:
                return "暂无称号";
            case 1:
                return "初入行当";
            case 2:
                return "小有名气";
            case 3:
                return "有求必应";
            case 4:
                return "金牌发布";
            default:
                return "绝版称号";
        }
    }
}
