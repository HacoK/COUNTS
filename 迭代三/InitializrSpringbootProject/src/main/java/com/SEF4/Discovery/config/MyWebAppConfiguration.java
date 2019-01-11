/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author Kevin
 */
@Configuration
public class MyWebAppConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * @Description: 对文件的路径进行配置,创建一个虚拟路径/Path/**
         * ，即只要在<img src="/Path/picName.jpg" />便可以直接引用图片 这是图片的物理路径
         * "file:/+本地图片的地址"
         * @Date： Create in 14:08 2017/12/20
         */
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            registry.addResourceHandler("/Path/**").addResourceLocations("file:"+path.getPath()+"/");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyWebAppConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.addResourceHandlers(registry);
    }
}