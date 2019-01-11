/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.controller;

import com.SEF4.Discovery.PO_to_VO.Converter;
import com.SEF4.Discovery.Stat.Statistics;
import com.SEF4.Discovery.domain.User;
import com.SEF4.Discovery.repository.FeedBackRepo;
import com.SEF4.Discovery.repository.UserRepo;
import com.SEF4.Discovery.service.UserManage;
import com.SEF4.Discovery.util.IncentiveHelper;
import com.SEF4.Discovery.util.JsonDateValueProcessor;
import com.SEF4.Discovery.view_object.personal;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
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
public class UserController { 
    public static String ResStr="{\"type\":\"JSON\",\"mes\":\"successful!\"}"; 
  
    @GetMapping(value = {"/","/login"})  
    String login() {  
        
        return "index.html";
    }
    @RequestMapping(value = {"/","/login"},method = RequestMethod.POST)
    @ResponseBody
    public void loginCheck(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception{
        Statistics.save_request_time();
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        if(username.equals("admin")&&password.equals("admin")){
            response.sendRedirect("/admin");
        }
        else if(UserManage.UserConfirm(username, password)){
            Statistics.save_Views(new Date());
            response.sendRedirect("/entry");
        }else{
            response.sendRedirect("/");
        }
    }
    
    @GetMapping(value = "/register")  
    String register() {  
        
        return "register.html";
    }
    
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public void registerSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception{
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String email=request.getParameter("email");
        /*System.out.println(username);
        System.out.println(password);
        System.out.println(email);*/
        if(UserManage.UserRegister(username, password, email))
            response.sendRedirect("/");
        else
            response.sendRedirect("/register");
    }
    
    @GetMapping(value = "/entry")  
    ModelAndView entry() {  
       ModelAndView mv=new ModelAndView();
       mv.setViewName("entry.html");
       return mv;
    }
    
    @GetMapping(value = "/admin")  
    ModelAndView admin() {  
       ModelAndView mv=new ModelAndView();
       mv.setViewName("admin.html");
       return mv;
    }
    
    @RequestMapping(value = "/personalInfo",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject personlInfo(@RequestParam("userName")String userName) throws IOException, Exception{
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        User user=UserRepo.findUser(userName);
        personal Info=Converter.User_personal(user);
        JSONObject json = JSONObject.fromObject(Info,JsonDateValueProcessor.getJsonConfig());
        return json;
    }
    
    @RequestMapping(value = "/task_accept",method = RequestMethod.POST)
    @ResponseBody
    public String acceptTask(@RequestParam("userName")String userName,@RequestParam("taskId")String taskId) throws IOException, Exception{
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        taskId=URLDecoder.decode(taskId,"utf-8");
        taskId=taskId.substring(1,taskId.length()-1);
        boolean result=UserManage.acceptTask(userName, taskId);
        FeedBackRepo.createFeedBack(taskId, userName);
        if(result)
            return ResStr;
        else
            return "Can't accept more!";
    }

    @RequestMapping(value = "/cashIn",method = RequestMethod.POST)
    @ResponseBody
    public String cashIn(@RequestParam("userName")String userName,@RequestParam("cash")Integer cash) throws IOException, Exception{
        Statistics.save_request_time();
        Statistics.save_user_active_time(userName);
        User user=UserRepo.findUser(userName);
        user.setBalance(user.getBalance()+cash);
        boolean result=UserRepo.updateUser(user);
        if(result)
            return ResStr;
        else
            return "Unexpected Error!";
    }
    
    @RequestMapping(value = "/cashOut",method = RequestMethod.POST)
    @ResponseBody
    public String cashOut(@RequestParam("userName")String userName,@RequestParam("cash")Integer cash) throws IOException, Exception{
       Statistics.save_request_time();
       Statistics.save_user_active_time(userName);
       return  IncentiveHelper.withdraw(userName, cash);
    }
    
    @RequestMapping(value = "/credit",method = RequestMethod.POST)
    @ResponseBody
    public Integer getBP(@RequestParam("userName")String userName) throws IOException, Exception{
       Statistics.save_request_time();
       Statistics.save_user_active_time(userName);
       return  UserRepo.findUser(userName).getBonus_point();
    }
    
     @RequestMapping(value = "/exchange",method = RequestMethod.POST)
    @ResponseBody
    public String gift_exchange(@RequestParam("userName")String userName,@RequestParam("BP")Integer BP,@RequestParam("giftID")Integer giftID) throws IOException, Exception{
       Statistics.save_request_time();
       Statistics.save_user_active_time(userName);
       return  IncentiveHelper.BP_exchange(userName, BP, giftID);
    }
}  