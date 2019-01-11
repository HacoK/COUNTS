/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.repository;

import com.SEF4.Discovery.domain.Task;
import com.SEF4.Discovery.util.FileOperation;
import com.SEF4.Discovery.util.JsonDateValueProcessor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author Kevin
 */
public class TaskRepo {
    public static boolean saveTask(Task task) throws FileNotFoundException{
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        String subPath="dataFile/Task/"+task.getTaskID().substring(0,task.getTaskID().indexOf(":"))+"/"+task.getName();
        task.setImagesUrl(subPath+"/Images");
        File dir = new File(path.getPath(),subPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename = task.getName();
        File f = new File(dir, filename);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            JSONObject json = JSONObject.fromObject(task,JsonDateValueProcessor.getJsonConfig());
            FileOperation.writeTxtFile(json.toString(), f);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static Task findTask(String taskID) throws FileNotFoundException, Exception{
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        String subPath="dataFile/Task/"+taskID.substring(0,taskID.indexOf(":"))+"/"+taskID.substring(taskID.indexOf(":")+1);
        File dir = new File(path.getPath(),subPath);
        if (!dir.exists()) {
            return null;
        }
        String filename = taskID.substring(taskID.indexOf(":")+1);
        File f = new File(dir, filename);
        if(!f.exists()){
            return null;
        }
        String jsonStr = FileOperation.readTxtFile(f);
        JSONObject jsonObject = JSONObject.fromObject(jsonStr,JsonDateValueProcessor.getJsonConfig());
        Task task = (Task) JSONObject.toBean(jsonObject, Task.class);
        return task;
    }
    public static File getTags(String taskID) throws FileNotFoundException{
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        String subPath="dataFile/Task/"+taskID.substring(0,taskID.indexOf(":"))+"/"+taskID.substring(taskID.indexOf(":")+1);
        File dir = new File(path.getPath(),subPath);
        if (!dir.exists()) {
            return null;
        }
        File f = new File(dir, "Tags");
        if(!f.exists()){
            return null;
        }
        return f;
    }
    public static List<String> getTasks() throws FileNotFoundException{
        List<String> taskIDs=new ArrayList<String>();
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        String subPath="dataFile/Task";
        File dir = new File(path.getPath(),subPath);
        if(!dir.exists())
            dir.mkdirs();
        String sponsor;
        String project;
        File taskSet;
        String[] sponsors=dir.list();
        File[] taskSets=dir.listFiles();
        for(int i=0;i<sponsors.length;i++){
            sponsor=sponsors[i];
            taskSet=taskSets[i];
            String[] projects=taskSet.list();
            for(int j=0;j<projects.length;j++){
                project=projects[j];
                String taskID=sponsor+":"+project;
                taskIDs.add(taskID);
            }
        }
        return taskIDs;
    }
}
