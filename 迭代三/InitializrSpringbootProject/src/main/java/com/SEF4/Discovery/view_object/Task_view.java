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
public class Task_view {
    private String name;
    private String id;
    private String preloadPicURL;
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

    public String getPreloadPicURL() {
        return preloadPicURL;
    }

    public void setPreloadPicURL(String preloadPicURL) {
        this.preloadPicURL = preloadPicURL;
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
