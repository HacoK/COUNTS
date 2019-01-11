/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.DateFactory;

import com.SEF4.Discovery.domain.Task;
import com.SEF4.Discovery.domain.User;
import com.SEF4.Discovery.repository.TaskRepo;
import com.SEF4.Discovery.repository.UserRepo;
import com.SEF4.Discovery.util.JsonDateConvert;
import com.SEF4.Discovery.util.RandomNumStr;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Kevin
 */
public class TaskFactory {
    public static int ranWithNum(double[] input){
	int len = input.length;
	double all = sum(input,len);
	double temp = Math.random();
        double sum_bf=0;
        double sum_temp=input[0]/all;
	for(int i=0;i<len;i++){
	    if(temp<sum_temp&&temp>=sum_bf){return i+1;}
            else{
                sum_bf=sum_temp;
                sum_temp+=(input[i+1]/all);
            }
	}
	return len;
    }
    public static double sum(double[] input,int t){
	double result = 0;
	for(int i=0;i<t;i++){result+=input[i];}
	return result;
    }
    public static void main(String[] args) throws Exception{
        int ran;
        Random rand = new Random(); 
        for(int i=0;i<400;i++){
        Integer task_lv=0;
        Task task = new Task();
        task.setTask_lv(task_lv);
        String title=RandomNumStr.get5Rand();
        task.setName(title);
        List<User> users=UserRepo.getUsers();
        ran=rand.nextInt(406);
        task.setTaskID(users.get(ran).getUserID()+ ":" + title);
        task.setWorker_num(5);
        task.setDescription("");
        task.setFin(0);
        task.setUrgent(false);
        task.setAcp_fin_flag(true);
        task.setTask_fin_flag(true);
        double[] parameter1={1,1.1,1.21,1.37,1.55,1.71};
        int index=ranWithNum(parameter1);
        index=(int) (index*8+8*Math.random()-8);
        Date past=JsonDateConvert.StringToDate("2018-05-01");
        Calendar c1 = Calendar.getInstance();
        c1.setTime(past);
        c1.add(Calendar.DAY_OF_MONTH,index );// 今天+1天  
        Date release_day= c1.getTime(); 
        task.setRelease_time(release_day);
        
        double[] parameter2={10,3,1,0.5};
        int duration=1;
        index=ranWithNum(parameter2);
        switch(index){
            case 1:
                duration=(int) (5*Math.random());
                break;
            case 2:
                duration=(int) (5+2*Math.random());
                break;
            case 3:
                duration=(int) (7+3*Math.random());
                break;
            case 4:
                duration=(int) (10+20*Math.random());
                break;
        }  
        Calendar c2 = Calendar.getInstance();
        c2.setTime(release_day);
        c2.add(Calendar.DAY_OF_MONTH, duration);// 今天+1天  
        Date deadline= c2.getTime(); 
        task.setDeadline(deadline);
        String[] labels={"生活纪实","旅游民俗","历史文物","自然景观","人文景观","商贸财经","IT与通讯","经济万象","文化娱乐","科技教育","体育运动","人物肖像","动物植物","素材创意"};
        double[] parameter3={1,2.5,2,3,1,1,1,1,1,1,1,3,5,1};
        index=ranWithNum(parameter3);
        task.setLabel(labels[index-1]);
        double[] parameter4={1.5,4,3,1};
        index=ranWithNum(parameter4);
        int imagenum=0;
        switch(index){
            case 1:
                imagenum=1+rand.nextInt(9);
                break;
            case 2:
                imagenum=10+rand.nextInt(9);
                break;
            case 3:
                imagenum=20+rand.nextInt(30);
                break;
            case 4:
                imagenum=50+rand.nextInt(50);
                break;
        }
        double[] parameter5={5,4,8};
        index=ranWithNum(parameter5);
        double bp=0;
        switch(index){
            case 1:
                bp=(9+rand.nextInt(4))/100.0;
                break;
            case 2:
                bp=(13+rand.nextInt(5))/100.0;
                break;
            case 3:
                bp=(18+rand.nextInt(12))/100.0;
                break;
        }
        task.setSum(imagenum*5);
        task.setBounty((int)(bp*imagenum));
        
        double x1=(bp-0.15)*-16;
        double x2=(Math.sqrt(imagenum)-5)*1.5;
        double x=x1+x2;
        int dur=0;
        if(x<-2)
            dur=1;
        else if(x<-1)
            dur=2;
        else if(x<1)
            dur=3;
        else if(x<2)
            dur=4;
        else if(x<3)
            dur=5;
        else if(x<4)
            dur=6;
        else
            dur=7;
        Calendar c3 = Calendar.getInstance();
        c3.setTime(release_day);
        c3.add(Calendar.DAY_OF_MONTH, dur);// 今天+1天  
        Date acp_fin= c3.getTime(); 
        task.setAcp_fin(acp_fin);
        Calendar c4 = Calendar.getInstance();
        c4.setTime(release_day);
        c4.add(Calendar.DAY_OF_MONTH, dur+1);// 今天+1天 
        Date task_fin= c4.getTime(); 
        task.setTask_fin(task_fin);
        boolean result=TaskRepo.saveTask(task);
        
        for(int j=0;j<5;j++){
        ran=rand.nextInt(406);
        List<String> tasks=users.get(ran).getTask_list();
        tasks.add(task.getTaskID());
        users.get(ran).setTask_list(tasks);
        UserRepo.updateUser(users.get(ran));
        }
        }
    }
}
