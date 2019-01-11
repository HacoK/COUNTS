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

public class User {
    private String authority;
    private String userID;
    private String password;
    private Double balance;
    private String username;
    private Integer task_fin;
    private List<String> task_list;  //接取过的所有任务
    private String mailbox;
    private Date regist_date;
    private List<String> fin_list;  //完成的所有任务
    private List<String> outdated_list;//逾期任务

    public List<String> getFin_list() {
        return fin_list;
    }

    public void setFin_list(List<String> fin_list) {
        this.fin_list = fin_list;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public Date getRegist_date() {
        return regist_date;
    }

    public void setRegist_date(Date regist_date) {
        this.regist_date = regist_date;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTask_fin() {
        return task_fin;
    }

    public void setTask_fin(Integer task_fin) {
        this.task_fin = task_fin;
    }

    public List<String> getTask_list() {
        return task_list;
    }

    public void setTask_list(List<String> task_list) {
        this.task_list = task_list;
    }
    public List<String> getOutdated_list() {
        return outdated_list;
    }

    public void setOutdated_list(List<String> outdated_list) {
        this.outdated_list = outdated_list;
    }
    
    private Integer worker_lv;
    private Integer sponsor_lv;
    private Integer continuous_check_in;
    private Integer worker_exp;
    private Integer sponsor_exp;
    private Date cur_date;  //上次签到日期
    private Integer remain_task_num;  //当天剩余可接取任务数
    private Integer credit;
    private Integer worker_title_lv;
    private Integer sponsor_title_lv;
    private Integer bonus_point;
    private Integer fin_low;
    private Integer fin_intermediate;
    private Integer fin_advanced;
    private List<String> sign_in_list;

    public Integer getWorker_lv() {
        return worker_lv;
    }

    public void setWorker_lv(Integer worker_lv) {
        this.worker_lv = worker_lv;
    }

    public Integer getSponsor_lv() {
        return sponsor_lv;
    }

    public void setSponsor_lv(Integer sponsor_lv) {
        this.sponsor_lv = sponsor_lv;
    }

    public Integer getContinuous_check_in() {
        return continuous_check_in;
    }

    public void setContinuous_check_in(Integer continuous_check_in) {
        this.continuous_check_in = continuous_check_in;
    }

    public Integer getWorker_exp() {
        return worker_exp;
    }

    public void setWorker_exp(Integer worker_exp) {
        this.worker_exp = worker_exp;
    }

    public Integer getSponsor_exp() {
        return sponsor_exp;
    }

    public void setSponsor_exp(Integer sponsor_exp) {
        this.sponsor_exp = sponsor_exp;
    }

    public Date getCur_date() {
        return cur_date;
    }

    public void setCur_date(Date cur_date) {
        this.cur_date = cur_date;
    }

    public Integer getRemain_task_num() {
        return remain_task_num;
    }

    public void setRemain_task_num(Integer remain_task_num) {
        this.remain_task_num = remain_task_num;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getBonus_point() {
        return bonus_point;
    }

    public void setBonus_point(Integer bonus_point) {
        this.bonus_point = bonus_point;
    }

    public Integer getFin_low() {
        return fin_low;
    }

    public void setFin_low(Integer fin_low) {
        this.fin_low = fin_low;
    }

    public Integer getFin_intermediate() {
        return fin_intermediate;
    }

    public void setFin_intermediate(Integer fin_intermediate) {
        this.fin_intermediate = fin_intermediate;
    }

    public Integer getFin_advanced() {
        return fin_advanced;
    }

    public void setFin_advanced(Integer fin_advanced) {
        this.fin_advanced = fin_advanced;
    }

    public Integer getWorker_title_lv() {
        return worker_title_lv;
    }

    public void setWorker_title_lv(Integer worker_title_lv) {
        this.worker_title_lv = worker_title_lv;
    }

    public Integer getSponsor_title_lv() {
        return sponsor_title_lv;
    }

    public void setSponsor_title_lv(Integer sponsor_title_lv) {
        this.sponsor_title_lv = sponsor_title_lv;
    }

    public List<String> getSign_in_list() {
        return sign_in_list;
    }

    public void setSign_in_list(List<String> sign_in_list) {
        this.sign_in_list = sign_in_list;
    }

}
