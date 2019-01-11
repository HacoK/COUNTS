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
public class Task_view_more {
    private String name;
    private String id;
    private List<String> preloadPicURLs;
    private Integer imageNums;

    public Integer getImageNums() {
        return imageNums;
    }

    public void setImageNums(Integer imageNums) {
        this.imageNums = imageNums;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }
    private String sponsor;

    public List<String> getPreloadPicURLs() {
        return preloadPicURLs;
    }

    public void setPreloadPicURLs(List<String> preloadPicURLs) {
        this.preloadPicURLs = preloadPicURLs;
    }
    private String cash;
    private String deadline;
    private String note;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
