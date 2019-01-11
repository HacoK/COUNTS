/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.Stat;

import com.SEF4.Discovery.util.DateHandler;
import com.SEF4.Discovery.util.FileOperation;
import com.SEF4.Discovery.util.JsonDateConvert;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author Kevin
 */
public class Statistics {
    public static boolean save_request_time() throws FileNotFoundException{
        String time=DateHandler.convert_Date(new Date());
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
            FileOperation.contentToTxt(f, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean save_user_active_time(String userID) throws FileNotFoundException{
        String time=DateHandler.convert_Date(new Date());
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
            FileOperation.contentToTxt(f, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean save_Views(Date date) throws FileNotFoundException{
        String weekDays=DateHandler.getWeekOfDate(date);
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        File dir = new File(path.getPath(), "dataFile/Statistics/views");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename=weekDays;
        File f=new File(dir,filename);
        if(!f.exists()){
            try {
                f.createNewFile();
                FileOperation.writeTxtFile(String.valueOf(0), f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Integer times=Integer.parseInt(FileOperation.readTxtFile(f).substring(0,FileOperation.readTxtFile(f).length()-1));
            times++;
            FileOperation.writeTxtFile(String.valueOf(times), f);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean save_PastViews(Date date) throws FileNotFoundException{
        String view_date=JsonDateConvert.getTimeStr(date);
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        File dir = new File(path.getPath(), "dataFile/Statistics/date_views");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename=view_date;
        File f=new File(dir,filename);
        if(!f.exists()){
            try {
                f.createNewFile();
                FileOperation.writeTxtFile(String.valueOf(0), f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Integer times=Integer.parseInt(FileOperation.readTxtFile(f).substring(0,FileOperation.readTxtFile(f).length()-1));
            times++;
            FileOperation.writeTxtFile(String.valueOf(times), f);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
