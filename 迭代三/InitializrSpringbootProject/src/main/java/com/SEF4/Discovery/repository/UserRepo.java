/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.repository;

import com.SEF4.Discovery.domain.User;
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
public class UserRepo {
    public static boolean saveUser(User user) throws FileNotFoundException {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        File dir = new File(path.getPath(), "dataFile/user/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename=user.getUserID();
        File f=new File(dir,filename);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            JSONObject json = JSONObject.fromObject(user,JsonDateValueProcessor.getJsonConfig());
            FileOperation.writeTxtFile(json.toString(), f);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static User findUser(String userID) throws FileNotFoundException, Exception {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        File dir = new File(path.getPath(), "dataFile/user/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename=userID;
        File f=new File(dir,filename);
        if(!f.exists()){
            return null;
        }
        String jsonStr = FileOperation.readTxtFile(f);
        if(jsonStr.charAt(0)!='{')
            return null;
        JSONObject jsonObject = JSONObject.fromObject(jsonStr,JsonDateValueProcessor.getJsonConfig());
        User user = (User) JSONObject.toBean(jsonObject, User.class);
        return user;
    }

    public static boolean updateUser(User user) throws FileNotFoundException {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        File dir = new File(path.getPath(), "dataFile/user/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename=user.getUserID();
        File f=new File(dir,filename);
        if(!f.exists()){
            return false;
        }
        try {
            JSONObject json = JSONObject.fromObject(user,JsonDateValueProcessor.getJsonConfig());
            FileOperation.writeTxtFile(json.toString(), f);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static List<User> getUsers() throws FileNotFoundException, Exception{
        List<User> users=new ArrayList<User>();
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        File dir = new File(path.getPath(), "dataFile/user/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String[] userIDs=dir.list();
        for(int i=0;i<userIDs.length;i++){
            users.add(findUser(userIDs[i]));
        }
        return users;
    }
}
