/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.controller;

import com.SEF4.Discovery.PO_to_VO.Converter;
import static com.SEF4.Discovery.PO_to_VO.Converter.Task_view_more;
import com.SEF4.Discovery.Stat.Statistics;
import com.SEF4.Discovery.domain.Task;
import com.SEF4.Discovery.domain.User;
import com.SEF4.Discovery.repository.TaskRepo;
import com.SEF4.Discovery.repository.UserRepo;
import com.SEF4.Discovery.service.InfoHandler;
import com.SEF4.Discovery.service.TaskManage;
import com.SEF4.Discovery.util.JsonDateValueProcessor;
import com.SEF4.Discovery.view_object.Task_view;
import com.SEF4.Discovery.view_object.Task_view_more;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class UserTaskLinkController {
    public static String ResStr="{\"type\":\"JSON\",\"mes\":\"successful!\"}"; 

    @RequestMapping(value = "/allTaskPage", method = RequestMethod.POST)
    @ResponseBody
    public Integer allTaskPage(@RequestParam("userName")String userName) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        List<String> taskList=TaskManage.discoverTask(userName);
        int Pages=taskList.size()/8;
        if(taskList.size()%8!=0)
            Pages++;
        return Pages;
    }
    
    @RequestMapping(value = "/allTask", method = RequestMethod.POST)
    @ResponseBody
    public List<JSONObject> discoverTasks(@RequestParam("userName")String userName,@RequestParam("page")Integer page) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        List<JSONObject> discovery=new ArrayList<JSONObject>();
        List<String> taskList=TaskManage.discoverTask(userName);
        if(taskList.size()==0)
            return null;
        int index=(page-1)*8;
        for(int i=0;i<8;i++){
            Task task=TaskRepo.findTask(taskList.get(index));
            Task_view view=Converter.Task_view(task);
            JSONObject json = JSONObject.fromObject(view,JsonDateValueProcessor.getJsonConfig());
            discovery.add(json);
            index++;
            if(index>=taskList.size())
                break;
        }
        
        return discovery;
    }
    
    @RequestMapping(value = "/userTaskPage", method = RequestMethod.POST)
    @ResponseBody
    public Integer userTaskPage(@RequestParam("userName")String userName) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        List<String> taskList=InfoHandler.getProc(userName);
        int Pages=taskList.size()/8;
        if(taskList.size()%8!=0)
            Pages++;
        return Pages;
    }
    
    @RequestMapping(value = "/userTaskNum", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getuserTaskNum(@RequestParam("userName")String userName) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);

        String MesStr="{\"type\":\"JSON\",\"proc\":\"proc_num\",\"release\":\"release_num\"}"; 
        String proc_num=String.valueOf(InfoHandler.getProc(userName).size());
        String release_num;
        if(InfoHandler.getTaskRelease(userName)!=null){
            release_num=String.valueOf(InfoHandler.getTaskRelease(userName).size());
        }
        else{
            release_num="0";
        }
        MesStr=MesStr.replace("proc_num", proc_num);
        MesStr=MesStr.replace("release_num", release_num);
        JSONObject json=JSONObject.fromObject(MesStr);
        return json;
    }
    
    @RequestMapping(value = "/userTask", method = RequestMethod.POST)
    @ResponseBody
    public List<JSONObject> getProcTasks(@RequestParam("userName")String userName,@RequestParam("page")Integer page) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        List<JSONObject> procTasks=new ArrayList<JSONObject>();
        List<String> taskList=InfoHandler.getProc(userName);
        int index=(page-1)*8;
        if(taskList.isEmpty())
            return null;
        for(int i=0;i<8;i++){
            Task task=TaskRepo.findTask(taskList.get(index));
            Task_view view=Converter.Task_view(task);
            JSONObject json = JSONObject.fromObject(view,JsonDateValueProcessor.getJsonConfig());
            procTasks.add(json);
            index++;
            if(index>=taskList.size())
                break;
        }
        return procTasks;
    }
    
    @RequestMapping(value = "/releaseTaskPage", method = RequestMethod.POST)
    @ResponseBody
    public Integer releasedTaskPage(@RequestParam("userName")String userName) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        List<String> taskList=InfoHandler.getTaskRelease(userName);
        int Pages=taskList.size()/8;
        if(taskList.size()%8!=0)
            Pages++;
        return Pages;
    }
    
    @RequestMapping(value = "/releaseTask", method = RequestMethod.POST)
    @ResponseBody
    public List<JSONObject> releasedTasks(@RequestParam("userName")String userName,@RequestParam("page")Integer page) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        List<JSONObject> discovery=new ArrayList<JSONObject>();
        List<String> taskList=InfoHandler.getTaskRelease(userName);
        if(taskList.size()==0)
            return null;
        int index=(page-1)*8;
        for(int i=0;i<8;i++){
            Task task=TaskRepo.findTask(taskList.get(index));
            Task_view view=Converter.Task_view(task);
            JSONObject json = JSONObject.fromObject(view,JsonDateValueProcessor.getJsonConfig());
            discovery.add(json);
            index++;
            if(index>=taskList.size())
                break;
        }
        
        return discovery;
    }
    
    @RequestMapping(value = "/finTaskPage", method = RequestMethod.POST)
    @ResponseBody
    public Integer finTaskPage(@RequestParam("userName")String userName) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        User user=UserRepo.findUser(userName);
        List<String> taskList=user.getFin_list();
        int Pages=taskList.size()/8;
        if(taskList.size()%8!=0)
            Pages++;
        return Pages;
    }
    
    @RequestMapping(value = "/finTaskNum", method = RequestMethod.POST)
    @ResponseBody
    public Integer finTaskNum(@RequestParam("userName")String userName) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        User user=UserRepo.findUser(userName);
        List<String> taskList=user.getFin_list();
        return taskList.size();
    }
    
    @RequestMapping(value = "/finTask", method = RequestMethod.POST)
    @ResponseBody
    public List<JSONObject> finTasks(@RequestParam("userName")String userName,@RequestParam("page")Integer page) throws Exception {
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        User user=UserRepo.findUser(userName);
        List<String> taskList=user.getFin_list();
        List<JSONObject> discovery=new ArrayList<JSONObject>();
        if(taskList.size()==0)
            return null;
        int index=(page-1)*8;
        for(int i=0;i<8;i++){
            Task task=TaskRepo.findTask(taskList.get(index));
            Task_view view=Converter.Task_view(task);
            JSONObject json = JSONObject.fromObject(view,JsonDateValueProcessor.getJsonConfig());
            discovery.add(json);
            index++;
            if(index>=taskList.size())
                break;
        }
        
        return discovery;
    }
}
