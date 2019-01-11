/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.controller;

import com.SEF4.Discovery.Stat.Statistics;
import com.SEF4.Discovery.Stat.get_Statistics;
import com.SEF4.Discovery.domain.feedback_items;
import com.SEF4.Discovery.repository.UserRepo;
import com.SEF4.Discovery.util.IncentiveHelper;
import com.SEF4.Discovery.util.JsonDateValueProcessor;
import com.SEF4.Discovery.view_object.Format;
import com.SEF4.Discovery.view_object.Stat;
import com.SEF4.Discovery.view_object.pie_item;
import java.util.ArrayList;
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
public class ChartController {
    @RequestMapping(value = "/timeChart", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject timeChart() throws Exception {
        Format datas=new Format();
        List<String> name=get_Statistics.get_PastDates();
        List<Integer> num=get_Statistics.get_PastViews();
        datas.setName(name);
        datas.setNum(num);
        JSONObject json = JSONObject.fromObject(datas,JsonDateValueProcessor.getJsonConfig());
        return json;
    }
    
    @RequestMapping(value = "/timeChart2", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject timeChart2() throws Exception {
        Format datas=new Format();
        List<Integer> num=get_Statistics.get_Views();
        datas.setNum(num);
        JSONObject json = JSONObject.fromObject(datas,JsonDateValueProcessor.getJsonConfig());
        return json;
    }
    
    @RequestMapping(value = "/timeChart3", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject timeChart3() throws Exception {
        List<String> times=new ArrayList<>();
        for(int i=0;i<24;i++){
            times.add(String.valueOf(i)+"—"+String.valueOf(i+1));
        }
        Format datas=new Format();
        List<Integer> num=get_Statistics.get_requests();
        datas.setName(times);
        datas.setNum(num);
        JSONObject json = JSONObject.fromObject(datas,JsonDateValueProcessor.getJsonConfig());
        return json;
    }
    
    @RequestMapping(value = "/timeChart4", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject timeChart4(@RequestParam("userName")String userName) throws Exception {
        List<String> times=new ArrayList<>();
        for(int i=0;i<24;i++){
            times.add(String.valueOf(i)+"—"+String.valueOf(i+1));
        }
        Format datas=new Format();
        List<Integer> num=get_Statistics.get_user_active_time(userName);
        datas.setName(times);
        datas.setNum(num);
        JSONObject json = JSONObject.fromObject(datas,JsonDateValueProcessor.getJsonConfig());
        return json;
    }
    
    @RequestMapping(value = "/pieChart1", method = RequestMethod.POST)
    @ResponseBody
    public List<JSONObject> pieChart1() throws Exception {
        List<JSONObject> items=new ArrayList<>();
        pie_item item=new pie_item();
        List<Integer> num=get_Statistics.get_userStat();
        item.setName("活跃用户");
        item.setValue(num.get(1));
        JSONObject json = JSONObject.fromObject(item,JsonDateValueProcessor.getJsonConfig());
        items.add(json);
        item.setName("普通用户");
        item.setValue(num.get(2));
        json = JSONObject.fromObject(item,JsonDateValueProcessor.getJsonConfig());
        items.add(json);
        item.setName("不活跃用户");
        item.setValue(num.get(2));
        json = JSONObject.fromObject(item,JsonDateValueProcessor.getJsonConfig());
        items.add(json);
        return items;
    }
    
    @RequestMapping(value = "/pieChart2", method = RequestMethod.POST)
    @ResponseBody
    public List<JSONObject> pieChart2() throws Exception {
        List<JSONObject> items=new ArrayList<>();
        pie_item item=new pie_item();
        List<Integer> num=get_Statistics.get_workerStat();
        item.setName("精英工人");
        item.setValue(num.get(1));
        JSONObject json = JSONObject.fromObject(item,JsonDateValueProcessor.getJsonConfig());
        items.add(json);
        item.setName("一般工人");
        item.setValue(num.get(2));
        json = JSONObject.fromObject(item,JsonDateValueProcessor.getJsonConfig());
        items.add(json);
        return items;
    }
    
    @RequestMapping(value = "/pieChart3", method = RequestMethod.POST)
    @ResponseBody
    public List<JSONObject> pieChart3() throws Exception {
        List<JSONObject> items=new ArrayList<>();
        pie_item item=new pie_item();
        List<Integer> num=get_Statistics.get_sponsorStat();
        item.setName("精英发布者");
        item.setValue(num.get(1));
        JSONObject json = JSONObject.fromObject(item,JsonDateValueProcessor.getJsonConfig());
        items.add(json);
        item.setName("一般发布者");
        item.setValue(num.get(2));
        json = JSONObject.fromObject(item,JsonDateValueProcessor.getJsonConfig());
        items.add(json);
        return items;
    }
    
    @RequestMapping(value = "/Stat", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject reStat() throws Exception {
        Stat stat=new Stat();
        List<Integer> num=get_Statistics.get_userStat();
        stat.setUsers(num.get(0));
        stat.setGoodUsers(num.get(1));
        num=get_Statistics.get_taskStat();
        stat.setTasks(num.get(0));
        stat.setFinishedTasks((int)(num.get(0)*0.6));
        stat.setRunningTasks(num.get(0)-(int)(num.get(0)*0.6));
        JSONObject json = JSONObject.fromObject(stat,JsonDateValueProcessor.getJsonConfig());
        return json;
    }
    
    @RequestMapping(value = "/barChart", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject barChart() throws Exception {
        Format datas=new Format();
        List<Integer> num=get_Statistics.getLabelCount();
        datas.setNum(num);
        List<String> name=get_Statistics.getFavLabel();
        datas.setName(name);
        JSONObject json = JSONObject.fromObject(datas,JsonDateValueProcessor.getJsonConfig());
        return json;
    }
    
    @RequestMapping(value = "/scatter1", method = RequestMethod.POST)
    @ResponseBody
    public List<Double[]> scatter1() throws Exception {
        List<Double> y=get_Statistics.get_TaskSt();
        List<String> labels=get_Statistics.get_TaskLabel();
        List<Double> x=new ArrayList<>();
        String[] label_set={"生活纪实","旅游民俗","历史文物","自然景观","人文景观","商贸财经","IT与通讯","经济万象","文化娱乐","科技教育","体育运动","人物肖像","动物植物","素材创意"};
        for(int i=0;i<labels.size();i++){
            if(labels.get(i).equals("生活纪实"))
                x.add(1.0);
            else if(labels.get(i).equals("旅游民俗"))
                x.add(2.0);
            else if(labels.get(i).equals("历史文物"))
                x.add(3.0);
            else if(labels.get(i).equals("自然景观"))
                x.add(4.0);
            else if(labels.get(i).equals("人文景观"))
                x.add(5.0);
            else if(labels.get(i).equals("商贸财经"))
                x.add(6.0);
            else if(labels.get(i).equals("IT与通讯"))
                x.add(7.0);
            else if(labels.get(i).equals("经济万象"))
                x.add(8.0);
            else if(labels.get(i).equals("文化娱乐"))
                x.add(9.0);
            else if(labels.get(i).equals("科技教育"))
                x.add(10.0);
            else if(labels.get(i).equals("体育运动"))
                x.add(11.0);
            else if(labels.get(i).equals("人物肖像"))
                x.add(12.0);
            else if(labels.get(i).equals("动物植物"))
                x.add(13.0);
            else if(labels.get(i).equals("素材创意"))
                x.add(14.0);
        }
        List<Double[]> re=new ArrayList<>();
        for(int i=0;i<x.size();i++){
            Double[] point={(double)x.get(i),y.get(i)};
            re.add(point);
        }
        return re;
    }
    @RequestMapping(value = "/scatter2", method = RequestMethod.POST)
    @ResponseBody
    public List<Double[]> scatter2() throws Exception {
        List<Double> y=get_Statistics.get_TaskSt();
        List<Integer> x=get_Statistics.get_PicNum();
        List<Double[]> re=new ArrayList<>();
        for(int i=0;i<x.size();i++){
            Double[] point={(double)x.get(i),y.get(i)};
            re.add(point);
        }
        return re;
    }
    
    @RequestMapping(value = "/scatter3", method = RequestMethod.POST)
    @ResponseBody
    public List<Double[]> scatter3() throws Exception {
        List<Double> y=get_Statistics.get_TaskSt();
        List<Integer> x=get_Statistics.get_TaskBounty();
        List<Double[]> re=new ArrayList<>();
        for(int i=0;i<x.size();i++){
            Double[] point={(double)x.get(i),y.get(i)};
            re.add(point);
        }
        return re;
    }
    
    @RequestMapping(value = "/scatter4", method = RequestMethod.POST)
    @ResponseBody
    public List<Double[]> scatter4() throws Exception {
        List<Double> y=get_Statistics.get_UserFreeDegree();
        List<Double> x=new ArrayList<>();
        List<String> labels=get_Statistics.get_UserFavLabel();
        String[] label_set={"生活纪实","旅游民俗","历史文物","自然景观","人文景观","商贸财经","IT与通讯","经济万象","文化娱乐","科技教育","体育运动","人物肖像","动物植物","素材创意"};
        for(int i=0;i<labels.size();i++){
            if(labels.get(i).equals("生活纪实"))
                x.add(1.0);
            else if(labels.get(i).equals("旅游民俗"))
                x.add(2.0);
            else if(labels.get(i).equals("历史文物"))
                x.add(3.0);
            else if(labels.get(i).equals("自然景观"))
                x.add(4.0);
            else if(labels.get(i).equals("人文景观"))
                x.add(5.0);
            else if(labels.get(i).equals("商贸财经"))
                x.add(6.0);
            else if(labels.get(i).equals("IT与通讯"))
                x.add(7.0);
            else if(labels.get(i).equals("经济万象"))
                x.add(8.0);
            else if(labels.get(i).equals("文化娱乐"))
                x.add(9.0);
            else if(labels.get(i).equals("科技教育"))
                x.add(10.0);
            else if(labels.get(i).equals("体育运动"))
                x.add(11.0);
            else if(labels.get(i).equals("人物肖像"))
                x.add(12.0);
            else if(labels.get(i).equals("动物植物"))
                x.add(13.0);
            else if(labels.get(i).equals("素材创意"))
                x.add(14.0);
        }
        List<Double[]> re=new ArrayList<>();
        for(int i=0;i<x.size();i++){
            Double[] point={(double)x.get(i),y.get(i)};
            re.add(point);
        }
        return re;
    }
}
