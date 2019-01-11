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
public class Task {
    private String name;
    private String taskID;
    private Integer Bounty;
    private Date release_time;
    private Date deadline;
    private Integer worker_num=1;
    private String description;
    private Integer fin;
    private Integer sum;
    private Boolean urgent;
    private String imagesUrl;

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public Integer getFin() {
        return fin;
    }

    public void setFin(Integer fin) {
        this.fin = fin;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
    
    public Integer getBounty() {
        return Bounty;
    }

    public void setBounty(Integer Bounty) {
        this.Bounty = Bounty;
    }

    public Date getRelease_time() {
        return release_time;
    }

    public void setRelease_time(Date release_time) {
        this.release_time = release_time;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getWorker_num() {
        return worker_num;
    }

    public void setWorker_num(Integer worker_num) {
        this.worker_num = worker_num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    private Integer task_lv;//1:低级任务；2：中级任务；3：高级任务

    public Integer getTask_lv() {
        return task_lv;
    }

    public void setTask_lv(Integer task_lv) {
        this.task_lv = task_lv;
    }
    
    private List<Integer> randList;

    public List<Integer> getRandList() {
        return randList;
    }

    public void setRandList(List<Integer> randList) {
        this.randList = randList;
    }
    
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    private Date acp_fin;
    private boolean acp_fin_flag;
    private Date task_fin;
    private boolean task_fin_flag;

    public Date getAcp_fin() {
        return acp_fin;
    }

    public void setAcp_fin(Date acp_fin) {
        this.acp_fin = acp_fin;
    }

    public Date getTask_fin() {
        return task_fin;
    }

    public void setTask_fin(Date task_fin) {
        this.task_fin = task_fin;
    }

    public boolean isAcp_fin_flag() {
        return acp_fin_flag;
    }

    public void setAcp_fin_flag(boolean acp_fin_flag) {
        this.acp_fin_flag = acp_fin_flag;
    }

    public boolean isTask_fin_flag() {
        return task_fin_flag;
    }

    public void setTask_fin_flag(boolean task_fin_flag) {
        this.task_fin_flag = task_fin_flag;
    }
    
    
}
