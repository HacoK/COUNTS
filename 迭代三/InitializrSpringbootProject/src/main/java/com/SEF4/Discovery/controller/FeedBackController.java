/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.controller;

import com.SEF4.Discovery.Stat.Statistics;
import com.SEF4.Discovery.domain.Task;
import com.SEF4.Discovery.domain.TaskFeedBack;
import com.SEF4.Discovery.domain.feedback_items;
import com.SEF4.Discovery.repository.FeedBackRepo;
import com.SEF4.Discovery.repository.TaskRepo;
import com.SEF4.Discovery.repository.UserRepo;
import com.SEF4.Discovery.service.FeedBackHandler;
import com.SEF4.Discovery.service.InfoHandler;
import com.SEF4.Discovery.util.IncentiveHelper;
import com.SEF4.Discovery.util.JsonDateValueProcessor;
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
public class FeedBackController {
    @RequestMapping(value = "/taskInfo_release", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject get_taskInfo_release(@RequestParam("taskID")String taskID,@RequestParam("userName")String userName) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        feedback_items feedbacks=IncentiveHelper.getFeedBacks(userName, taskID);
        JSONObject json = JSONObject.fromObject(feedbacks,JsonDateValueProcessor.getJsonConfig());
        return json;
    }
    
    @RequestMapping(value = "/taskProgress_release", method = RequestMethod.POST)
    @ResponseBody
    public String get_taskProgress_release(@RequestParam("taskID")String taskID,@RequestParam("userName")String userName) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        List<String> involved=InfoHandler.getInvolved(taskID);
        Integer fin_num=0;
        for(int i=0;i<involved.size();i++){
            String userID=involved.get(i);
            TaskFeedBack feedback=FeedBackRepo.findFeedBack(taskID, userID);
            fin_num+=feedback.getFin_img();
        }
        Task task=TaskRepo.findTask(taskID);
        Integer sum=task.getSum();
        Integer process=100*fin_num/sum;
        return String.valueOf(process);
    }
    @RequestMapping(value = "/extraBonus", method = RequestMethod.POST)
    @ResponseBody
    public String BP_Reward(@RequestParam("taskID")String taskID,@RequestParam("workerId")String workerId,@RequestParam("Bonus")Integer Bonus) throws Exception {
        Statistics.save_request_time();
        String result=FeedBackHandler.BP_Reward(taskID, Bonus, workerId);
        return result;
    }
    
    @RequestMapping(value = "/rank", method = RequestMethod.POST)
    @ResponseBody
    public String setSatisfaction(@RequestParam("taskID")String taskID,@RequestParam("workerId")String workerId,@RequestParam("rank")Integer rank) throws Exception {
        Statistics.save_request_time();
        String result=FeedBackHandler.setSatisfaction(taskID, rank, workerId);
        return result;
    }
    
    @RequestMapping(value = "/dailyMark", method = RequestMethod.POST)
    @ResponseBody
    public boolean sign_in(@RequestParam("userName")String userName) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        boolean result=IncentiveHelper.sign_in(UserRepo.findUser(userName));
        return result;
    }
}
