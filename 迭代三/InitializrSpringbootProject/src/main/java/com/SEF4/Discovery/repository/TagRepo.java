/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.repository;

import com.SEF4.Discovery.domain.Tag;
import com.SEF4.Discovery.util.FileOperation;
import com.SEF4.Discovery.util.JsonDateValueProcessor;
import java.io.File;
import java.io.FileNotFoundException;
import net.sf.json.JSONObject;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author Kevin
 */
public class TagRepo {
    public static boolean updateTag(File f,Tag tag) throws FileNotFoundException {
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            JSONObject json = JSONObject.fromObject(tag,JsonDateValueProcessor.getJsonConfig());
            FileOperation.writeTxtFile(json.toString(), f);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static Tag findTag(String taskID,Integer imageID,String userName) throws FileNotFoundException, Exception {
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
        if(!f.exists()){
            return null;
        }
        String jsonStr = FileOperation.readTxtFile(f);
        JSONObject jsonObject = JSONObject.fromObject(jsonStr,JsonDateValueProcessor.getJsonConfig());
        Tag tag = (Tag) JSONObject.toBean(jsonObject, Tag.class);
        return tag;
    }
}
