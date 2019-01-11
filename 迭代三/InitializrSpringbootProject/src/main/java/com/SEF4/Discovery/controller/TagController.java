/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.controller;

import com.SEF4.Discovery.Stat.Statistics;
import com.SEF4.Discovery.domain.Tag;
import com.SEF4.Discovery.domain.Task;
import com.SEF4.Discovery.domain.TaskFeedBack;
import com.SEF4.Discovery.repository.FeedBackRepo;
import com.SEF4.Discovery.repository.TagRepo;
import com.SEF4.Discovery.repository.TaskRepo;
import com.SEF4.Discovery.repository.UserRepo;
import com.SEF4.Discovery.service.FeedBackHandler;
import com.SEF4.Discovery.service.TagHelper;
import com.SEF4.Discovery.util.JsonDateValueProcessor;
import com.SEF4.Discovery.util.RandomNumStr;
import java.net.URLDecoder;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Kevin
 */
@Controller  
@EnableAutoConfiguration 
public class TagController {
    public static String ResStr="{\"type\":\"JSON\",\"mes\":\"successful!\"}"; 

    @RequestMapping(value = "/mainInfo", method = RequestMethod.POST)
    @ResponseBody
    public String saveGrobalTag(@RequestParam("taskID")String taskID,@RequestParam("imageID")Integer imageID,@RequestParam("mainInfo")String grobalTag,@RequestParam("userName")String userName) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        boolean result=TagHelper.saveGrobalTag(taskID, imageID, grobalTag, userName);   
        if(result)
            return ResStr;
        else
            return "Fail!Unknown Reason...";
    }
    
    @RequestMapping(value = "/areaInfo", method = RequestMethod.POST)
    @ResponseBody
    public String saveGrobalTag(@RequestParam("taskID")String taskID,@RequestParam("imageID")Integer imageID,@RequestParam("Info")String areaTag,@RequestParam("string")String line,@RequestParam("userName")String userName) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        taskID=URLDecoder.decode(taskID,"utf-8");
        line=URLDecoder.decode(line,"utf-8");
        areaTag=URLDecoder.decode(areaTag,"utf-8");
        boolean result=TagHelper.saveAreaTag(taskID, imageID, line, areaTag, userName);
//        System.out.println("taskID:"+taskID);
//        System.out.println("imageID:"+String.valueOf(imageID));
//        System.out.println("Info:"+areaTag);
//        System.out.println("string:"+line);
//        System.out.println("userName:"+userName);
        if(result)
            return ResStr;
        else
            return "Fail!Unknown Reason...";
    }
    
    @RequestMapping(value = "/delInfo", method = RequestMethod.POST)
    @ResponseBody
    public String clearTags(@RequestParam("taskID")String taskID,@RequestParam("imageID")Integer imageID,@RequestParam("userName")String userName) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        boolean result=TagHelper.clearTags(taskID, imageID, userName);
        if(result)
            return ResStr;
        else
            return "Fail!Unknown Reason...";
    }
    
    @RequestMapping(value = "/Push", method = RequestMethod.POST)
    @ResponseBody
    public String pushImg(@RequestParam("taskID")String taskID,@RequestParam("imageID")Integer imageID,@RequestParam("userName")String userName) throws Exception {
//        System.out.println("taskID:" + taskID);
//        System.out.println("imageID:" + String.valueOf(imageID));
//        System.out.println("userName:" + userName);
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        String result=FeedBackHandler.pushImg(taskID, imageID, userName);
        return result;
    }
    
    @RequestMapping(value = "/readInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject displayTags(@RequestParam("taskID")String taskID,@RequestParam("imageID")Integer imageID,@RequestParam("userName")String userName) throws Exception {
//        System.out.println("taskID:" + taskID);
//        System.out.println("imageID:" + String.valueOf(imageID));
//        System.out.println("userName:" + userName);
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        Tag tag=TagRepo.findTag(taskID, imageID, userName);
        JSONObject json = JSONObject.fromObject(tag,JsonDateValueProcessor.getJsonConfig());
        return json;
    }

    @RequestMapping(value = "/readInfoR", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject displayTagsR(@RequestParam("taskID")String taskID,@RequestParam("imageID")Integer imageID,@RequestParam("userName")String userName) throws Exception {
//        System.out.println("taskID:" + taskID);
//        System.out.println("imageID:" + String.valueOf(imageID));
//        System.out.println("userName:" + userName);
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        Task task=TaskRepo.findTask(taskID);
        List<Integer> randList=task.getRandList();
        if(randList==null||randList.isEmpty()){
            randList=RandomNumStr.randomSort(task.getSum()/task.getWorker_num());
            task.setRandList(randList);
            TaskRepo.saveTask(task);
        }
        Tag tag=TagRepo.findTag(taskID, randList.get(imageID-1), userName);
        JSONObject json = JSONObject.fromObject(tag,JsonDateValueProcessor.getJsonConfig());
        return json;
    }
    
    @RequestMapping(value = "/taskProgress", method = RequestMethod.POST)
    @ResponseBody
    public Integer getProgress(@RequestParam("taskID")String taskID,@RequestParam("userName")String userName) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        TaskFeedBack feedback=FeedBackRepo.findFeedBack(taskID, userName);
        Integer progress=100*feedback.getFin_img()/feedback.getTotal_img();
        return progress;
    }
}
