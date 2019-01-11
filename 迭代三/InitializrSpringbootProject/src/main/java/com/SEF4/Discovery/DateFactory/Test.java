/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.DateFactory;

import com.SEF4.Discovery.PO_to_VO.Converter;
import com.SEF4.Discovery.Stat.Statistics;
import com.SEF4.Discovery.Stat.get_Statistics;
import com.SEF4.Discovery.repository.TagRepo;
import com.SEF4.Discovery.repository.TaskRepo;
import com.SEF4.Discovery.repository.UserRepo;
import com.SEF4.Discovery.service.FeedBackHandler;
import com.SEF4.Discovery.service.InfoHandler;
import com.SEF4.Discovery.service.TagHelper;
import com.SEF4.Discovery.service.TaskManage;
import com.SEF4.Discovery.util.DateHandler;
import com.SEF4.Discovery.util.EnDe_Code;
import com.SEF4.Discovery.util.JsonDateConvert;
import com.SEF4.Discovery.util.JsonDateValueProcessor;
import com.SEF4.Discovery.view_object.Task_view;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import net.sf.json.JSONObject;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author Kevin
 */
public class Test {
    public static void main(String[] args) throws FileNotFoundException, Exception{
        
        /*User user=new User();
        user.setAuthority("root");
        user.setBalance(100.55);
        user.setPassword("admin");
        user.setTask_fin(8);
        List<String> list=new ArrayList<String>();
        list.add("0");
        list.add("2");
        list.add("6");
        list.add("8");
        user.setTask_list(list);
        user.setUserID("admin");
        user.setUsername("root");
        user.setMailbox("********@qq.com");
        user.setRegist_date(new Date());
        UserRepo.saveUser(user);*/
        //System.out.println(UserRepo.findUser("88888888").getPassword());
        //JSONObject json = JSONObject.fromObject(user,JsonDateValueProcessor.getJsonConfig());
        //System.out.println(json.toString());
        
        //Json对象与字符串的转化
        //JSONObject jsonObject=JSONObject.fromObject(json.toString());
        //User user_cp=(User)JSONObject.toBean(jsonObject, User.class);
        
        //时间差计算
        /*long t1 = user_cp.getRegist_date().getTime();
        long t2 = new Date().getTime();
        long millis = t2 - t1;
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        System.out.println(Long.toString(days));*/
        
        //System.out.println(user_cp.getRegist_date());
        //UserRepo.saveUser(user_cp);
        
        
        //文件存取路径获取
        //获取跟目录
        //File path = new File(ResourceUtils.getURL("classpath:").getPath());
        //if (!path.exists()) {
         //   path = new File("");
        //}
        //System.out.println("path:" + path.getPath());
        //如果上传目录为/static/images/upload/，则可以如下获取：
        /*File upload = new File(path.getPath(), "dataFile/user/");
        if (!upload.exists()) {
            upload.mkdirs();
        }
        System.out.println("upload url:" + upload.getAbsolutePath());*/
        //在开发测试模式时，得到的地址为：{项目跟目录}/target/static/images/upload/
        //在打包成jar正式发布时，得到的地址为：{发布jar包目录}/static/images/upload/
        
        
        //前端Json参数获取及解析
        /*@RequestMapping("/insideByJson.tml")  
        public @ResponseBody Map<String, Object> insideByJson(@RequestBody String jsonParam) {  
        //json字符串转换成bean  
        JSONObject json=JSONObject.fromObject(jsonParam);  
        String[] dateFormats = new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"};    
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));  
        Inside inside=(Inside) JSONObject.toBean(json, Inside.class); 
}  */
       
       //getJSONArray("");
       //get("");
       //List<JSONObject> discovery=new ArrayList<JSONObject>();
        //List<String> taskList=TaskManage.discoverTask("qcw");

            //Task task=TaskRepo.findTask(taskList.get(0));
            //Task_view view=Converter.Task_view(task);
            //System.out.println(view.getPreloadPicURL());
        
        
//        User user=UserRepo.findUser("qcw");
//
//        List<String> task_total=user.getTask_list();
//        List<String> task_fin=user.getFin_list();
//        int Pages=taskList.size()/8;
//        if(taskList.size()%8!=0)
//            Pages++;      
         System.out.println("Before");

         List<Double> y=get_Statistics.get_UserFreeDegree();
         List<String> labels=get_Statistics.get_UserFavLabel();
         System.out.println(y.size());
         System.out.println(labels.size());
//         Date past=JsonDateConvert.StringToDate("2018-05-01");
//         int times=30;
//         int index=1;
//         int next_time=(int) (times*1.3);
//         Random rand = new Random(); 
//
//
//         while(!past.after(new Date())){
//             switch (index) {
//                 case 1:
//                     for(int i=0;i<times;i++){
//                         Statistics.save_Views(past);
//                         
//                         Statistics.save_PastViews(past);
//                     }
//                     index++;
//                     break;
//                 case 5:
//                     for(int i=0;i<next_time;i++){
//                         Statistics.save_Views(past);
//                         Statistics.save_PastViews(past);
//                     }
//                     times=next_time;
//                     next_time=(int) (times*1.3);
//                     index=1;
//                     break;
//                 default:
//                     int random=times+rand.nextInt(next_time-times);
//                     for(int i=0;i<random;i++){
//                         Statistics.save_Views(past);
//                         Statistics.save_PastViews(past);
//                     }
//                     index++;
//                     break;
//             }
//                 Calendar c = Calendar.getInstance();
//                 c.setTime(past);
//                 c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天  
//                 past = c.getTime(); 
//             
//         }

         System.out.println("Finish");
    }
}
