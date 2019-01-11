/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.view_object;

/**
 *
 * @author Kevin
 */
public class Stat {
    private Integer users;
    private Integer goodUsers;
    private Integer tasks;
    private Integer finishedTasks;
    private Integer runningTasks;

    public Integer getUsers() {
        return users;
    }

    public void setUsers(Integer users) {
        this.users = users;
    }

    public Integer getGoodUsers() {
        return goodUsers;
    }

    public void setGoodUsers(Integer goodUsers) {
        this.goodUsers = goodUsers;
    }

    public Integer getTasks() {
        return tasks;
    }

    public void setTasks(Integer tasks) {
        this.tasks = tasks;
    }

    public Integer getFinishedTasks() {
        return finishedTasks;
    }

    public void setFinishedTasks(Integer finishedTasks) {
        this.finishedTasks = finishedTasks;
    }

    public Integer getRunningTasks() {
        return runningTasks;
    }

    public void setRunningTasks(Integer runningTasks) {
        this.runningTasks = runningTasks;
    }
}
