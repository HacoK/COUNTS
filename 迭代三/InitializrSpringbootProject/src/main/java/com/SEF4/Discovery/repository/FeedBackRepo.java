/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.repository;

import com.SEF4.Discovery.domain.Task;
import com.SEF4.Discovery.domain.TaskFeedBack;
import com.SEF4.Discovery.util.FileOperation;
import com.SEF4.Discovery.util.JsonDateValueProcessor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import net.sf.json.JSONObject;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author Kevin
 */
public class FeedBackRepo {
    public static boolean createFeedBack(String taskID,String userName) throws FileNotFoundException, Exception {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        String subPath="dataFile/Task/"+taskID.substring(0,taskID.indexOf(":"))+"/"+taskID.substring(taskID.indexOf(":")+1)+"/Tags/"+userName;
        File dir = new File(path.getPath(),subPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        String filename = "FeedBack";
        File f = new File(dir, filename);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        TaskFeedBack feedback=new TaskFeedBack();
        feedback.setFin_state(-1);
        feedback.setTaskID(taskID);
        feedback.setUserID(userName);
        Task task=TaskRepo.findTask(taskID);
        feedback.setTotal_img(task.getSum()/task.getWorker_num());
        feedback.setFin_img(0);
        feedback.setFin_imgs(new ArrayList<Integer>());
        feedback.setFin_date(new Date());
        try {
            JSONObject json = JSONObject.fromObject(feedback,JsonDateValueProcessor.getJsonConfig());
            FileOperation.writeTxtFile(json.toString(), f);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static TaskFeedBack findFeedBack(String taskID,String userName) throws FileNotFoundException, Exception {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        String subPath="dataFile/Task/"+taskID.substring(0,taskID.indexOf(":"))+"/"+taskID.substring(taskID.indexOf(":")+1)+"/Tags/"+userName;
        File dir = new File(path.getPath(),subPath);
        if (!dir.exists()) {
            return null;
        }
        
        String filename = "FeedBack";
        File f = new File(dir, filename);
        if(!f.exists()){
            return null;
        }
        String jsonStr = FileOperation.readTxtFile(f);
        JSONObject jsonObject = JSONObject.fromObject(jsonStr,JsonDateValueProcessor.getJsonConfig());
        TaskFeedBack feedback = (TaskFeedBack) JSONObject.toBean(jsonObject, TaskFeedBack.class);
        return feedback;
    }
    
    public static boolean updateFeedBack(TaskFeedBack feedback) throws FileNotFoundException {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        String subPath="dataFile/Task/"+feedback.getTaskID().substring(0,feedback.getTaskID().indexOf(":"))+"/"+feedback.getTaskID().substring(feedback.getTaskID().indexOf(":")+1)+"/Tags/"+feedback.getUserID();
        File dir = new File(path.getPath(),subPath);
        if (!dir.exists()) {
            return false;
        }
        
        String filename = "FeedBack";
        File f = new File(dir, filename);
        try {
            JSONObject json = JSONObject.fromObject(feedback,JsonDateValueProcessor.getJsonConfig());
            FileOperation.writeTxtFile(json.toString(), f);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
