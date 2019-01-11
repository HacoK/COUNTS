/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.domain;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class TaskFeedBack {
    private String taskID;
    private String userID;
    private Integer fin_state;//-1未完成，0正常完成，1逾期
    private Date fin_date;
    private Integer fin_speed;
    private Integer fin_quality;
    private Integer Satisfaction;
    private Integer BP;
    private Integer total_img;
    private Integer fin_img;
    private List<Integer> fin_imgs;

    public List<Integer> getFin_imgs() {
        return fin_imgs;
    }

    public void setFin_imgs(List<Integer> fin_imgs) {
        this.fin_imgs = fin_imgs;
    }

    public Integer getFin_state() {
        return fin_state;
    }

    public void setFin_state(Integer fin_state) {
        this.fin_state = fin_state;
    }

    public Date getFin_date() {
        return fin_date;
    }

    public void setFin_date(Date fin_date) {
        this.fin_date = fin_date;
    }

    public Integer getFin_speed() {
        return fin_speed;
    }

    public void setFin_speed(Integer fin_speed) {
        this.fin_speed = fin_speed;
    }

    public Integer getFin_quality() {
        return fin_quality;
    }

    public void setFin_quality(Integer fin_quality) {
        this.fin_quality = fin_quality;
    }

    public Integer getSatisfaction() {
        return Satisfaction;
    }

    public void setSatisfaction(Integer Satisfaction) {
        this.Satisfaction = Satisfaction;
    }

    public Integer getBP() {
        return BP;
    }

    public void setBP(Integer BP) {
        this.BP = BP;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getTotal_img() {
        return total_img;
    }

    public void setTotal_img(Integer total_img) {
        this.total_img = total_img;
    }

    public Integer getFin_img() {
        return fin_img;
    }

    public void setFin_img(Integer fin_img) {
        this.fin_img = fin_img;
    }

    
}
