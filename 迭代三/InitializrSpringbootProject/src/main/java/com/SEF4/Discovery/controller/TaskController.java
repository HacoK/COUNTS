/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.controller;

import com.SEF4.Discovery.PO_to_VO.Converter;
import com.SEF4.Discovery.Stat.Statistics;
import com.SEF4.Discovery.domain.Task;
import com.SEF4.Discovery.domain.User;
import com.SEF4.Discovery.repository.TaskRepo;
import com.SEF4.Discovery.service.TaskManage;
import com.SEF4.Discovery.service.UserManage;
import com.SEF4.Discovery.util.JsonDateConvert;
import com.SEF4.Discovery.util.JsonDateValueProcessor;
import com.SEF4.Discovery.util.RandomNumStr;
import com.SEF4.Discovery.view_object.Task_view_more;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Kevin
 */
@Controller  
@EnableAutoConfiguration  
public class TaskController { 
    public static String ResStr="{\"type\":\"JSON\",\"mes\":\"successful!\"}"; 

    @RequestMapping(value = "/release", method = RequestMethod.POST)
    @ResponseBody
    public String release(HttpServletRequest request, HttpServletResponse response) {
        try {
            Statistics.save_request_time();
            String title = request.getParameter("title");
            String cash = request.getParameter("cash");
            String deadline = request.getParameter("deadline");
            String groups = request.getParameter("groups");
            String urgent = request.getParameter("urgent");
            String remarks = request.getParameter("remarks");
            String userName = request.getParameter("userName");
            String label = request.getParameter("label");
            if(label.equals("其他标签"))
                label=request.getParameter("labelE");
            String result=TaskManage.TaskRelease(title, cash, deadline, groups, urgent, remarks, userName,label);
            return result;
        } catch (Exception e) {
            return "0";
        }
        
    }
    /**    
     * 文件上传具体实现方法;    
     *     
     * @param file    
     * @return    
     */      
    @RequestMapping("/upload")      
    @ResponseBody      
    public String handleFileUpload( @RequestParam("userName")String userName, @RequestParam("projectName")String projectName,@RequestParam("file") MultipartFile file){  
        if (!file.isEmpty()) {      
            try {
                Statistics.save_request_time();
                Statistics.save_user_active_time(userName);
                File path = new File(ResourceUtils.getURL("classpath:").getPath());
                if (!path.exists()) {
                    path = new File("");
                }
                String subPath="dataFile/Task/"+userName+"/"+projectName+"/Images";
                File dir = new File(path.getPath(),subPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String filename = file.getOriginalFilename();
                File f = new File(dir, filename);
                if (!f.exists()) {
                    try {
                        f.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f));      
                out.write(file.getBytes());      
                out.flush();      
                out.close();
                TaskManage.sumUpdate(userName+":"+projectName);
                return ResStr;
            } catch (FileNotFoundException e) {      
                e.printStackTrace();      
                return "上传失败," + e.getMessage();      
            } catch (IOException e) {      
                e.printStackTrace();      
                return "上传失败," + e.getMessage();      
            }      
      
        } else {      
            return "上传失败，因为文件是空的.";      
        }      
    } 
    
    @RequestMapping(value = "/taskInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject detailedTask(@RequestParam("taskID")String taskID) throws Exception {
            Statistics.save_request_time();
            Task task=TaskRepo.findTask(taskID);
            Task_view_more view=Converter.Task_view_more(task);
            JSONObject json = JSONObject.fromObject(view,JsonDateValueProcessor.getJsonConfig());
            return json;
    }
    
    @RequestMapping(value = "/imagePath", method = RequestMethod.POST)
    @ResponseBody
    public String getImagePath(@RequestParam("taskID")String taskID,@RequestParam("index")Integer index) throws Exception {
            Statistics.save_request_time();
            Task task = TaskRepo.findTask(taskID);
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            String subPath = task.getImagesUrl();
            File dir = new File(path.getPath(), subPath);
            List<String> imagePaths = new ArrayList<String>();
            if(index>dir.list().length)
                return null;
            String imageName = dir.list()[index-1];
            String url = "/Path/" + task.getImagesUrl() + "/" + imageName;
            return url;
    }
    
    @RequestMapping(value = "/imagePathR", method = RequestMethod.POST)
    @ResponseBody
    public String getImagePathR(@RequestParam("taskID")String taskID,@RequestParam("index")Integer index) throws Exception {
            Statistics.save_request_time();
            Task task = TaskRepo.findTask(taskID);
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            String subPath = task.getImagesUrl();
            File dir = new File(path.getPath(), subPath);
            List<String> imagePaths = new ArrayList<String>();
            if(index>dir.list().length)
                return null;
            List<Integer> randList = task.getRandList();
            if (randList==null||randList.isEmpty()) {
                randList = RandomNumStr.randomSort(dir.list().length);
                task.setRandList(randList);
                TaskRepo.saveTask(task);
            }
            String imageName = dir.list()[randList.get(index-1)-1];
            String url = "/Path/" + task.getImagesUrl() + "/" + imageName;
            return url;
    }
}  