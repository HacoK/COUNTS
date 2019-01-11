/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.service;

import com.SEF4.Discovery.domain.Tag;
import com.SEF4.Discovery.domain.TaskFeedBack;
import com.SEF4.Discovery.repository.FeedBackRepo;
import com.SEF4.Discovery.repository.TagRepo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author Kevin
 */
public class TagHelper {
    public static boolean saveGrobalTag(String taskID,Integer imageID,String grobalTag,String userName) throws FileNotFoundException, Exception{
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        String subPath="dataFile/Task/"+taskID.substring(0,taskID.indexOf(":"))+"/"+taskID.substring(taskID.indexOf(":")+1)+"/Tags/"+userName;
        File dir = new File(path.getPath(),subPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename = String.valueOf(imageID);
        File f = new File(dir, filename);
        Tag tag=TagRepo.findTag(taskID, imageID, userName);
        if(tag==null){
            tag=new Tag();
            tag.setLines(new ArrayList<String>());
            tag.setAreaTags(new ArrayList<String>());
        }
        tag.setGrobalTag(grobalTag);
        boolean result=TagRepo.updateTag(f, tag);
        return result;
    }
    
    public static boolean saveAreaTag(String taskID,Integer imageID,String Line,String AreaTag,String userName) throws FileNotFoundException, Exception{
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        String subPath="dataFile/Task/"+taskID.substring(0,taskID.indexOf(":"))+"/"+taskID.substring(taskID.indexOf(":")+1)+"/Tags/"+userName;
        File dir = new File(path.getPath(),subPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        String filename = String.valueOf(imageID);
        File f = new File(dir, filename);
        Tag tag=TagRepo.findTag(taskID, imageID, userName);
        if(tag==null){
            tag=new Tag();
            tag.setGrobalTag("");
            tag.setLines(new ArrayList<String>());
            tag.setAreaTags(new ArrayList<String>());
        }
        List<String> Lines=tag.getLines();
        List<String> AreaTags=tag.getAreaTags();
        Lines.add(Line);
        AreaTags.add(AreaTag);
        tag.setLines(Lines);
        tag.setAreaTags(AreaTags);
        boolean result=TagRepo.updateTag(f, tag);
        return result;
    }
    
    public static boolean clearTags(String taskID,Integer imageID,String userName) throws FileNotFoundException, Exception{
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        String subPath="dataFile/Task/"+taskID.substring(0,taskID.indexOf(":"))+"/"+taskID.substring(taskID.indexOf(":")+1)+"/Tags/"+userName;
        File dir = new File(path.getPath(),subPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        String filename = String.valueOf(imageID);
        File f = new File(dir, filename);
        Tag tag=TagRepo.findTag(taskID, imageID, userName);
        if(tag==null){
            tag=new Tag();
            tag.setGrobalTag("");
            tag.setLines(new ArrayList<String>());
            tag.setAreaTags(new ArrayList<String>());
        }
        tag.setGrobalTag("");
        tag.setLines(new ArrayList<String>());
        tag.setAreaTags(new ArrayList<String>());
        TaskFeedBack feedback=FeedBackRepo.findFeedBack(taskID, userName);
        if(feedback==null){
            FeedBackRepo.createFeedBack(taskID,userName);
            feedback=FeedBackRepo.findFeedBack(taskID, userName);
        }
        List<Integer> fin_imgs=feedback.getFin_imgs();
        Integer fin_img=feedback.getFin_img();
        if(fin_imgs.contains(imageID)){
            fin_imgs.remove(imageID);
            fin_img--;
            feedback.setFin_img(fin_img);
            feedback.setFin_imgs(fin_imgs);
        }
        if(feedback.getFin_state()==0&&fin_img<feedback.getTotal_img()){
            feedback.setFin_state(-1);
        }
        boolean result1=TagRepo.updateTag(f, tag);
        boolean result2=FeedBackRepo.updateFeedBack(feedback);
        boolean result=result1&&result2;
        return result;
    }
}
