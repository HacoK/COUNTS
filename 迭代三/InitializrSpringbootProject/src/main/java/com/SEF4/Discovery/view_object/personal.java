/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.view_object;

import java.util.List;

/**
 *
 * @author Kevin
 */
public class personal {
    private String nickname;
    private String id;
    private Integer numberOfTask;
    private String favourite;
    private Integer days;
    private String cash;
    private String recent;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumberOfTask() {
        return numberOfTask;
    }

    public void setNumberOfTask(Integer numberOfTask) {
        this.numberOfTask = numberOfTask;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getRecent() {
        return recent;
    }

    public void setRecent(String recent) {
        this.recent = recent;
    }
    private Integer lvl1;
    private String exp1;
    private String honor1;
    private Integer lvl2;
    private String exp2;
    private String honor2;
    private List<Integer> workerNum;
    private Integer releaserNum;

    public Integer getReputationNum() {
        return reputationNum;
    }

    public void setReputationNum(Integer reputationNum) {
        this.reputationNum = reputationNum;
    }
    private Integer reputationNum;

    public Integer getLvl1() {
        return lvl1;
    }

    public void setLvl1(Integer lvl1) {
        this.lvl1 = lvl1;
    }

    public String getExp1() {
        return exp1;
    }

    public void setExp1(String exp1) {
        this.exp1 = exp1;
    }

    public String getHonor1() {
        return honor1;
    }

    public void setHonor1(String honor1) {
        this.honor1 = honor1;
    }

    public Integer getLvl2() {
        return lvl2;
    }

    public void setLvl2(Integer lvl2) {
        this.lvl2 = lvl2;
    }

    public String getExp2() {
        return exp2;
    }

    public void setExp2(String exp2) {
        this.exp2 = exp2;
    }

    public String getHonor2() {
        return honor2;
    }

    public void setHonor2(String honor2) {
        this.honor2 = honor2;
    }

    public List<Integer> getWorkerNum() {
        return workerNum;
    }

    public void setWorkerNum(List<Integer> workerNum) {
        this.workerNum = workerNum;
    }

    public Integer getReleaserNum() {
        return releaserNum;
    }

    public void setReleaserNum(Integer releaserNum) {
        this.releaserNum = releaserNum;
    }
    
    private Integer rank;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
    
}
