/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.PO_to_VO;

import com.SEF4.Discovery.domain.Task;
import com.SEF4.Discovery.domain.User;
import com.SEF4.Discovery.service.InfoHandler;
import com.SEF4.Discovery.util.JsonDateConvert;
import com.SEF4.Discovery.view_object.Task_view;
import com.SEF4.Discovery.view_object.Task_view_more;
import com.SEF4.Discovery.view_object.personal;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author Kevin
 */
public class Converter {
    public static personal User_personal(User user) throws FileNotFoundException, Exception{
        personal view=new personal();
        view.setNickname(user.getUsername());
        view.setId(user.getUserID());
        view.setNumberOfTask(user.getTask_fin());
        view.setFavourite("");
        long t1 = user.getRegist_date().getTime();
        long t2 = new Date().getTime();
        long millis = t2 - t1;
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        view.setDays((int)days);
        view.setCash(Double.toString(user.getBalance()));
        if(user.getTask_list().size()==0)
            view.setRecent("No Task Yet...");
        else
            view.setRecent(user.getTask_list().get(user.getTask_list().size()-1));
        view.setLvl1(user.getWorker_lv());
        Integer maxExp1=InfoHandler.getMaxExp(user.getWorker_lv());
        view.setExp1(String.valueOf(user.getWorker_exp())+"/"+String.valueOf(maxExp1));
        view.setHonor1(InfoHandler.getWorkerTitle(user.getWorker_title_lv()));
        view.setLvl2(user.getSponsor_lv());
        Integer maxExp2=InfoHandler.getMaxExp(user.getSponsor_lv());
        view.setExp2(String.valueOf(user.getSponsor_exp())+"/"+String.valueOf(maxExp2));
        view.setHonor2(InfoHandler.getSponsorTitle(user.getSponsor_title_lv()));
        Integer releaserNum=InfoHandler.getTaskRelease(user.getUserID()).size();
        view.setReleaserNum(releaserNum);
        List<Integer> workerNum=new ArrayList<Integer>();
        workerNum.add(user.getFin_list().size());
        workerNum.add(user.getFin_low());
        workerNum.add(user.getFin_intermediate());
        workerNum.add(user.getFin_advanced());
        workerNum.add(user.getRemain_task_num());
        view.setWorkerNum(workerNum);
        view.setReputationNum(user.getCredit());
        view.setRank(InfoHandler.getRank(user.getUserID()));
        return view;
    } 
    public static Task_view Task_view(Task task) throws FileNotFoundException{
        Task_view view=new Task_view();
        view.setName(task.getName());
        view.setId(task.getTaskID());
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        String subPath=task.getImagesUrl();
        File dir = new File(path.getPath(),subPath);
        String imageName;
        if(dir.list().length==0)
            imageName="";
        else
            imageName=dir.list()[0];
        String url="/Path/"+task.getImagesUrl()+"/"+imageName;
        view.setPreloadPicURL(url);
        view.setCash(Double.toString(task.getBounty()));
        view.setDeadline(JsonDateConvert.getTimeStr(task.getDeadline()));
        view.setNote(task.getDescription());
        return view;
    }
    
    public static Task_view_more Task_view_more(Task task) throws FileNotFoundException{
        Task_view_more view=new Task_view_more();
        view.setName(task.getName());
        view.setId(task.getTaskID());
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        String subPath=task.getImagesUrl();
        File dir = new File(path.getPath(),subPath);
        List<String> preloadPicURLs=new ArrayList<String>();
        for(int i=0;i<dir.list().length&&i<5;i++){
            String imageName=dir.list()[i];
            String url="/Path/"+task.getImagesUrl()+"/"+imageName;
            preloadPicURLs.add(url);
        }
        view.setPreloadPicURLs(preloadPicURLs);
        view.setCash(Double.toString(task.getBounty()));
        view.setDeadline(JsonDateConvert.getTimeStr(task.getDeadline()));
        view.setNote(task.getDescription());
        view.setSponsor(view.getId().substring(0,view.getId().indexOf(":")));
        view.setImageNums(dir.list().length);
        return view;
    }
}
